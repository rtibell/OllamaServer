package com.tibell.ai.ollama.ollama;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OllamaAPIOptions {
    private Float temperature = Float.valueOf(1.0f); // 0.7 (0.85)
    private Float repeat_penalty = Float.valueOf(1.9f); // 1.1 (0.7)
    private Integer top_k = Integer.valueOf(40); // 40 (20)
    private Float top_p = Float.valueOf(0.9f); // 0.9 (0.6)

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
