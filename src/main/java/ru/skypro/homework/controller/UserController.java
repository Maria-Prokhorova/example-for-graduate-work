package ru.skypro.homework.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.register.NewPassword;
import ru.skypro.homework.dto.register.UpdateUser;
import ru.skypro.homework.dto.user.User;
import ru.skypro.homework.service.UserService;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/set_password")
    public ResponseEntity<?> updatePassword(@RequestBody NewPassword newPassword) {
        if (userService.updatePassword(newPassword)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getInfoAboutUser() {
        User infoAboutUser = userService.getInfoAboutUser();
        if (infoAboutUser != null) {
            return new ResponseEntity<>(infoAboutUser, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PatchMapping("/me")
    public ResponseEntity<?> updateInfoAboutUser(@RequestBody UpdateUser newInfoUser) {
        UpdateUser updateUser = userService.updateInfoAboutUser(newInfoUser);
        if (updateUser != null) {
            return new ResponseEntity<>(updateUser, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PatchMapping("me/image")
    public ResponseEntity<?> updateAvatarUser(@RequestBody String image) {
        if (userService.updateAvatarUser(image)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
