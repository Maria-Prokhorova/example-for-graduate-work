package ru.skypro.homework.dto.register;

import lombok.Data;
import ru.skypro.homework.dto.Role;

// DTO для регистрации нового пользователя
@Data
public class Register {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
}
