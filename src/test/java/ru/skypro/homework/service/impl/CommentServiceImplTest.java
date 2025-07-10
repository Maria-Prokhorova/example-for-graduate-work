package ru.skypro.homework.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.homework.dto.comment.Comment;
import ru.skypro.homework.dto.comment.Comments;
import ru.skypro.homework.dto.comment.CreateOrUpdateComment;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.Role;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.exception.AccessDeniedException;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.exception.CommentNotFoundException;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.SecurityService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTest {

    @Mock
    CommentRepository commentRepository;

    @Mock
    AdRepository adRepository;

    @Mock
    CommentMapper commentMapper;

    @Mock
    private SecurityService securityService;

    @InjectMocks
    CommentServiceImpl commentService;

    UserEntity testUserEntity;
    AdEntity testAdEntity;
    CommentEntity testCommentEntity;
    Comment testCommentDTO;
    Comments testCommentsDTO;
    CreateOrUpdateComment testCreateOrUpdateComment;

    /**
     * Подготовка тестовых данных перед каждым методом
     */
    @BeforeEach
    void setUp() {
        testUserEntity = new UserEntity();
        testUserEntity.setId(1);
        testUserEntity.setFirstName("Antony");
        testUserEntity.setLastName("Mackey");
        testUserEntity.setEmail("anmac@example.com");
        testUserEntity.setPassword("password");
        testUserEntity.setPhoneNumber("+9876543210");
        testUserEntity.setRole(Role.USER);
        testUserEntity.setImagePath("/avatar.jpg");

        testAdEntity = new AdEntity();
        testAdEntity.setId(1);
        testAdEntity.setAuthor(testUserEntity);
        testAdEntity.setTitle("Test Ad");
        testAdEntity.setDescription("Test Ad Description");
        testAdEntity.setImagePath("/avatar.jpg");
        testAdEntity.setPrice(10000);

        testCommentEntity = new CommentEntity();
        testCommentEntity.setId(1);
        testCommentEntity.setText("text");
        testCommentEntity.setAuthor(testUserEntity);
        testCommentEntity.setCreatedAt(LocalDateTime.now());
        testCommentEntity.setAd(testAdEntity);

        testAdEntity.setCommentsInAd(Set.of(testCommentEntity));

        testCommentDTO = new Comment();
        testCommentDTO.setAuthor(testCommentEntity.getAuthor().getId());
        testCommentDTO.setText(testCommentEntity.getText());
        testCommentDTO.setAuthorImage(testCommentEntity.getAuthor().getImagePath());
        testCommentDTO.setPk(testCommentEntity.getId());
        testCommentDTO.setAuthorFirstName(testCommentEntity.getAuthor().getFirstName());
        testCommentDTO.setCreatedAt(testCommentEntity.getCreatedAt());

        testCommentsDTO = new Comments();
        testCommentsDTO.setCount(1);
        testCommentsDTO.setResults(List.of(testCommentDTO));
    }

    /**
     * Тест успешного предоставления всех комментариев
     */
    @Test
    void shouldReturnResultOfGetCommentsWhenCommentsExist() {
        when(adRepository.findById(1)).thenReturn(Optional.ofNullable(testAdEntity));
        when(commentRepository.getAmountCommentsByAdID(1))
                .thenReturn(1);
        when(commentRepository.getListCommentEntityByAdID(1))
                .thenReturn(List.of(testCommentEntity));
        when(commentMapper.toCommentDto(testCommentEntity)).thenReturn(testCommentDTO);

        Comments result = commentService.getComments(1);

        assertEquals(testCommentsDTO, result);
        verify(commentRepository).getAmountCommentsByAdID(1);
        verify(commentRepository).getListCommentEntityByAdID(1);
        verify(commentMapper).toCommentDto(testCommentEntity);
    }

    /**
     * Тест на выброс исключения - AdNotFoundException
     */
    @Test
    void shouldReturnResultOfGetCommentsWhenAdDoNotExists() {
        when(adRepository.findById(2)).thenThrow(AdNotFoundException.class);

        assertThrows(AdNotFoundException.class, () -> adRepository.findById(2));
    }

    /**
     * Тест успешного добавления комментария к объявлению
     */
    @Test
    void shouldReturnResultOfAddCommentWhenCommentWasAdded() {
        when(adRepository.findById(1))
                .thenReturn(Optional.of(testAdEntity));
        when(securityService.getCurrentUser())
                .thenReturn(testUserEntity);
        when(commentMapper.toCommentEntity(
                testCreateOrUpdateComment,
                testUserEntity,
                testAdEntity))
                .thenReturn(testCommentEntity);
        when(commentRepository.save(any(CommentEntity.class)))
                .thenReturn(testCommentEntity);
        when(commentMapper.toCommentDto(any(CommentEntity.class))).thenReturn(testCommentDTO);

        assertEquals(testCommentDTO, commentService.addComment(1, testCreateOrUpdateComment));
        verify(securityService).getCurrentUser();
    }

    /**
     * Тест на выброс искючения - AdNotFoundException
     */
    @Test
    void shouldReturnResultOfAddCommentWhenAdNotFound() {
        when(adRepository.findById(2)).thenThrow(AdNotFoundException.class);

        assertThrows(AdNotFoundException.class, () -> adRepository.findById(2));
    }

    /**
     * Тест успешного удаления комментария к объявлению
     */
    @Test
    void shouldReturnResultOfDeleteCommentWhenCommentWasDeleted() {
        when(adRepository.findById(1))
                .thenReturn(Optional.of(testAdEntity));
        when(commentRepository.findById(1))
                .thenReturn(Optional.of(testCommentEntity));
        when(securityService.isOwnerOfComment(any(CommentEntity.class)))
                .thenReturn(true);

        commentService.deleteComment(1, 1);

        verify(commentRepository).deleteById(testCommentEntity.getId());
    }

    /**
     * тест на выброс исключения - AdNotFoundException
     */
    @Test
    void shouldReturnResultOfDeleteCommentWhenAdNotFound() {
        when(adRepository.findById(2)).thenThrow(AdNotFoundException.class);

        assertThrows(
                AdNotFoundException.class,
                () -> commentService.deleteComment(2, 1));
    }

    /**
     * тест на выброс исключения - CommentNotFoundException
     */
    @Test
    void shouldReturnResultOfDeleteCommentWhenCommentNotFound() {
        when(adRepository.findById(1)).thenReturn(Optional.of(testAdEntity));
        when(commentRepository.findById(2)).thenThrow(CommentNotFoundException.class);

        assertThrows(
                CommentNotFoundException.class,
                () -> commentService.deleteComment(1, 2));
    }

    /**
     * тест на выброс исключения при отсутсвии прав на удаление -
     * - AccessDeniedException
     */
    @Test
    void shouldReturnResultOfDeleteCommentWhenNoPermissionToDeleteComment() {
        when(adRepository.findById(1))
                .thenReturn(Optional.of(testAdEntity));
        when(commentRepository.findById(1))
                .thenReturn(Optional.of(testCommentEntity));
        when(securityService.isOwnerOfComment(any(CommentEntity.class)))
                .thenReturn(false);

        assertThrows(
                AccessDeniedException.class,
                () -> commentService.deleteComment(1, 1));
    }

    /**
     * тест успешного изменения комментария к объявлению
     */
    @Test
    void shouldReturnResultOfUpdateCommentWhenCommentWasUpdated() {
        when(adRepository.findById(1))
                .thenReturn(Optional.of(testAdEntity));
        when(commentRepository.findById(1))
                .thenReturn(Optional.of(testCommentEntity));
        when(securityService.isOwnerOfComment(any(CommentEntity.class)))
                .thenReturn(true);
        when(commentRepository.save(any(CommentEntity.class)))
                .thenReturn(testCommentEntity);
        when(commentMapper.toCommentDto(testCommentEntity))
                .thenReturn(testCommentDTO);

        Comment result = commentService.updateComment(
                1,
                1,
                testCreateOrUpdateComment);
        assertEquals(testCommentDTO, result);
    }

    /**
     * тест на выброс исключения - AdNotFoundException
     */
    @Test
    void shouldReturnResultOfUpdateCommentWhenAdNotFound() {
        when(adRepository.findById(2)).thenThrow(AdNotFoundException.class);

        assertThrows(
                AdNotFoundException.class,
                () -> commentService.updateComment(
                        2,
                        1,
                        testCreateOrUpdateComment
                ));
    }

    /**
     * тест на выброс исключения - CommentNotFoundException
     */
    @Test
    void shouldReturnResultOfUpdateCommentWhenCommentNotFound() {
        when(adRepository.findById(1))
                .thenReturn(Optional.of(testAdEntity));
        when(commentRepository.findById(2))
                .thenThrow(CommentNotFoundException.class);

        assertThrows(
                CommentNotFoundException.class,
                () -> commentService.updateComment(
                        1,
                        2,
                        testCreateOrUpdateComment));
    }

    /**
     * тест на выброс исключения при отсутсвии прав на изменение -
     * - AccessDeniedException
     */
    @Test
    void shouldReturnResultOfUpdateCommentWhenNoPermissionToUpdateComment() {
        when(adRepository.findById(1))
                .thenReturn(Optional.of(testAdEntity));
        when(commentRepository.findById(1))
                .thenReturn(Optional.of(testCommentEntity));
        when(securityService.isOwnerOfComment(any(CommentEntity.class)))
                .thenReturn(false);

        assertThrows(
                AccessDeniedException.class,
                () -> commentService.updateComment(
                        1,
                        1,
                        testCreateOrUpdateComment));
    }
}
