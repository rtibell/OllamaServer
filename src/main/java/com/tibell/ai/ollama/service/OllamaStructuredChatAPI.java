package com.tibell.ai.ollama.service;

import com.tibell.ai.ollama.message.MessageCommand;
import com.tibell.ai.ollama.message.StructuredChatMessageCommand;
import com.tibell.ai.ollama.ollama.OllamaAPIResponse;
import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.OllamaResult;
import io.github.ollama4j.utils.OptionsBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class OllamaStructuredChatAPI implements OllamaStructureChat {

    private static final String queryNameCategoryOneliner = """
            Här är ett JSON-objekt med tomma "namn" och "kategorier"-arrayer. Kan du fylla i dessa arrayer med namnen på personer som nämns i texten och lämpliga nyhetskategori som matchar texten? Kan du också fylla i strängen "description" med en sammanfattning som beskriver innehållet i texten med en mening?
            Svara endast med det ifylda JSON-objectet.
            {
              "names": [],
              "categorys": [],
              "description": ""
            }
            """;

    @Value("${ollama.api.url}")
    private String apiUrl;

    @Value("${ollama.api.model}")
    private String model;

    public OllamaStructuredChatAPI() {
        log.info("OllamaStructuredChat created");
        log.info("Connecting to Ollama API at: {} with model {}", apiUrl, model);
//        ollamaAPI = new OllamaAPI(apiUrl);
//        ollamaAPI.setRequestTimeoutSeconds(30);
    }

    public OllamaAPIResponse queryNameCategoryOneliner(MessageCommand messageCommand) {
        log.info("Connecting to Ollama API at: {} with model {}", apiUrl, model);
        OllamaAPI locAPI = new OllamaAPI(apiUrl);
        locAPI.setRequestTimeoutSeconds(90);
        StructuredChatMessageCommand structuredChatMessageCommand = (StructuredChatMessageCommand) messageCommand;
        log.info("queryNameCategoryOneliner: {}", messageCommand);
        OllamaResult result = null;
        try {
            result = locAPI.generate(
                    model,
                    queryNameCategoryOneliner + "\n" + structuredChatMessageCommand.getText(),
                    true,
                    new OptionsBuilder()
                            .setTemperature(0.9f)
                            .build());
//            List<OllamaChatMessage> chatList = new ArrayList<>();
//            OllamaChatMessage msg = new OllamaChatMessage();
//            msg.setContent(queryNameCategoryOneliner + "\n" + structuredChatMessageCommand.getText());
//            chatList.add(msg);
//            result = locAPI.chat(model, chatList);
        } catch (OllamaBaseException e) {
            log.warn("OllamaBaseException: {}", e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.warn("IOException: {}", e.getMessage());
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            log.warn("InterruptedException: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        if (result != null && result.getResponse() != null) {
            log.info("Result: {}", result.getResponse());
        }

        return null;
    }
}
