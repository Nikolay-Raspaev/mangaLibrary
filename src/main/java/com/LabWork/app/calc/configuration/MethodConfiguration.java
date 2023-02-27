package com.LabWork.app.calc.configuration;

import com.LabWork.app.calc.domain.MethodInteger;
import com.LabWork.app.calc.domain.MethodString;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MethodConfiguration {

    public MethodInteger createIntegerMethods() {
        return new MethodInteger();
    }


    public MethodString createStringMethods() {
        return new MethodString();
    }
}
