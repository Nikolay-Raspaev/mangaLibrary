package com.LabWork.app.calc.domain;

import org.springframework.stereotype.Component;

@Component(value = "string")
public class MethodString implements IMethods<String>{
    @Override
    public String Sum(String first, String second) {
        return first.concat(second);
    }

    @Override
    public String Multiplication(String first, String second) {
        String res = first;
        for (int i = 0; i < Integer.parseInt(second) - 1; i++) {
            res = Sum(res, first);
        }
        return res;
    }

    @Override
    public String Difference(String first, String second) {
        if (first.contains(second)) {
            return (first).replace(second, "");
        }
        return first;
    }

    @Override
    public String Contains(String first, String second) {
        if ((first).contains(second)) {
            return "true";
        } else{
            return "false";
        }
    }
}
