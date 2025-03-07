package org.example.battlearena.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/hello")
public class HelloWorldController {

  @GetMapping
  public String sayHello() {
    return "Response.ok(\"Hello, World!\").build()";
  }
}
