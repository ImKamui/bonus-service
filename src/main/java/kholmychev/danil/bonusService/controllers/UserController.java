package kholmychev.danil.bonusService.controllers;

import kholmychev.danil.bonusService.dto.JwtResponseDto;
import kholmychev.danil.bonusService.dto.UserDto;
import kholmychev.danil.bonusService.models.User;
import kholmychev.danil.bonusService.providers.JwtTokenProvider;
import kholmychev.danil.bonusService.services.UserService;
import kholmychev.danil.bonusService.services.UsersDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final AuthenticationManager authenticationManager;
    private final UsersDetailsService usersDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager,
    		UsersDetailsService usersDetailsService, JwtTokenProvider jwtTokenProvider)
    {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.usersDetailsService = usersDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
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
    
    @PostMapping("/auth/register")
    public ResponseEntity<String> register(@RequestBody UserDto user)
    {
    		try
    		{
    			userService.register(user.getUsername(), user.getPassword(), user.getRole());
    			return new ResponseEntity<>("User registered success", HttpStatus.CREATED);
    		}
    		catch(IllegalArgumentException e)
    		{
    			return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    		}
    }
    
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody UserDto login) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    login.getUsername(),
                    login.getPassword()
                )
            );

            UserDetails userDetails = usersDetailsService.loadUserByUsername(login.getUsername());
            String token = jwtTokenProvider.generateToken(userDetails);

            return ResponseEntity.ok(new JwtResponseDto(token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
    
//    @PostMapping("/auth/login")
//    public ResponseEntity<?> login(@RequestBody UserDto login) {
//        try {
//            User user = userService.findOneByUsername(login.getUsername());
//            if (user == null) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body("User not found: " + login.getUsername());
//            }
//            System.out.println("=== LOGIN DEBUG ===");
//            System.out.println("Found user: " + user.getUsername());
//            System.out.println("Password in DB: " + user.getPassword());
//            System.out.println("Password from request: " + login.getPassword());
//            System.out.println("Role in DB: " + user.getUserRole());
//            System.out.println("===================");
//
//            authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                    login.getUsername(),
//                    login.getPassword()
//                )
//            );
//
//            UserDetails userDetails = usersDetailsService.loadUserByUsername(
//                login.getUsername());
//            String token = jwtTokenProvider.generateToken(userDetails);
//
//            return ResponseEntity.ok(new JwtResponseDto(token));
//        } catch (Exception e) {
//            System.out.println("=== AUTH ERROR ===");
//            System.out.println("Exception: " + e.getClass().getSimpleName());
//            System.out.println("Message: " + e.getMessage());
//            e.printStackTrace();
//            System.out.println("==================");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                .body("Invalid credentials: " + e.getMessage());
//        }
//    }
    
    
}

