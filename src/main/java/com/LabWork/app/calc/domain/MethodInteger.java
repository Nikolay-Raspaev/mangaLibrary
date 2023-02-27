package com.LabWork.app.calc.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component(value = "int")
public class MethodInteger implements IMethods<Integer> {
    @Override
    public Integer Sum(Integer first, Integer second) {
        return first + second;
    }
    @Override
    public Integer Multiplication(Integer first, Integer second) {
        return first * second;
    }

    @Override
    public Integer Difference(Integer first, Integer second) {
        return first - second;
    }

    @Override
    public Integer Contains(Integer first, Integer second) {
        if (second==0){
            return second;
        }else{
            return first/second;
        }
    }
}
