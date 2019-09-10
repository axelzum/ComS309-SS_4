package com.example.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.ResponseBody;

import com.example.beans.*;

@Controller

public class UserRegisterController {

    @RequestMapping(method = RequestMethod.POST, value="/register/student")

    @ResponseBody
    public RegistrationReply registerStudent(@RequestBody User student) {
        RegistrationReply usrregreply = new RegistrationReply();

        Registration.getInstance().add(student);

        //We are setting the below value just to reply a message back to the caller

        usrregreply.setName(student.getName());

        usrregreply.setAge(student.getAge());

        usrregreply.setRegistrationNumber(student.getRegistrationNumber());

        usrregreply.setRegistrationStatus("Successful");

        return usrregreply;

    }

}
