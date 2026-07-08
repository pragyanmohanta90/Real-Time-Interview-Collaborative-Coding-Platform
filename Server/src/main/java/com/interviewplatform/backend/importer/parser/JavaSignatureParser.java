package com.interviewplatform.backend.importer.parser;

import com.interviewplatform.backend.model.ExecutionMetadata;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class JavaSignatureParser {

    private static final Pattern CLASS_PATTERN =
            Pattern.compile("class\\s+(\\w+)");

    private static final Pattern METHOD_PATTERN =
            Pattern.compile(
                    "public\\s+([\\w\\[\\]]+)\\s+(\\w+)\\s*\\(([^)]*)\\)"
            );

    public ExecutionMetadata parse(String javaCode) {

        ExecutionMetadata metadata = new ExecutionMetadata();

        // Class Name
        Matcher classMatcher = CLASS_PATTERN.matcher(javaCode);

        if (classMatcher.find()) {
            metadata.setClassName(classMatcher.group(1));
        }

        // Method Signature
        Matcher methodMatcher = METHOD_PATTERN.matcher(javaCode);

        if (methodMatcher.find()) {

            // Return Type
            metadata.setReturnType(methodMatcher.group(1));

            // Method Name
            metadata.setMethodName(methodMatcher.group(2));

            // Parameters
            String parameters = methodMatcher.group(3).trim();

            List<String> parameterTypes = new ArrayList<>();
            List<String> parameterNames = new ArrayList<>();

            if (!parameters.isEmpty()) {

                String[] params = parameters.split(",");

                for (String parameter : params) {

                    parameter = parameter.trim();

                    String[] parts = parameter.split("\\s+");

                    if (parts.length >= 2) {

                        parameterTypes.add(parts[0]);
                        parameterNames.add(parts[1]);
                    }
                }
            }

            metadata.setParameterTypes(parameterTypes);
            metadata.setParameterNames(parameterNames);
        }

        return metadata;
    }
}