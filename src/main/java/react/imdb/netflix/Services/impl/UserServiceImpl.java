package react.imdb.netflix.Services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import react.imdb.netflix.Entities.Users;
import react.imdb.netflix.Repositories.UsersRepository;
import react.imdb.netflix.Services.UserService;

@Service
public class UserServiceImpl  implements UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Users user = usersRepository.findByEmail(s);
        if(user!=null){
            return user;
        }else
        {
            throw new UsernameNotFoundException("USER NOT FOUND");
        }
    }

    @Override
    public Users createUser(Users user) {
        return usersRepository.save(user);
    }

    @Override
    public Users getUser(String email) {
        return usersRepository.findByEmail(email);
    }
}
