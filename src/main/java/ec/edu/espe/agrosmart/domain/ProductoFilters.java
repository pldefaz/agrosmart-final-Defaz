package ec.edu.espe.agrosmart.domain;

import java.math.BigDecimal;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class ProductoFilters {

    public static final Predicate<Producto> IS_VALID = p ->
            p.getPrecioUsd() != null
                    && p.getPrecioUsd().compareTo(BigDecimal.ZERO) > 0
                    && !p.getCorreosNotificacion().isEmpty();

    public static final Consumer<Producto> LOG_PRODUCTO = p ->
            System.out.println("Producto procesado -> id: " + p.getId()
                    + ", nombre: " + p.getNombre());

    public static final Function<Producto, Producto> A_MAYUSCULAS = p ->
            new Producto(p.getId(), p.getNombre().toUpperCase(), p.getCategoria(),
                    p.getPrecioUsd(), p.getCorreosNotificacion());
}