package ru.nsu.ccfit.morozov.calculator;

import java.util.*;

public class CalcData {

    private final Map<String, Double> variables = new HashMap<>();
    private final Stack<Double> stack = new Stack<>();

    public void push(Double value){
        stack.push(value);
    }

    public Double pop(){
        return stack.pop();
    }

    public Double peek(){
        return stack.lastElement();
    }

    public void add(String name, Double value){
        variables.put(name, value);
    }

    public Double get(String name){
        return variables.get(name);
    }

    public void delete(String name) {
        variables.remove(name);
    }

    public List<Double> getStack(){
        return Collections.unmodifiableList(stack);
    }

    public int size(){
        return stack.size();
    }

    public boolean empty(){
        return stack.empty();
    }
}