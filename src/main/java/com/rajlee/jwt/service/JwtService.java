package com.rajlee.jwt.service;


import com.rajlee.jwt.dao.UserDao;
import com.rajlee.jwt.entity.JwtRequest;
import com.rajlee.jwt.entity.JwtResponse;
import com.rajlee.jwt.entity.User;
import com.rajlee.jwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    public JwtResponse createJwtToken(JwtRequest jwtRequest){
        String userName = jwtRequest.getUsername();
        String userPassword = jwtRequest.getUserPassword();
        authenticate(userName, userPassword);

        final UserDetails userDetails =loadUserByUsername(userName);
        String newGeneratedToken = jwtUtil.generateToken(userDetails);

        User user = userDao.findById(userName).get();
        return new JwtResponse(user, newGeneratedToken);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findById(username).get();

        if(user != null){
            return new org.springframework.security.core.userdetails.User(
                    user.getUserName(),
                    user.getPassword(),
                    getAuthorities(user)
            );
        }else{
            throw new UsernameNotFoundException("userName not found");
        }
    }

    private Set getAuthorities(User user){
        Set authorities = new HashSet();

        user.getRoleSet().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
        });
        return authorities;
    }

    private void authenticate(String userName, String userPassword){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
        }catch (DisabledException | BadCredentialsException e){
            System.out.println(e.getMessage());
        }
    }
}
