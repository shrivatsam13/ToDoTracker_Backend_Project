package com.project.UserAuthenticationApp.service;

import com.project.UserAuthenticationApp.domain.User;
import com.project.UserAuthenticationApp.exception.UserAlreadyExistsException;
import com.project.UserAuthenticationApp.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;
    private User user;

    @BeforeEach
    public void setUp() throws Exception {
        user = new User("U1234", "JOHN123");
    }
    @AfterEach
    public void tearDown() throws Exception {
        user = null;
    }
    @Test
    public void givenCustomerToSaveReturnSavedCustomerSuccess() throws UserAlreadyExistsException {
        when(userRepository.findById(anyString())).thenReturn(Optional.ofNullable(null));
        when(userRepository.save(any())).thenReturn(user);
        assertEquals(user,userService.saveUser(user));
        verify(userRepository,times(1)).save(any());
        verify(userRepository,times(1)).findById(any());
    }

    @Test
    public void givenUserToSaveReturnSavedUserFailure() throws UserAlreadyExistsException {
        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(user));
        assertThrows(UserAlreadyExistsException.class,()->userService.saveUser(user));
        verify(userRepository,times(1)).findById(any());
        verify(userRepository,times(0)).save(any());
    }
    @Test
    public void givenUserLoginSuccessReturnRetrievedUser()
    {
        when(userRepository.findByUserEmailAndPassword(anyString(),any())).thenReturn(user);

        assertEquals(user,userRepository.findByUserEmailAndPassword(user.getUserEmail(),user.getPassword()));
        verify(userRepository,times(1)).findByUserEmailAndPassword(anyString(),any());
    }

}