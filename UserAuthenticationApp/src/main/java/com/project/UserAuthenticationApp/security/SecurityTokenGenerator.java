package com.project.UserAuthenticationApp.security;



import com.project.UserAuthenticationApp.domain.User;


public interface SecurityTokenGenerator {
    String createToken(User user);

}
