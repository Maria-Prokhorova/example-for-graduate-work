package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.comment.CommentDto;
import ru.skypro.homework.dto.comment.CommentsDto;
import ru.skypro.homework.service.CommentService;

@Tag(name = "Комментарии", description = "Раздел содержит методы по работе с комментариями объявлений")
@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(summary = "Получение комментариев объявления")
    @GetMapping("/{id}/comments")
    public CommentsDto getComments(@PathVariable Integer id) {
        return commentService.getComments(id);
    }

    @Operation(summary = "Добавление комментария к объявлению")
    @PostMapping("/{id}/comments")
    public CommentDto addComment(@PathVariable Integer id, @RequestBody String textComment) {
       return commentService.addComment(id, textComment);
    }

    @Operation(summary = "Удаление комментария")
    @DeleteMapping("/{adId}/comments/{commentId}")
    public void deleteComment(@PathVariable Integer adId, @PathVariable Integer commentId) {
       commentService.deleteComment(adId, commentId);
    }

    @Operation(summary = "Обновление комментария")
    @PatchMapping("/{adId}/comments/{commentId}")
    public CommentDto updateComment(@PathVariable Integer adId, @PathVariable Integer commentId, @RequestBody String textComment) {
        return commentService.upDateComment(adId, commentId, textComment);
    }
}
