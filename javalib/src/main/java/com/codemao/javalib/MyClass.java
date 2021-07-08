package com.codemao.javalib;

import java.lang.reflect.Constructor;

public class MyClass {

    private  class A {
    }
    private static class B {
    }

    public static void main(String[] args) {
//        MyClass.A a = new MyClass.A();
//        for (Constructor constructor : a.getClass().getDeclaredConstructors()) {
//            System.out.println("MyClass--name:" + constructor.toString());
//        }

        MyClass.B b = new MyClass.B();
        for (Constructor constructor : b.getClass().getDeclaredConstructors()) {
            System.out.println("MyClass--name:" + constructor.toString());
        }
    }


}