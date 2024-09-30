package com.tibell.ai.ollama.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;

@AllArgsConstructor
@Getter
public class NameCategoryShort {
    private ArrayList<String> names;
    private ArrayList<String> categories;
    private String shortDescription;
}
