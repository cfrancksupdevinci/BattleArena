package org.example.battlearena;

import org.example.battlearena.service.HelloWorldService;

public class Main {
    public static void main(String[] args) {
        HelloWorldService helloWorldService = new HelloWorldService("localhost:8080/hello");
        helloWorldService.GetHelloWorld();
        Game.main(args);
    }
}
