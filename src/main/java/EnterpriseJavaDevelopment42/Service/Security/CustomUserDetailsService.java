package EnterpriseJavaDevelopment42.Service.Security;

import EnterpriseJavaDevelopment42.Config.CustomUserDetails;
import EnterpriseJavaDevelopment42.Model.Security.User;
import EnterpriseJavaDevelopment42.Repository.Security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if(!user.isPresent()){
            throw new UsernameNotFoundException("El Usuario no existe");
        }
        CustomUserDetails userDetails = new CustomUserDetails();
        userDetails.setUser(user.get());
        return userDetails;
    }
}
