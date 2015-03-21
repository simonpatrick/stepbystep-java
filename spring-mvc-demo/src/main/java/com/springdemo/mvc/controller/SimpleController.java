package com.springdemo.mvc.controller;

import com.springdemo.mvc.model.UserModel;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by patrick on 15/3/21.
 *
 * @version $Id$
 */

@Controller
public class SimpleController {
    @RequestMapping(value = "/request",method = RequestMethod.GET)
    public String getFormData(){
        return "show form data";
    }

    @RequestMapping(value = "/request",method = RequestMethod.POST)
    public ResponseEntity request(RequestEntity<UserModel> request){
        ResponseEntity<UserModel> res = ResponseEntity.ok(request.getBody());
        return res;
    }
}
