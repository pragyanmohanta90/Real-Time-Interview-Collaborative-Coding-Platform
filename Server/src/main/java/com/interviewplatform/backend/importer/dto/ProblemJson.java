package com.interviewplatform.backend.importer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProblemJson {

    private String title;

    @JsonProperty("problem_id")
    private String problemId;

    @JsonProperty("problem_slug")
    private String problemSlug;

    private String difficulty;

    private List<String> topics;

    private String description;

    private List<ExampleJson> examples;

    private List<String> constraints;

    @JsonProperty("code_snippets")
    private CodeSnippetsJson codeSnippets;

    public ProblemJson() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    public String getProblemSlug() {
        return problemSlug;
    }

    public void setProblemSlug(String problemSlug) {
        this.problemSlug = problemSlug;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ExampleJson> getExamples() {
        return examples;
    }

    public void setExamples(List<ExampleJson> examples) {
        this.examples = examples;
    }

    public List<String> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<String> constraints) {
        this.constraints = constraints;
    }

    public CodeSnippetsJson getCodeSnippets() {
        return codeSnippets;
    }

    public void setCodeSnippets(CodeSnippetsJson codeSnippets) {
        this.codeSnippets = codeSnippets;
    }
}