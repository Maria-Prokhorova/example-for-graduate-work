package ru.skypro.homework.dto.register;

import lombok.Data;

/**
 * DTO для смены пароля пользователя
 */
@Data
public class NewPassword {
    private String currentPassword;
    private String newPassword;
}
