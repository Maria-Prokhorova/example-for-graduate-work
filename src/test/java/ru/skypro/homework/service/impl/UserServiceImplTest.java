package ru.skypro.homework.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.skypro.homework.dto.register.NewPassword;
import ru.skypro.homework.dto.register.UpdateUser;
import ru.skypro.homework.dto.user.User;
import ru.skypro.homework.entity.Role;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.SecurityService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Основные тесты для UserServiceImpl.
 * Тестирует успешные сценарии для всех публичных методов UserService.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private SecurityService securityService;

    @InjectMocks
    private UserServiceImpl userService;

    private UserEntity testUser;
    private User testUserDto;
    private UpdateUser testUpdateUser;
    private NewPassword testNewPassword;

    /**
     * Подготовка тестовых данных перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        testUser = new UserEntity();
        testUser.setId(1);
        testUser.setFirstName("John");
        testUser.setLastName("Doe");
        testUser.setEmail("john@example.com");
        testUser.setPassword("encodedPassword");
        testUser.setPhoneNumber("+1234567890");
        testUser.setRole(Role.USER);
        testUser.setImagePath("/avatar.jpg");

        testUserDto = new User();
        testUserDto.setId(1);
        testUserDto.setFirstName("John");
        testUserDto.setLastName("Doe");
        testUserDto.setEmail("john@example.com");
        testUserDto.setPhone("+1234567890");
        testUserDto.setRole(Role.USER.name());
        testUserDto.setImage("/avatar.jpg");

        testUpdateUser = new UpdateUser();
        testUpdateUser.setFirstName("Jane");
        testUpdateUser.setLastName("Smith");
        testUpdateUser.setPhone("+0987654321");

        testNewPassword = new NewPassword();
        testNewPassword.setCurrentPassword("oldPassword");
        testNewPassword.setNewPassword("newPassword");
    }

    /**
     * Тест успешного обновления пароля пользователя.
     */
    @Test
    void updatePassword_WithValidPassword_ShouldUpdatePassword() {
        when(securityService.getCurrentUser()).thenReturn(testUser);
        when(passwordEncoder.matches("oldPassword", "encodedPassword")).thenReturn(true);
        when(passwordEncoder.encode("newPassword")).thenReturn("newEncodedPassword");
        when(userRepository.save(any(UserEntity.class))).thenReturn(testUser);

        userService.updatePassword(testNewPassword);

        verify(securityService).getCurrentUser();
        verify(passwordEncoder).matches("oldPassword", "encodedPassword");
        verify(passwordEncoder).encode("newPassword");
        verify(userRepository).save(testUser);
        assertEquals("newEncodedPassword", testUser.getPassword());
    }

    /**
     * Тест получения информации о текущем пользователе.
     */
    @Test
    void getInfoAboutUser_ShouldReturnUserInfo() {
        when(securityService.getCurrentUser()).thenReturn(testUser);
        when(userMapper.toUserDto(testUser)).thenReturn(testUserDto);

        User result = userService.getInfoAboutUser();

        assertNotNull(result);
        assertEquals(testUserDto, result);
        verify(securityService).getCurrentUser();
        verify(userMapper).toUserDto(testUser);
    }

    /**
     * Тест успешного обновления информации о пользователе.
     */
    @Test
    void updateInfoAboutUser_ShouldReturnUpdatedUser() {
        when(securityService.getCurrentUser()).thenReturn(testUser);
        when(userRepository.save(any(UserEntity.class))).thenReturn(testUser);

        UpdateUser result = userService.updateInfoAboutUser(testUpdateUser);

        assertNotNull(result);
        assertEquals(testUpdateUser, result);
        verify(securityService).getCurrentUser();
        verify(userMapper).updateUserEntityFromDto(testUser, testUpdateUser);
        verify(userRepository).save(testUser);
    }

    /**
     * Тест обновления информации о пользователе с null параметром.
     */
    @Test
    void updateInfoAboutUser_WithNullUpdateUser_ShouldHandleGracefully() {
        when(securityService.getCurrentUser()).thenReturn(testUser);
        when(userRepository.save(any(UserEntity.class))).thenReturn(testUser);

        UpdateUser result = userService.updateInfoAboutUser(null);

        assertNull(result);
        verify(securityService).getCurrentUser();
        verify(userMapper).updateUserEntityFromDto(testUser, null);
        verify(userRepository).save(testUser);
    }
} 