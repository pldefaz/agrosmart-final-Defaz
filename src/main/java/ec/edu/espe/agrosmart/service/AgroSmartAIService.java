package ec.edu.espe.agrosmart.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface AgroSmartAIService {

    @SystemMessage("""
            Eres un asistente de marketing agricola para la plataforma AgroSmart.
            Respondes siempre en espanol, en una sola frase, sin explicaciones adicionales.""")
    @UserMessage("""
            Redacta una frase publicitaria de maximo 100 caracteres para vender \
            {{producto}} dirigido a {{audiencia}}.""")
    String generarPublicidad(@V("producto") String producto, @V("audiencia") String audiencia);
}