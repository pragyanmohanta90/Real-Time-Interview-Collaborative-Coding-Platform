package com.interviewplatform.backend.candidate.codegen;

import com.interviewplatform.backend.model.ExecutionMetadata;
import com.interviewplatform.backend.model.TestCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JavaDriverGenerator {

    public String generate(
            String userCode,
            ExecutionMetadata metadata,
            List<TestCase> testCases
    ) {

        // Convert Solution -> Main
        String transformedCode = transformUserCode(userCode);

        // Remove last closing brace
        transformedCode = removeLastBrace(transformedCode);

        StringBuilder source = new StringBuilder();

        source.append(transformedCode);
        source.append("\n\n");

        // Inject main()
        source.append(generateMainMethod(metadata, testCases));

        source.append("\n}");

        return source.toString();
    }


    private String transformUserCode(String code) {

        code = code.replaceFirst(
                "public\\s+class\\s+Solution",
                "public class Main"
        );

        code = code.replaceFirst(
                "class\\s+Solution",
                "public class Main"
        );

        return code;
    }


    private String removeLastBrace(String code) {

        int lastBrace = code.lastIndexOf("}");

        if (lastBrace == -1) {
            return code;
        }

        return code.substring(0, lastBrace);
    }

    private String generateMainMethod(
            ExecutionMetadata metadata,
            List<TestCase> testCases
    ) {

        StringBuilder builder = new StringBuilder();

        builder.append("    public static void main(String[] args) {\n\n");

        builder.append("        Main solution = new Main();\n\n");

        for (TestCase testCase : testCases) {

            builder.append(generateInvocation(metadata, testCase));
        }

        builder.append("    }\n");

        return builder.toString();
    }


    private String generateInvocation(
            ExecutionMetadata metadata,
            TestCase testCase
    ) {

        StringBuilder builder = new StringBuilder();

        String returnType = metadata.getReturnType();

        builder.append("        System.out.println(");

        // Array Return Types
        if ("int[]".equals(returnType)) {

            builder.append("java.util.Arrays.toString(");
        } else if ("String[]".equals(returnType)) {

            builder.append("java.util.Arrays.toString(");
        }

        builder.append("solution.")
                .append(metadata.getMethodName())
                .append("(");

        List<String> arguments = testCase.getArguments();

        for (int i = 0; i < arguments.size(); i++) {

            builder.append(
                    convertArgument(
                            metadata.getParameterTypes().get(i),
                            arguments.get(i)
                    )
            );

            if (i != arguments.size() - 1) {
                builder.append(", ");
            }
        }

        builder.append(")");

        if ("int[]".equals(returnType) ||
                "String[]".equals(returnType)) {

            builder.append(")");
        }

        builder.append(");\n");

        return builder.toString();
    }


    // Convert Argument
    private String convertArgument(
            String type,
            String value
    ) {

        if (value == null) {
            return "null";
        }

        value = value.trim();

        switch (type) {

            case "int":
            case "long":
            case "double":
            case "float":
            case "boolean":
                return value;

            case "String":
                return "\"" + value.replace("\"", "") + "\"";

            case "char":
                return "'" + value.replace("'", "") + "'";

            case "int[]":

                String numbers = value
                        .replace("[", "")
                        .replace("]", "")
                        .trim();

                if (numbers.isEmpty()) {
                    return "new int[]{}";
                }

                return "new int[]{" + numbers + "}";

            case "String[]":

                String text = value
                        .replace("[", "")
                        .replace("]", "")
                        .trim();

                if (text.isEmpty()) {
                    return "new String[]{}";
                }

                String[] parts = text.split(",");

                StringBuilder builder = new StringBuilder();

                builder.append("new String[]{");

                for (int i = 0; i < parts.length; i++) {

                    builder.append("\"")
                            .append(parts[i].trim())
                            .append("\"");

                    if (i != parts.length - 1) {
                        builder.append(",");
                    }
                }

                builder.append("}");

                return builder.toString();

            default:
                return value;
        }
    }


}