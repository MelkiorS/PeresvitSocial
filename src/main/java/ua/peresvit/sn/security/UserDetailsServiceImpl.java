package ua.peresvit.sn.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.peresvit.sn.domain.dao.UserRepository;
import ua.peresvit.sn.domain.entity.User;


@Service
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user != null) {
            if (!user.isEnabled()) {
                throw new RuntimeException("User is unable.");
            }
            return ExampleUserDetails.getBuilder()
                    .firstName(user.getFirstName())
                    .id(user.getUserId())
                    .lastName(user.getLastName())
                    .password(user.getPassword())
                    .role(user.getRole())
                    .socialSignInProvider(user.getSocialMediaServices())
                    .username(user.getEmail())
                    .build();
        }
        throw new UsernameNotFoundException("User with email '" + username + "' not found.");
    }
}