package kholmychev.danil.bonusService.controllers;

import kholmychev.danil.bonusService.dto.UserDto;
import kholmychev.danil.bonusService.models.User;
import kholmychev.danil.bonusService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService)
    {
        this.userService = userService;
    }


    @GetMapping("/users")
    public @ResponseBody List<User> getAll()
    {
        return userService.findAll();
    }
    
    @GetMapping("/users/{id}")
    public @ResponseBody User getUser(@PathVariable Integer id)
    {
    		return userService.findOne(id);
    }
    
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto user)
    {
    		try
    		{
    			userService.register(user.getUsername(), user.getPassword());
    			return new ResponseEntity<>("User registered success", HttpStatus.CREATED);
    		}
    		catch(IllegalArgumentException e)
    		{
    			return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    		}
    }
    
    
}

