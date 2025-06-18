package ru.skypro.homework.dto.register;

import lombok.Data;

// DTO для входа в систему
@Data
public class Login {

    private String username;
    private String password;
}
