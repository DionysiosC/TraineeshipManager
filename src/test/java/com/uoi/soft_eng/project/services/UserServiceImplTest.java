package com.uoi.soft_eng.project.services;

import com.uoi.soft_eng.project.model.User;
import com.uoi.soft_eng.project.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private User mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockUser = new User();
        mockUser.setUsername("john.doe");
        mockUser.setPassword("plaintext");
    }

    @Test
    void testSaveUser_encodesPasswordAndSaves() {
        when(bCryptPasswordEncoder.encode("plaintext")).thenReturn("hashedPassword");

        userService.saveUser(mockUser);

        assertEquals("hashedPassword", mockUser.getPassword());
        verify(userRepository).save(mockUser);
    }

    @Test
    void testIsUserPresent_whenUserExists_returnsTrue() {
        when(userRepository.findByUsername("john.doe")).thenReturn(Optional.of(mockUser));

        boolean exists = userService.isUserPresent(mockUser);

        assertTrue(exists);
    }

    @Test
    void testIsUserPresent_whenUserDoesNotExist_returnsFalse() {
        when(userRepository.findByUsername("john.doe")).thenReturn(Optional.empty());

        boolean exists = userService.isUserPresent(mockUser);

        assertFalse(exists);
    }

    @Test
    void testLoadUserByUsername_returnsUser() {
        when(userRepository.findByUsername("john.doe")).thenReturn(Optional.of(mockUser));

        User result = userService.loadUserByUsername("john.doe");

        assertNotNull(result);
        assertEquals("john.doe", result.getUsername());
    }

    @Test
    void testLoadUserByUsername_returnsNullIfNotFound() {
        when(userRepository.findByUsername("john.doe")).thenReturn(Optional.empty());

        User result = userService.loadUserByUsername("john.doe");

        assertNull(result);
    }

    @Test
    void testFindById_returnsUser() {
        when(userRepository.findById("john.doe")).thenReturn(Optional.of(mockUser));

        User result = userService.findById("john.doe");

        assertNotNull(result);
    }

    @Test
    void testFindById_returnsNullIfNotFound() {
        when(userRepository.findById("john.doe")).thenReturn(Optional.empty());

        User result = userService.findById("john.doe");

        assertNull(result);
    }
}
