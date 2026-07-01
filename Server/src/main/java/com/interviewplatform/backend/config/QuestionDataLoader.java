package com.interviewplatform.backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interviewplatform.backend.importer.dto.ExampleJson;
import com.interviewplatform.backend.importer.dto.ProblemJson;
import com.interviewplatform.backend.model.*;
import com.interviewplatform.backend.repository.QuestionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionDataLoader implements CommandLineRunner {

    private final QuestionRepository questionRepository;
    private final ObjectMapper objectMapper;

    public QuestionDataLoader(QuestionRepository questionRepository,
                              ObjectMapper objectMapper) {
        this.questionRepository = questionRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {

        if (questionRepository.count() > 0) {
            System.out.println("Questions already exist. Skipping import.");
            return;
        }

        PathMatchingResourcePatternResolver resolver =
                new PathMatchingResourcePatternResolver();

        Resource[] resources =
                resolver.getResources("classpath:dataset/problems/*.json");

        for (Resource resource : resources) {

            try {

                ProblemJson json =
                        objectMapper.readValue(resource.getInputStream(), ProblemJson.class);

                Question question = new Question();

                question.setExternalProblemId(json.getProblemId());
                question.setSlug(json.getProblemSlug());

                question.setTitle(json.getTitle());
                question.setDescription(json.getDescription());

                question.setDifficulty(
                        Difficulty.valueOf(json.getDifficulty().toUpperCase())
                );

                if (json.getTopics() != null && !json.getTopics().isEmpty()) {

                    question.setCategory(json.getTopics().get(0));
                    question.setTags(json.getTopics());

                }

                question.setConstraints(json.getConstraints());

                StarterCode starterCode = new StarterCode();

                if (json.getCodeSnippets() != null) {

                    starterCode.setJava(json.getCodeSnippets().getJava());
                    starterCode.setCpp(json.getCodeSnippets().getCpp());
                    starterCode.setJavascript(json.getCodeSnippets().getJavascript());
                    starterCode.setPython(json.getCodeSnippets().getPython3());

                }

                question.setStarterCode(starterCode);

                List<Example> examples = new ArrayList<>();

                if (json.getExamples() != null) {

                    for (ExampleJson exampleJson : json.getExamples()) {

                        Example example = new Example();

                        String text = exampleJson.getExampleText();

                        String input = "";
                        String output = "";
                        String explanation = "";

                        if (text.contains("Input:")) {

                            int start = text.indexOf("Input:") + 6;

                            int end = text.contains("Output:")
                                    ? text.indexOf("Output:")
                                    : text.length();

                            input = text.substring(start, end).trim();
                        }

                        if (text.contains("Output:")) {

                            int start = text.indexOf("Output:") + 7;

                            int end = text.contains("Explanation:")
                                    ? text.indexOf("Explanation:")
                                    : text.length();

                            output = text.substring(start, end).trim();
                        }

                        if (text.contains("Explanation:")) {

                            explanation =
                                    text.substring(text.indexOf("Explanation:") + 12).trim();
                        }

                        example.setInput(input);
                        example.setOutput(output);
                        example.setExplanation(explanation);

                        examples.add(example);

                    }

                }

                question.setExamples(examples);

                question.setEstimatedTime(15);

                question.setTestCases(new ArrayList<>());

                questionRepository.save(question);

            } catch (Exception e) {

                System.out.println("Failed: " + resource.getFilename());

            }

        }

        System.out.println("Imported " + questionRepository.count() + " questions.");

    }

}