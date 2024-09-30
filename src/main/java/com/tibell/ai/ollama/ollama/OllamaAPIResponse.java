package com.tibell.ai.ollama.ollama;

// https://github.com/ollama/ollama/blob/main/docs/api.md

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
public class OllamaAPIResponse {
    private String model;
    private String create_at;
    private String response;
    private Boolean done;
    private Long[] context;
    private Long total_duration;
    private Long load_duration;
    private Integer prompt_eval_count;
    private Long prompt_eval_duration;
    private Integer eval_count;
    private Long eval_duration;

    public String toString() {
        return "OllamaAPIResponse{ " +
                "model='" + model + '\'' +
                ", create_at='" + create_at + '\'' +
                ", response='" + response.stripTrailing() + '\'' +
                ", done=" + done +
                //", context=" + context +
                ", total_duration=" + total_duration +
                //", load_duration=" + load_duration +
                //", prompt_eval_count=" + prompt_eval_count +
                //", prompt_eval_duration=" + prompt_eval_duration +
                //", eval_count=" + eval_count +
                //", eval_duration=" + eval_duration +
                '}';
    }
    //{
    //  "model": "llama3.2",
    //  "created_at": "2023-08-04T19:22:45.499127Z",
    //  "response": "",
    //  "done": true,
    //  "context": [1, 2, 3],
    //  "total_duration": 10706818083,
    //  "load_duration": 6338219291,
    //  "prompt_eval_count": 26,
    //  "prompt_eval_duration": 130079000,
    //  "eval_count": 259,
    //  "eval_duration": 4232710000
    //}
}
