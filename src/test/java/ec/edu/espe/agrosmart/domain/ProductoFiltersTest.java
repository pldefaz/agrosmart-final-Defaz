package ec.edu.espe.agrosmart.domain;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductoFiltersTest {

    @Test
    void isValid_conPrecioMayorACeroYConCorreos_debeSerVerdadero() {
        Producto valido = new Producto(1L, "Quinua organica de altura", "Quinua",
                new BigDecimal("4.50"), List.of("ventas@agrosmart.ec"));

        assertTrue(ProductoFilters.IS_VALID.test(valido));
    }

    @Test
    void isValid_conPrecioCero_debeSerFalso() {
        Producto precioCero = new Producto(4L, "Quinua en grano sin procesar", "Quinua",
                BigDecimal.ZERO, List.of("ventas@agrosmart.ec"));

        assertFalse(ProductoFilters.IS_VALID.test(precioCero));
    }

    @Test
    void isValid_sinCorreosDeNotificacion_debeSerFalso() {
        Producto sinCorreos = new Producto(5L, "Quinua perlada blanca", "Quinua",
                new BigDecimal("3.90"), List.of());

        assertFalse(ProductoFilters.IS_VALID.test(sinCorreos));
    }

    @Test
    void aMayusculas_debeDevolverUnProductoNuevoSinMutarElOriginal() {
        Producto original = new Producto(1L, "quinua organica de altura", "Quinua",
                new BigDecimal("4.50"), List.of("ventas@agrosmart.ec"));

        Producto transformado = ProductoFilters.A_MAYUSCULAS.apply(original);

        assertFalse(original.getNombre().equals(original.getNombre().toUpperCase())
                && original == transformado);
        assertTrue(transformado.getNombre().equals("QUINUA ORGANICA DE ALTURA"));
        assertTrue(original.getNombre().equals("quinua organica de altura"));
    }
}