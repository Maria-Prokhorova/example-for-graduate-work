package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.comment.CommentDto;
import ru.skypro.homework.dto.comment.CommentsDto;
import ru.skypro.homework.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

    @Override
    public CommentsDto getComments(Integer idAd) {
        return null;
    }

    @Override
    public CommentDto addComment(Integer idAd, String textComment) {
        return null;
    }

    @Override
    public boolean deleteComment(Integer adId, Integer commentId) {
        return true;
    }

    @Override
    public CommentDto upDateComment(Integer adId, Integer commentId, String textComment) {
        return null;
    }
}
