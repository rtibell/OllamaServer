package com.tibell.ai.ollama.service;

import com.tibell.ai.ollama.message.MessageCommand;
import com.tibell.ai.ollama.ollama.OllamaAPIResponse;

public interface OllamaStructureChat {
    public OllamaAPIResponse queryNameCategoryOneliner(MessageCommand messageCommand);
}
