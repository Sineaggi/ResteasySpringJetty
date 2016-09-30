package com.example;

import javax.inject.Named;

@Named
public class HelloWorld {
    private final String string = "Hello, 世界";

    public String getString() {
        return string;
    }
}
