package com.insurance.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "adminRestController")
@Scope(value = "request")
@RequestMapping("/admin-feign")
@CrossOrigin(origins = "*")
public class AdminRestController {

}
