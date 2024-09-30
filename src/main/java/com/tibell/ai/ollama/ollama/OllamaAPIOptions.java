package com.tibell.ai.ollama.ollama;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OllamaAPIOptions {
    private Double temperature = Double.valueOf(0);

    //{
    //  "model": "codellama:code",
    //  "prompt": "def compute_gcd(a, b):",
    //  "suffix": "    return result",
    //  "options": {
    //    "temperature": 0
    //  },
    //  "stream": false
    //}
}
