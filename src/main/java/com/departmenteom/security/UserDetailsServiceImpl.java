package com.departmenteom.security;

import com.departmenteom.entity.UsersCred;
import com.departmenteom.repo.UsersCredRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("userDetailsServiceImpl")
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersCredRepo usersCredRepo;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserDetailsServiceImpl(UsersCredRepo usersCredRepo) {
        this.usersCredRepo = usersCredRepo;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsersCred usersCred = usersCredRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User doesn`t exist"));
        return SecurityUser.fromUser(usersCred);
    }

    public UsersCred getUserByUsername(String username) {
        return usersCredRepo.findByUsername(username).get();
    }

    public UsersCred saveUser(UsersCred usersCred) {
        Optional<UsersCred> userCredOpt = usersCredRepo.findByUsername(usersCred.getUsername());
        if (userCredOpt.isPresent()) {
            usersCred.setId(userCredOpt.get().getId());
            usersCred.setPassword(userCredOpt.get().getPassword());
            usersCred = usersCredRepo.save(usersCred);
            log.info("User updated");
        } else {
            usersCred.setPassword(bCryptPasswordEncoder.encode(usersCred.getPassword()));
            usersCred = usersCredRepo.save(usersCred);
            log.info("User {} saved with role {}", usersCred.getUsername(), usersCred.getRole());
        }
        return usersCred;
    }

    public Iterable<UsersCred> getAllUsers() {
        return usersCredRepo.findAll();
    }

}
