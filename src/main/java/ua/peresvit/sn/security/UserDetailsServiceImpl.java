package ua.peresvit.sn.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.peresvit.sn.domain.dao.UserRepository;
import ua.peresvit.sn.domain.entity.Role;
import ua.peresvit.sn.domain.entity.User;

import java.util.Collection;


@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private  UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with email '" + username + "' not found."));

        if (!user.isEnabled()) {
            throw new RuntimeException("User is unable.");
        }

        return ExampleUserDetails.getBuilder()
                .firstName(user.getFirstName())
                .id(user.getUserId())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .role(user.getRoles().get(0)) // TODO refactor to multiple roles !!!!!!!!!!!!!
                .socialSignInProvider(user.getSocialMediaServices())
                .username(user.getEmail())
                .build();
    }

    public static Collection<? extends GrantedAuthority> getAuthorities(User user){
        String[] userRoles = user.getRoles().stream()
                .map(Role::getRoleName)
                .toArray(String[]::new);
        return AuthorityUtils.createAuthorityList(userRoles);
    }
}