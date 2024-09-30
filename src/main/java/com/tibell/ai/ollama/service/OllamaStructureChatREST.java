package com.tibell.ai.ollama.service;

import com.tibell.ai.ollama.message.MessageCommand;
import com.tibell.ai.ollama.message.StructuredChatMessageCommand;
import com.tibell.ai.ollama.ollama.OllamaAPIGenerateRequest;
import com.tibell.ai.ollama.ollama.OllamaAPIResponse;
import com.tibell.ai.ollama.ollama.OllamemRestAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OllamaStructureChatREST implements OllamaStructureChat {
    private static final String OLLAME_RESPONSE_FORMAT = "json";
    private static final String PROMPT_TEMPLATE = "Här är ett JSON-objekt med tomma fält för \"namn\" och \"kategorier\", båda är arrayer. Kan du fylla i dessa arrayer med namnen på personer som nämns i texten och lämpliga nyhetskategori som matchar texten. Namnen skall sparas i JSON fältet  \"names\" och kategorier i fältet \"categorys\". Fyll också i strängen \"description\" med en sammanfattning av texten som beskriver innehållet i texten med en mening.  Svara endast med det ifylda JSON-objectet: { \"names\": [], \"categorys\": [], \"description\": \"\" }. Svara endast med det ifylda JSON objektet eller ett tomt objekt om svar ej kan ges. Texten börjar här: ";

    @Value("${ollama.api.model}")
    private String model;

    private final OllamemRestAPI ollamemRestAPI;

    public OllamaStructureChatREST(OllamemRestAPI ollamemRestAPI) {
        this.ollamemRestAPI = ollamemRestAPI;
    }

    @Override
    public OllamaAPIResponse queryNameCategoryOneliner(MessageCommand messageCommand) {
        log.info("queryNameCategoryOneliner");
        StructuredChatMessageCommand structuredChatMessageCommand = (StructuredChatMessageCommand) messageCommand;
        log.info("Getting response for text: {}", structuredChatMessageCommand.getText());
        String prompt = PROMPT_TEMPLATE + " " + structuredChatMessageCommand.getText();
        OllamaAPIGenerateRequest request = OllamaAPIGenerateRequest
                .builder()
                .model(model)
                .format(OLLAME_RESPONSE_FORMAT)
                .prompt(prompt)
                .stream(false)
                .raw(true)
                .build();
        log.info("queryNameCategoryOneliner request: {}", request.toString());
        OllamaAPIResponse response = ollamemRestAPI.generate(request);
        log.info("queryNameCategoryOneliner response: {}", response.toString());
        request = null;
        return response;
    }
}
