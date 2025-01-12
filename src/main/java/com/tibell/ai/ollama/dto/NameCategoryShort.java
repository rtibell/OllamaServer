package com.tibell.ai.ollama.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class NameCategoryShort {
    private List<String> name;
    private List<String> category;
    private String description;

//    public NameCategoryShort(Object name, Object category, String description) {
//        if (name instanceof String) {
//            if (this.name == null) this.name = new ArrayList<String>();
//            this.name.add((String) name);
//        } else if (name instanceof ArrayList) {
//            this.name = (ArrayList<String>) name;
//        } else {
//            log.error("NameCategoryShort: name is not a String or ArrayList<String>");
//        }
//
//        if (category instanceof String) {
//            if (this.category == null) this.category = new ArrayList<String>();
//            this.category.add((String) category);
//        } else if (category instanceof ArrayList) {
//            this.category = (ArrayList<String>) category;
//        } else {
//            log.error("NameCategoryShort: category is not a String or ArrayList<String>");
//        }
//    }
//
//    public void setName(Object name) {
//        if (name instanceof String) {
//            if (this.name == null) this.name = new ArrayList<String>();
//            this.name.add((String) name);
//        } else if (name instanceof ArrayList) {
//            this.name = (ArrayList<String>) name;
//        } else {
//            log.error("NameCategoryShort: name is not a String or ArrayList<String>");
//        }
//    }
//
//    public void setCategory(Object category) {
//        if (category instanceof String) {
//            if (this.category == null) this.category = new ArrayList<String>();
//            this.category.add((String) category);
//        } else if (category instanceof ArrayList) {
//            this.category = (ArrayList<String>) category;
//        } else {
//            log.error("NameCategoryShort: category is not a String or ArrayList<String>");
//        }
//
//    }
//
//    public NameCategoryShort(String oneName, ArrayList<String> category, String description) {
//        if (this.name == null) {
//            this.name = new ArrayList<String>();
//        }
//        this.name.add(oneName);
//        this.category = category;
//        this.description = description;
//    }
//
//    public NameCategoryShort(ArrayList<String> name, String oneCategory, String description) {
//        this.name = name;
//        if (this.category == null) {
//            this.category = new ArrayList<String>();
//        }
//        this.category.add(oneCategory);
//        this.description = description;
//    }
//
//    public void setName(String oneName) {
//        if (this.name == null) {
//            this.name = new ArrayList<String>();
//        }
//        this.name.add(oneName);
//    }
//
//    public void setCategory(String oneCategory) {
//        if (this.category == null) {
//            this.category = new ArrayList<String>();
//        }
//        this.category.add(oneCategory);
//    }
}
