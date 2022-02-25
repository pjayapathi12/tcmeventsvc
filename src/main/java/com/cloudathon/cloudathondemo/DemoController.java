package com.cloudathon.cloudathondemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DemoController {

    @Autowired
    private EmployeeRepository repository;
    @Autowired
    private TcmEventProducer tcmEventProducer;

    @PostMapping("/publish/event")
    public String publish(@RequestBody String event) {
        return tcmEventProducer.produce(event);
    }

    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        return repository.findAll();
    }

    @GetMapping("/test")
    public String test() {
        return "Test Successful with Azure spring boot web app deployment and setup";
    }

}
