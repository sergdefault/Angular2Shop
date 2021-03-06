package com.tsymbaliuk.controllers;

import com.tsymbaliuk.entity.Order;
import com.tsymbaliuk.security.JwtUser;
import com.tsymbaliuk.security.JwtUserFactory;
import com.tsymbaliuk.security.repository.UserRepository;
import com.tsymbaliuk.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.security.Principal;
import java.util.List;

/**
 * Created by SerhiiTsymbaliuk on 12/8/17.
 */
@RestController
@CrossOrigin
public class OrderController {


    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    @RequestMapping(value = "/orders",method = RequestMethod.GET)
    @ResponseBody
    public  List<Order> getOrdersByUserId(Principal principal){
        String name = principal.getName();
        JwtUser jwtUser = JwtUserFactory.create(userRepository.findByUsername(name));
        List<Order> ordersByUserId = orderService.getOrdersByUserId(jwtUser.getId());
        return ordersByUserId;
    }

    @RequestMapping(value = "/orders/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Order> saveOrder(@RequestBody Order order){
        orderService.saveOrder(order);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
