package react.imdb.netflix.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import react.imdb.netflix.Entities.Users;
import react.imdb.netflix.Services.UserService;
import react.imdb.netflix.jwt.JwtTokenGenerator;
import react.imdb.netflix.models.JwtRequest;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/")
public class HomeController {
    @Autowired
    private JwtTokenGenerator jwtTokenGenerator;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserService userService;

    @PutMapping("/changepass")
    public ResponseEntity<?> changePassword(@RequestBody JwtRequest request) {
        Users existedUser = userService.getUser(request.getEmail());

        if (passwordEncoder.matches(request.getPassword(), existedUser.getPassword())) {
            existedUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userService.createUser(existedUser);
            return ResponseEntity.ok(existedUser);
        }

        return ResponseEntity.badRequest().body("Old password does not match!");
    }

    @PutMapping("/changename")
    public ResponseEntity<?> changeName(@RequestBody Users user) {
        System.out.println(user);
        Users existedUser = userService.getUser(user.getEmail());
        System.out.println(user);
        existedUser.setFullname(user.getFullname());
        userService.createUser(existedUser);
        return ResponseEntity.ok(existedUser);
    }
}
