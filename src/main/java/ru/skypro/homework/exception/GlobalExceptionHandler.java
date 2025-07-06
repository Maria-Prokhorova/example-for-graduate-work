package ru.skypro.homework.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Глобальный обработчик исключений.
 * Преобразует исключения приложения в соответствующие HTTP статусы.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Обработка исключения, когда пользователь не найден.
     *
     * @param e исключение
     * @return ResponseEntity с статусом 404
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
        log.error("Пользователь не найден: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    /**
     * Обработка исключения, когда объявление не найдено.
     *
     * @param e исключение
     * @return ResponseEntity с статусом 404
     */
    @ExceptionHandler(AdNotFoundException.class)
    public ResponseEntity<String> handleAdNotFoundException(AdNotFoundException e) {
        log.error("Объявление не найдено: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    /**
     * Обработка исключения, когда комментарий не найден.
     *
     * @param e исключение
     * @return ResponseEntity с статусом 404
     */
    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<String> handleCommentNotFoundException(CommentNotFoundException e) {
        log.error("Комментарий не найден: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    /**
     * Обработка исключения, когда изображение не найдено.
     *
     * @param e исключение
     * @return ResponseEntity с статусом 404
     */
    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<String> handleImageNotFoundException(ImageNotFoundException e) {
        log.error("Изображение не найдено: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    /**
     * Обработка исключения, когда пользователь уже существует.
     *
     * @param e исключение
     * @return ResponseEntity с статусом 400
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        log.error("Пользователь уже существует: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    /**
     * Обработка исключения, когда пароль неверный.
     *
     * @param e исключение
     * @return ResponseEntity с статусом 401
     */
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<String> handleInvalidPasswordException(InvalidPasswordException e) {
        log.error("Неверный пароль: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    /**
     * Обработка исключения, когда доступ запрещен.
     *
     * @param e исключение
     * @return ResponseEntity с статусом 403
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException e) {
        log.error("Доступ запрещен: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    /**
     * Обработка исключения аутентификации.
     *
     * @param e исключение
     * @return ResponseEntity с статусом 401
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        log.error("Ошибка аутентификации: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    /**
     * Обработка общих исключений.
     *
     * @param e исключение
     * @return ResponseEntity с статусом 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception e) {
        log.error("Неожиданная ошибка: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Произошла внутренняя ошибка сервера");
    }
}