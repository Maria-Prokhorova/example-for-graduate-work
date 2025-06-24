package ru.skypro.homework.service;

import ru.skypro.homework.dto.comment.CommentDto;
import ru.skypro.homework.dto.comment.CommentsDto;

public interface CommentService {

    CommentsDto getComments(Integer idAd);

    CommentDto addComment(Integer idAd, String textComment);

    boolean deleteComment(Integer adId, Integer commentId);

    CommentDto upDateComment(Integer adId, Integer commentId, String textComment);

}
