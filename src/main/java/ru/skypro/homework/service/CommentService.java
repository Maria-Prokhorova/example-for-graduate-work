package ru.skypro.homework.service;

import ru.skypro.homework.dto.comment.Comment;
import ru.skypro.homework.dto.comment.Comments;

public interface CommentService {

    Comments getComments(Integer idAd);

    Comment addComment(Integer idAd, String textComment);

    boolean deleteComment(Integer adId, Integer commentId);

    Comment upDateComment(Integer adId, Integer commentId, String textComment);

}
