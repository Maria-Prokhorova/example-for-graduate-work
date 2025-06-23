package ru.skypro.homework.dto.register;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO для смены пароля пользователя
 */
@Data
public class NewPassword {

    @Schema(description = "текущий пароль", minLength= 8, maxLength= 16)
    private String currentPassword;

    @Schema(description = "новый пароль", minLength= 8, maxLength= 16)
    private String newPassword;
}