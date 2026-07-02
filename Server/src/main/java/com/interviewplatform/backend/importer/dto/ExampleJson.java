package com.interviewplatform.backend.importer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ExampleJson {

    @JsonProperty("example_num")
    private int exampleNum;

    @JsonProperty("example_text")
    private String exampleText;

    private List<String> images;

    public ExampleJson() {
    }

    public int getExampleNum() {
        return exampleNum;
    }

    public void setExampleNum(int exampleNum) {
        this.exampleNum = exampleNum;
    }

    public String getExampleText() {
        return exampleText;
    }

    public void setExampleText(String exampleText) {
        this.exampleText = exampleText;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}