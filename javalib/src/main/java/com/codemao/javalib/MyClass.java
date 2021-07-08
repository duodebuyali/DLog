package com.codemao.javalib;

import java.lang.reflect.Constructor;

public class MyClass {


    private static class B {
    }

    public static void main(String[] args) {
        MyClass.B b = new MyClass.B();
        for (Constructor constructor : b.getClass().getDeclaredConstructors()) {
            System.out.println("MyClass--name:" + constructor.toString());
        }
    }
}