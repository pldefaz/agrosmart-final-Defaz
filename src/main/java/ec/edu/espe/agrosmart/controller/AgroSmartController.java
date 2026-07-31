package ec.edu.espe.agrosmart.controller;

import ec.edu.espe.agrosmart.domain.Producto;
import ec.edu.espe.agrosmart.service.ProductoService;
import ec.edu.espe.agrosmart.service.PublicidadService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class AgroSmartController {

    private final ProductoService productoService;
    private final PublicidadService publicidadService;

    public AgroSmartController(ProductoService productoService, PublicidadService publicidadService) {
        this.productoService = productoService;
        this.publicidadService = publicidadService;
    }

    @GetMapping("/productos")
    public Flux<Producto> listar() {
        return productoService.obtenerProductosComercializables();
    }

    @GetMapping("/productos/{id}")
    public Mono<Producto> buscarPorId(@PathVariable Long id) {
        return productoService.buscarPorId(id);
    }

    @GetMapping("/agrosmart/publicidad")
    public Mono<String> publicidad(@RequestParam String producto, @RequestParam String audiencia) {
        return publicidadService.generarPublicidad(producto, audiencia);
    }
}