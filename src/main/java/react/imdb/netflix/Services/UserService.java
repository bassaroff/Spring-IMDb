package react.imdb.netflix.Services;


import org.springframework.security.core.userdetails.UserDetailsService;
import react.imdb.netflix.Entities.Users;


public interface UserService extends UserDetailsService {
    Users createUser(Users user);
    Users getUser(String email);
}
