package com.daniel.farmacia.application.service.impl;

import com.daniel.farmacia.application.dto.detalleVenta.DetalleVentaRequestDto;
import com.daniel.farmacia.application.dto.venta.VentaRequestDto;
import com.daniel.farmacia.application.dto.venta.VentaResponseDto;
import com.daniel.farmacia.application.mapper.detalleVenta.DetalleVentaMapper;
import com.daniel.farmacia.application.mapper.venta.VentaMapper;
import com.daniel.farmacia.application.service.IVentaService;
import com.daniel.farmacia.domain.entity.DetalleVenta;
import com.daniel.farmacia.domain.entity.Producto;
import com.daniel.farmacia.domain.entity.Usuario;
import com.daniel.farmacia.domain.entity.Venta;
import com.daniel.farmacia.domain.entity.type.TipoEstadoVenta;
import com.daniel.farmacia.domain.repository.IDetalleVentaRepository;
import com.daniel.farmacia.domain.repository.IProductoRepository;
import com.daniel.farmacia.domain.repository.IUsuarioRepository;
import com.daniel.farmacia.domain.repository.IVentaRepository;
import com.daniel.farmacia.web.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional // para evitar LazyInitException en mappers
public class VentaServiceImpl implements IVentaService {

    private final IVentaRepository ventaRepository;
    private final IUsuarioRepository usuarioRepository;
    private final IProductoRepository productoRepository;
    private final IDetalleVentaRepository detalleVentaRepository;
    private final VentaMapper ventaMapper;
    private final DetalleVentaMapper detalleVentaMapper;


    @Override
    @Transactional(readOnly = true)
    public List<VentaResponseDto> listarVentas() {
        return ventaRepository.findAllWithRelations().stream()
                .map(ventaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public VentaResponseDto buscarVentaPorId(Long id) {
        Venta venta = ventaRepository.findByIdWithRelations(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrado con ID: " + id));
        return ventaMapper.toDto(venta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VentaResponseDto> listarVentasPorCliente(Long clienteId) {
        return ventaRepository.findByCliente_Id(clienteId).stream()
                .map(ventaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public VentaResponseDto crearVenta(VentaRequestDto requestDto) {
        Usuario cliente = usuarioRepository.findById(requestDto.getIdCliente())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + requestDto.getIdCliente()));

        Venta venta = ventaMapper.toEntity(requestDto, cliente);
        // variable final apunta a la MISMA instancia
        final Venta ventaParaDetalles = venta;

        // Validar receta para antibioticos
        validarRecetaParaAntibioticos(requestDto.getDetalles());

        List<DetalleVenta> detalles = requestDto.getDetalles().stream()
                .map(dto -> {
                    Producto producto = productoRepository.findById(dto.getIdProducto())
                            .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + dto.getIdProducto()));
                    return detalleVentaMapper.toEntity(dto, ventaParaDetalles, producto);
                })
                .collect(Collectors.toList());

        venta.setDetalles(detalles);
        venta.calcularTotal(); // Recalcular total basado en detalles

        venta = ventaRepository.save(venta);
        return ventaMapper.toDto(venta);
    }

    private void validarRecetaParaAntibioticos(List<DetalleVentaRequestDto> detalles) {
        for (DetalleVentaRequestDto detalle : detalles) {
            Producto producto = productoRepository.findById(detalle.getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + detalle.getIdProducto()));

            if ("Antibioticos".equalsIgnoreCase(producto.getCategoria().getNombreCategoria())) {
                if (detalle.getNumeroReceta() == null || detalle.getNumeroReceta().trim().isEmpty()) {
                    throw new ResourceNotFoundException("El producto '" + producto.getNombre() + "' requiere receta médica. Por favor, ingrese el numero de receta.");
                }
            }
        }
    }

    @Override
    public VentaResponseDto actualizarEstadoVenta(Long id, TipoEstadoVenta estado) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrado con ID: " + id));

        venta.setEstado(estado);
        venta = ventaRepository.save(venta);

        return ventaMapper.toDto(venta);
    }

    @Override
    public void eliminarVenta(Long id) {
        if(!ventaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Venta no encontrado " + id);
        }
        ventaRepository.deleteById(id);
    }
}
