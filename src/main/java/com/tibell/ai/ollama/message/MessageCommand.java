package com.tibell.ai.ollama.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public abstract class MessageCommand {
    private MessageType messageType;

}
