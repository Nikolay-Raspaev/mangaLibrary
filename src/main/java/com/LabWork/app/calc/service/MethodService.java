package com.LabWork.app.calc.service;

import com.LabWork.app.calc.domain.IMethods;
import com.LabWork.app.calc.domain.MethodInteger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class MethodService {
    private final ApplicationContext applicationContext;

    public MethodService(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public String Sum(Object first, Object second, String type) {
        final IMethods method = (IMethods) applicationContext.getBean(type);
        if (method instanceof MethodInteger) {
            return String.format("%s", method.Sum(Integer.parseInt(first.toString()), Integer.parseInt(second.toString())));
        }
        else {
            return String.format("%s", method.Sum(first, second));
        }
    }

    public String Multiplication(Object first, Object second, String type) {
        final IMethods method = (IMethods) applicationContext.getBean(type);
        if (method instanceof MethodInteger) {
            return String.format("%s", method.Multiplication(Integer.parseInt(first.toString()), Integer.parseInt(second.toString())));
        }
        else {
            return String.format("%s", method.Multiplication(first, second));
        }
    }

    public String Difference(Object first, Object second, String type) {
        final IMethods method = (IMethods) applicationContext.getBean(type);
        if (method instanceof MethodInteger) {
            return String.format("%s", method.Difference(Integer.parseInt(first.toString()), Integer.parseInt(second.toString())));
        }
        else {
            return String.format("%s", method.Difference(first, second));
        }
    }

    public String Contains(Object first, Object second, String type) {
        final IMethods method = (IMethods) applicationContext.getBean(type);
        if (method instanceof MethodInteger) {
            return String.format("%s", method.Contains(Integer.parseInt(first.toString()), Integer.parseInt(second.toString())));
        }
        else {
            return String.format("%s", method.Contains(first, second));
        }
    }
}
