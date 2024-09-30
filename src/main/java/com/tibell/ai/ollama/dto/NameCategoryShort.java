package com.tibell.ai.ollama.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NameCategoryShort {
    private ArrayList<String> names;
    private ArrayList<String> categorys;
    private String description;
}
