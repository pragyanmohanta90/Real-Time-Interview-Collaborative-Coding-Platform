package com.interviewplatform.backend.importer.parser;

import com.interviewplatform.backend.model.Example;
import com.interviewplatform.backend.model.TestCase;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExampleParser {

    public List<TestCase> parse(List<Example> examples) {

        List<TestCase> testCases = new ArrayList<>();

        if (examples == null) {
            return testCases;
        }

        for (Example example : examples) {

            TestCase testCase = new TestCase();

            // Original input
            testCase.setInput(example.getInput());

            // Parsed arguments
            testCase.setArguments(parseArguments(example.getInput()));

            // Expected output
            testCase.setExpectedOutput(example.getOutput());

            // Visible example
            testCase.setHidden(false);

            testCases.add(testCase);
        }

        return testCases;
    }

    private List<String> parseArguments(String input) {

        List<String> arguments = new ArrayList<>();

        if (input == null || input.isBlank()) {
            return arguments;
        }

        StringBuilder current = new StringBuilder();

        int bracketDepth = 0;

        for (char c : input.toCharArray()) {

            if (c == '[') {
                bracketDepth++;
            }

            if (c == ']') {
                bracketDepth--;
            }

            if (c == ',' && bracketDepth == 0) {

                addArgument(arguments, current.toString());

                current.setLength(0);

                continue;
            }

            current.append(c);
        }

        addArgument(arguments, current.toString());

        return arguments;
    }

    private void addArgument(List<String> arguments, String token) {

        token = token.trim();

        if (token.isEmpty()) {
            return;
        }

        int equalsIndex = token.indexOf('=');

        if (equalsIndex != -1) {

            token = token.substring(equalsIndex + 1).trim();
        }

        arguments.add(token);
    }
}