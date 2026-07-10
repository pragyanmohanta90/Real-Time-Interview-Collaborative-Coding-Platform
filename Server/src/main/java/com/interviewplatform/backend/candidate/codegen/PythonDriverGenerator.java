package com.interviewplatform.backend.candidate.codegen;

import com.interviewplatform.backend.model.ExecutionMetadata;
import com.interviewplatform.backend.model.TestCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PythonDriverGenerator {

    public String generate(
            String userCode,
            ExecutionMetadata metadata,
            List<TestCase> testCases
    ) {

        StringBuilder source = new StringBuilder();

        // Add typing import
        source.append("from typing import *\n\n");

        // User solution
        source.append(userCode);
        source.append("\n\n");

        // Create solution object
        source.append("solution = Solution()\n\n");

        // Generate test case calls
        for (TestCase testCase : testCases) {

            source.append("print(solution.")
                    .append(metadata.getMethodName())
                    .append("(");

            List<String> args = testCase.getArguments();

            for (int i = 0; i < args.size(); i++) {

                source.append(convertArgument(
                        metadata.getParameterTypes().get(i),
                        args.get(i)
                ));

                if (i != args.size() - 1) {
                    source.append(", ");
                }
            }

            source.append("))\n");
        }

        return source.toString();
    }

    private String convertArgument(String type, String value) {

        if (value == null) {
            return "None";
        }

        value = value.trim();

        switch (type) {

            case "int":
            case "long":
            case "double":
            case "float":
                return value;

            case "boolean":
                return value.toLowerCase();

            case "String":
                return "\"" + value.replace("\"", "") + "\"";

            case "int[]":
            case "String[]":
                return value;

            default:
                return value;
        }
    }

}