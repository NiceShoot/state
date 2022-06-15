package com.example.state.genericClass;

import java.util.ArrayList;
import java.util.List;

public class mainClass {
    public static void main(String[] args) {
        GenericClass<String> genericClassString = new GenericClass<>("泛型类String");
        GenericClass<Integer> genericClassInteger= new GenericClass<>(123);
        System.out.println(genericClassInteger.getValue());

        List<?> list = new ArrayList<>();
        GenericClass<?> genericClass = new GenericClass<>(123);

    }
}
