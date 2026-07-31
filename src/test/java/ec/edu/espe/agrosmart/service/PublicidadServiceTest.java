package ec.edu.espe.agrosmart.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.test.StepVerifier;
import static org.mockito.Mockito.when;

class PublicidadServiceTest {

    @Test
    void generarPublicidad_conRespuestaExitosaDeLaIA_debeEmitirElTextoGenerado() {
        AgroSmartAIService aiService = Mockito.mock(AgroSmartAIService.class);
        when(aiService.generarPublicidad("Quinua organica de altura", "tiendas de alimentacion saludable"))
                .thenReturn("Descubre la Quinua Organica de Altura, pura energia andina.");

        PublicidadService service = new PublicidadService(aiService);

        StepVerifier.create(service.generarPublicidad("Quinua organica de altura", "tiendas de alimentacion saludable"))
                .expectNextMatches(texto -> texto.contains("Quinua"))
                .verifyComplete();
    }

    @Test
    void generarPublicidad_cuandoElProveedorFalla_debeEmitirMensajeDeOnErrorResume() {
        AgroSmartAIService aiService = Mockito.mock(AgroSmartAIService.class);
        when(aiService.generarPublicidad(Mockito.anyString(), Mockito.anyString()))
                .thenThrow(new RuntimeException("429 Too Many Requests"));

        PublicidadService service = new PublicidadService(aiService);

        StepVerifier.create(service.generarPublicidad("Quinua", "tiendas de alimentacion saludable"))
                .expectNextMatches(texto -> texto.startsWith("Publicidad no disponible"))
                .verifyComplete();
    }
}