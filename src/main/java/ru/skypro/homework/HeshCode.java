package ru.skypro.homework;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HeshCode {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "123456789"; // ваш реальный пароль
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("Новый хеш: " + encodedPassword);
    }
}
