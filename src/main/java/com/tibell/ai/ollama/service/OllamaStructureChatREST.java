package com.tibell.ai.ollama.service;

import com.tibell.ai.ollama.message.MessageCommand;
import com.tibell.ai.ollama.message.StructuredChatMessageCommand;
import com.tibell.ai.ollama.ollama.OllamaAPIGenerateRequest;
import com.tibell.ai.ollama.ollama.OllamaAPIOptions;
import com.tibell.ai.ollama.ollama.OllamaAPIResponse;
import com.tibell.ai.ollama.ollama.OllamemRestAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OllamaStructureChatREST implements OllamaStructureChat {
    private static final String OLLAME_RESPONSE_FORMAT = "json";
    //private static final String PROMPT_TEMPLATE = "Här är ett JSON-objekt med tomma fält för \"name\" och \"kategorier\", båda är listor med namn respektive kategorier. Även om någon av fälte innehåller ingen eller endast ett värde så skall de fortfarande hanteras som listor eller arrayer. Dessa arrayer skall fyllas med namnen på personer som nämns i texten och lämpliga nyhetskategori som matchar texten. Namnen skall sparas i JSON fältet  \"name\" och kategorier i fältet \"category\", båda hanteras som listor. Fyll också i strängen \"description\" med en sammanfattning av texten som beskriver innehållet i texten med en mening.  Resultatet skall endast innehålla det ifylda JSON-objectet: { \"name\": [], \"category\": [], \"description\": \"\" }. Svara med en tom lista om svar ej kan ges. Texten börjar här: ";
    private static final String PROMPT_TEMPLATE = "I need a well-formed JSON object that represents information derived from the supplied text [TEXT]. The object should include the following keys: * name (list of strings representing the full names of persons and companys found in the supplied text). * category (list of strings representing categories mathcing the content given by the supplied text). * description (String with one or two sentenses describing the content of the supplied text). Ensure that the JSON is: 1. Properly formatted with 2 spaces for indentation. 2. Valid JSON syntax. 3. name and category should be json arrays even if it only contains one element. 4. Empyt arrays is defined as [] 5. Name should contains both first and last name and they should be keep together as a regular string. [TEXT]: ";

    @Value("${ollama.api.model}")
    private String model;

    private final OllamemRestAPI ollamemRestAPI;

    public OllamaStructureChatREST(OllamemRestAPI ollamemRestAPI) {
        this.ollamemRestAPI = ollamemRestAPI;
    }

    @Override
    public OllamaAPIResponse queryNameCategoryOneliner(MessageCommand messageCommand) {
        //log.info("queryNameCategoryOneliner for command: {}", messageCommand);
        StructuredChatMessageCommand structuredChatMessageCommand = (StructuredChatMessageCommand) messageCommand;
        log.debug("Getting response for text: {}", structuredChatMessageCommand.getText());
        String prompt = PROMPT_TEMPLATE + " " + structuredChatMessageCommand.getText();
        String useModel = model;
        if (structuredChatMessageCommand.getModel() != null) { useModel = structuredChatMessageCommand.getModel(); }
        OllamaAPIGenerateRequest request = OllamaAPIGenerateRequest
                .builder()
                .model(useModel)
                .format(OLLAME_RESPONSE_FORMAT)
                .prompt(prompt)
                .options(new OllamaAPIOptions())
                .stream(false)
                .raw(true)
                .keep_alive(0)
                .build();
        log.debug("queryNameCategoryOneliner request: {}", request.toString());
        Long startTime = System.nanoTime();
        OllamaAPIResponse response = ollamemRestAPI.generate(request);
        Long endTime = System.nanoTime();
        log.debug("queryNameCategoryOneliner response: {}", response.toString());
        response.setRunnningTime(endTime - startTime);
        request = null;
        return response;
    }
}
