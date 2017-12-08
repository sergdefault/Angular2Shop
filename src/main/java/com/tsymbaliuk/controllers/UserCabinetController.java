package com.tsymbaliuk.controllers;

import com.tsymbaliuk.security.JwtUser;
import com.tsymbaliuk.security.JwtUserFactory;
import com.tsymbaliuk.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

/**
 * Created by SerhiiTsymbaliuk on 12/7/17.
 */

@RestController
@CrossOrigin()
@RequestMapping(value = "/cabinet")
@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
public class UserCabinetController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public JwtUser getUser(Principal principal){
        String name = principal.getName();
        JwtUser jwtUser = JwtUserFactory.create(userRepository.findByUsername(name));
        return jwtUser;
    }
}
