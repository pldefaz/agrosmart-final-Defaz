package ec.edu.espe.agrosmart.service;

import ec.edu.espe.agrosmart.entity.ProductoEntity;
import ec.edu.espe.agrosmart.exception.ProductoNoEncontradoException;
import ec.edu.espe.agrosmart.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.when;

class ProductoServiceTest {

    private List<ProductoEntity> datosDePrueba() {
        return List.of(
                new ProductoEntity("Quinua organica de altura", new BigDecimal("4.50"), 500, "Quinua",
                        "ventas@agrosmart.ec,exportacion@agrosmart.ec"),
                new ProductoEntity("Quinua roja premium", new BigDecimal("5.80"), 320, "Quinua",
                        "ventas@agrosmart.ec"),
                new ProductoEntity("Quinua negra andina", new BigDecimal("6.20"), 180, "Quinua",
                        "distribucion@agrosmart.ec"),
                new ProductoEntity("Quinua en grano sin procesar", BigDecimal.ZERO, 100, "Quinua",
                        "ventas@agrosmart.ec"),
                new ProductoEntity("Quinua perlada blanca", new BigDecimal("3.90"), 250, "Quinua", "")
        );
    }

    @Test
    void obtenerProductosComercializables_conTresValidosYDosInvalidos_debeEmitirSoloLosTresValidos() {
        ProductoRepository repository = Mockito.mock(ProductoRepository.class);
        when(repository.findAll()).thenReturn(datosDePrueba());
        ProductoService service = new ProductoService(repository);
        Flux<ec.edu.espe.agrosmart.domain.Producto> flujo = service.obtenerProductosComercializables();
        StepVerifier.create(flujo)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void obtenerProductosComercializables_sinNingunProductoValido_debeEmitirElProductoGenerico() {
        ProductoRepository repository = Mockito.mock(ProductoRepository.class);
        when(repository.findAll()).thenReturn(List.of(
                new ProductoEntity("Quinua en grano sin procesar", BigDecimal.ZERO, 100, "Quinua", "ventas@agrosmart.ec")
        ));
        ProductoService service = new ProductoService(repository);

        StepVerifier.create(service.obtenerProductosComercializables())
                .expectNextMatches(p -> p.getNombre().equals("Sin productos comercializables"))
                .verifyComplete();
    }

    @Test
    void buscarPorId_conIdExistente_debeEmitirElProducto() {
        ProductoRepository repository = Mockito.mock(ProductoRepository.class);
        ProductoEntity entity = datosDePrueba().get(0);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        ProductoService service = new ProductoService(repository);

        StepVerifier.create(service.buscarPorId(1L))
                .expectNextMatches(p -> p.getNombre().equals("Quinua organica de altura"))
                .verifyComplete();
    }

    @Test
    void buscarPorId_conIdInexistente_debeEmitirProductoNoEncontradoException() {
        ProductoRepository repository = Mockito.mock(ProductoRepository.class);
        when(repository.findById(9999L)).thenReturn(Optional.empty());
        ProductoService service = new ProductoService(repository);

        StepVerifier.create(service.buscarPorId(9999L))
                .expectError(ProductoNoEncontradoException.class)
                .verify();
    }
}