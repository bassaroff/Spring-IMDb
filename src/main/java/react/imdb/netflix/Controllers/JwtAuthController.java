package react.imdb.netflix.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import react.imdb.netflix.Entities.Roles;
import react.imdb.netflix.Entities.Users;
import react.imdb.netflix.Services.UserService;
import react.imdb.netflix.jwt.JwtTokenGenerator;
import react.imdb.netflix.models.JwtRequest;
import react.imdb.netflix.models.JwtResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping(value = "/auth")
public class JwtAuthController {

    @Autowired
    private JwtTokenGenerator jwtTokenGenerator;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //    @RequestMapping(value = "/auth")
//    public ResponseEntity<?> auth(@RequestBody JwtRequest request) throws Exception{
//        authenticate(request.getEmail(), request.getPassword());
//        final UserDetails userDetails =
//                userService.loadUserByUsername(request.getEmail());
//        final String token = jwtTokenGenerator.generateToken(userDetails);
//        return ResponseEntity.ok(new JwtResponse(token));
//    }
//
//    public void authenticate(String email, String password) throws Exception{
//        try{
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
//
//        }catch (DisabledException e){
//            throw new Exception("USER_DISABLED", e);
//        }catch (BadCredentialsException e){
//            throw new Exception("INVALID_CREDENTIALS", e);
//        }
//    }
    @RequestMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody JwtRequest request) throws Exception{
        if(isEmailExists(request.getEmail())){
            return ResponseEntity.ok("User already exists!");
        }else{
            Users user = new Users();
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setFullname(request.getFullName());
            List<Roles> roles = new ArrayList<>();
            user.setRoles(roles);
            userService.createUser(user);
            return ResponseEntity.ok("User registered successfully");
        }
    }

    @RequestMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest request) throws Exception {
        System.out.println(request);
        authenticate(request.getEmail(), request.getPassword());
        final UserDetails userDetails =
                userService.loadUserByUsername(request.getEmail());
        final String token = jwtTokenGenerator.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    public void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @RequestMapping(value = "/getuser/{token}")
    public ResponseEntity<?> getUser(@PathVariable String token){
        String email = jwtTokenGenerator.getEmailFromToken(token);
        Users user = userService.getUser(email);

        return ResponseEntity.ok(user);
    }

    public boolean isEmailExists(String email){
        Users user = userService.getUser(email);
        if(user!=null){
            return true;
        }else return false;
    }
}
