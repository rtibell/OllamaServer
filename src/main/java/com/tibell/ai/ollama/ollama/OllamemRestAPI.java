package com.tibell.ai.ollama.ollama;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "ollamemRestAPI", url = "${ollama.api.url}")
public interface OllamemRestAPI {

    @PostMapping("/generate")
    public OllamaAPIResponse generate(OllamaAPIGenerateRequest request);
}
