package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.comment.Comment;
import ru.skypro.homework.dto.comment.Comments;
import ru.skypro.homework.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

    @Override
    public Comments getComments(Integer idAd) {
        return null;
    }

    @Override
    public Comment addComment(Integer idAd, String textComment) {
        return null;
    }

    @Override
    public boolean deleteComment(Integer adId, Integer commentId) {
        return true;
    }

    @Override
    public Comment upDateComment(Integer adId, Integer commentId, String textComment) {
        return null;
    }
}
