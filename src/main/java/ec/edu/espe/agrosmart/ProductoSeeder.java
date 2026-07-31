package ec.edu.espe.agrosmart;

import ec.edu.espe.agrosmart.entity.ProductoEntity;
import ec.edu.espe.agrosmart.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class ProductoSeeder {

    @Bean
    CommandLineRunner sembrarProductos(ProductoRepository repository) {
        return args -> {
            // idempotente: si ya hay datos, no vuelve a insertar (evita violar
            // el unique de nombre_producto en cada reinicio de la app)
            if (repository.count() == 0) {

                // 3 productos VALIDOS: precio > 0 y con correos de notificacion
                repository.save(new ProductoEntity(
                        "Quinua organica de altura",
                        new BigDecimal("4.50"), 500, "Quinua",
                        "ventas@agrosmart.ec,exportacion@agrosmart.ec"));

                repository.save(new ProductoEntity(
                        "Quinua roja premium",
                        new BigDecimal("5.80"), 320, "Quinua",
                        "ventas@agrosmart.ec"));

                repository.save(new ProductoEntity(
                        "Quinua negra andina",
                        new BigDecimal("6.20"), 180, "Quinua",
                        "distribucion@agrosmart.ec"));

                // INVALIDO 1: precio en 0
                repository.save(new ProductoEntity(
                        "Quinua en grano sin procesar",
                        BigDecimal.ZERO, 100, "Quinua",
                        "ventas@agrosmart.ec"));

                // INVALIDO 2: sin correos de notificacion
                repository.save(new ProductoEntity(
                        "Quinua perlada blanca",
                        new BigDecimal("3.90"), 250, "Quinua",
                        ""));
            }
        };
    }
}