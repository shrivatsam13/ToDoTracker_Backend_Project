package com.project.UserTaskApp.proxy;

import com.project.UserTaskApp.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="user-authentication-app",url="localhost:8099")
public interface UserProxy {

    @PostMapping("/api/v1/save")
    public ResponseEntity<?> saveUser(@RequestBody User user);

    @DeleteMapping("/api/v1/deleteUser")
    public String deleteUser(@RequestBody User user);
}
