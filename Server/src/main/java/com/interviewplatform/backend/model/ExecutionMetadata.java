package com.interviewplatform.backend.model;

import java.util.List;

public class ExecutionMetadata {

    // Class Name
    private String className;

    // Method Name
    private String methodName;

    // Return Type
    private String returnType;

    // Parameter Types
    private List<String> parameterTypes;


    public List<String> getParameterNames() {
        return parameterNames;
    }

    public void setParameterNames(List<String> parameterNames) {
        this.parameterNames = parameterNames;
    }

    private List<String> parameterNames;

    public ExecutionMetadata() {
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public List<String> getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(List<String> parameterTypes) {
        this.parameterTypes = parameterTypes;
    }
}