package com.epam.HelloWorld;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class HelloWorldRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("hello world,,,Alaa");
    }

}