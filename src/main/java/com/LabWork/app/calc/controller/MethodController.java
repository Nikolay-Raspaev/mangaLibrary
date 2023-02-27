package com.LabWork.app.calc.controller;

import com.LabWork.app.calc.service.MethodService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MethodController {
    private final MethodService methodService;

    public MethodController(MethodService methodService) {
        this.methodService = methodService;
    }

    @GetMapping("/sum")
    @ResponseBody
    public String sum(@RequestParam(value = "first", defaultValue = "0") String first,
                        @RequestParam(value = "second", defaultValue = "0") String second,
                        @RequestParam(value = "type", defaultValue = "int") String type) {
        Object a = first;
        Object b = second;
        System.out.println(a.getClass());
        return methodService.Sum(a, b, type);
    }

    @GetMapping("/multiplication")
    @ResponseBody
    public String mul(@RequestParam(value = "first", defaultValue = "0") Object first,
                      @RequestParam(value = "second", defaultValue = "0") Object second,
                      @RequestParam(value = "type", defaultValue = "int") String type) {
        return methodService.Multiplication(first, second, type);
    }

    @GetMapping("/difference")
    @ResponseBody
    public String difference(@RequestParam(value = "first", defaultValue = "0") Object first,
                      @RequestParam(value = "second", defaultValue = "0") Object second,
                      @RequestParam(value = "type", defaultValue = "int") String type) {
        return methodService.Difference(first, second, type);
    }

    @GetMapping("/cont")
    @ResponseBody
    public String cont(@RequestParam(value = "first", defaultValue = "0") Object first,
                       @RequestParam(value = "second", defaultValue = "0") Object second,
                       @RequestParam(value = "type", defaultValue = "int") String type) {
        return methodService.Contains(first, second, type);
    }
}
