package com.focuslearn.controller;

import com.focuslearn.model.User;
import com.focuslearn.repository.JourneyRepository;
import com.focuslearn.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AdminControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JourneyRepository journeyRepository;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listUsers_returnsUsers() {
        User u = new User();
        u.setEmail("test@example.com");
        when(userRepository.findAll()).thenReturn(List.of(u));

        ResponseEntity<List<User>> res = adminController.listUsers();
        assertEquals(1, res.getBody().size());
        assertEquals("test@example.com", res.getBody().get(0).getEmail());
    }
}