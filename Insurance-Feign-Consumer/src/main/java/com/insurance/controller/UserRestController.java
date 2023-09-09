package com.insurance.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "userRestController")
@Scope(value = "request")
@RequestMapping("/user-feign")
@CrossOrigin(origins = "*")
public class UserRestController {

}
