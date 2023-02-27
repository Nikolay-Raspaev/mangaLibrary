package com.LabWork.app.calc.domain;

public interface IMethods<T> {
    T Sum(T first, T second);

    T Multiplication(T first, T second);

    T Difference(T first, T second);

    T Contains(T first, T second);

}
