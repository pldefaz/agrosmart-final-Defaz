package ec.edu.espe.agrosmart.mapper;

import ec.edu.espe.agrosmart.domain.Producto;
import ec.edu.espe.agrosmart.entity.ProductoEntity;
import java.util.Arrays;
import java.util.List;

public class ProductoMapper {

    public static Producto toDominio(ProductoEntity entity) {
        String crudo = entity.getCorreosNotificacion();
        List<String> correos = (crudo == null || crudo.isBlank())
                ? List.of()
                : Arrays.asList(crudo.split(","));

        return new Producto(
                entity.getIdProducto(),
                entity.getNombreProducto(),
                entity.getCategoria(),
                entity.getPrecioUsd(),
                correos
        );
    }
}