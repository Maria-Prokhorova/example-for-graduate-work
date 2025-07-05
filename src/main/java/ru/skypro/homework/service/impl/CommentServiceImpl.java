package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.comment.Comment;
import ru.skypro.homework.dto.comment.Comments;
import ru.skypro.homework.dto.comment.CreateOrUpdateComment;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.exception.CommentNotFoundException;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.SecurityService;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final AdRepository adRepository;
    private final CommentMapper commentMapper;
    private final SecurityService securityService;

    public CommentServiceImpl(CommentRepository commentRepository, AdRepository adRepository, CommentMapper commentMapper, SecurityService securityService) {
        this.commentRepository = commentRepository;
        this.adRepository = adRepository;
        this.commentMapper = commentMapper;
        this.securityService = securityService;
    }

    /**
     * Получение всех комментариев
     * относящихся к определенному объявлению
     * @param idAd id объявления
     * @return DTO comments
     */
    @Override
    public Comments getComments(Integer idAd) {
        adRepository
                .findById(idAd)
                .orElseThrow(() -> new AdNotFoundException(idAd));

        Comments comments = new Comments();
        comments.setCount(commentRepository.getAmountCommentsByAdID(idAd));
        if (comments.getCount() == 0) {
            throw new CommentNotFoundException(idAd);
        }

        List<Comment> commentList = new ArrayList<>(comments.getCount());
        List<CommentEntity> entities = commentRepository.getListCommentEntityByAdID(idAd);
        entities.stream().map(commentMapper::toCommentDto).forEach(commentList::add);
        comments.setResults(commentList);
        return comments;
    }

    /**
     * Добавление нового комментария
     * к определенному объявлению
     * @param idAd id объявления
     * @param textComment текст комментария
     * @return DTO comment
     */
    @Override
    public Comment addComment(Integer idAd, String textComment) {
        AdEntity adEntity = adRepository
                .findById(idAd)
                .orElseThrow(() -> new AdNotFoundException(idAd));
        CreateOrUpdateComment createComment = new CreateOrUpdateComment();
        UserEntity userEntity = securityService.getCurrentUser();
        createComment.setText(textComment);
        CommentEntity commentEntity = commentMapper.toCommentEntity(
                createComment,
                userEntity,
                adEntity
        );
        commentRepository.save(commentEntity);
        return commentMapper.toCommentDto(commentEntity);
    }

    /**
     * Удаление комментарий
     * к определенному объявлению
     * @param idAd id объявления
     * @param commentId id комментария
     */
    @Override
    public void deleteComment(Integer idAd, Integer commentId) {
        adRepository
                .findById(idAd)
                .orElseThrow(() -> new AdNotFoundException(idAd));
        commentRepository
                .findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
        commentRepository.deleteById(commentId);
    }

    /**
     * Обновление комментария
     * к определенному объявлению
     * @param idAd id объявления
     * @param commentId id комментария
     * @param textComment текст комментария
     * @return DTO comment
     */
    @Override
    public Comment updateComment(Integer idAd, Integer commentId, String textComment) {
        adRepository
                .findById(idAd)
                .orElseThrow(() -> new AdNotFoundException(idAd));
        CommentEntity commentEntity = commentRepository
                .findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));

        CreateOrUpdateComment createComment = new CreateOrUpdateComment();
        commentMapper.updateCommentEntityFromDto(commentEntity, createComment);
        commentRepository.save(commentEntity);
        return commentMapper.toCommentDto(commentEntity);
    }
}
