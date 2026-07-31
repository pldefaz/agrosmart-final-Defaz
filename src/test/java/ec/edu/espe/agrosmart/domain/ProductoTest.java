package ec.edu.espe.agrosmart.domain;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ProductoTest {

    @Test
    void constructor_alMutarLaListaOriginalDespuesDeCrear_noDebeAfectarAlProducto() {
        List<String> correos = new ArrayList<>();
        correos.add("ventas@agrosmart.ec");
        Producto producto = new Producto(1L, "Quinua organica de altura", "Quinua",
                new BigDecimal("4.50"), correos);

        correos.add("intruso@mail.com");

        assertEquals(1, producto.getCorreosNotificacion().size());
    }

    @Test
    void getCorreosNotificacion_dosLlamadasSeguidas_debenDevolverListasDistintasEnMemoria() {
        Producto producto = new Producto(1L, "Quinua roja premium", "Quinua",
                new BigDecimal("5.80"), List.of("ventas@agrosmart.ec"));

        List<String> primeraLlamada = producto.getCorreosNotificacion();
        List<String> segundaLlamada = producto.getCorreosNotificacion();

        assertNotSame(primeraLlamada, segundaLlamada);
        assertEquals(primeraLlamada, segundaLlamada);
    }

    @Test
    void getCorreosNotificacion_alIntentarModificarLaListaDevuelta_debeLanzarExcepcion() {
        Producto producto = new Producto(1L, "Quinua negra andina", "Quinua",
                new BigDecimal("6.20"), List.of("distribucion@agrosmart.ec"));

        List<String> correosDevueltos = producto.getCorreosNotificacion();

        assertThrows(UnsupportedOperationException.class,
                () -> correosDevueltos.add("otro@mail.com"));
    }
}