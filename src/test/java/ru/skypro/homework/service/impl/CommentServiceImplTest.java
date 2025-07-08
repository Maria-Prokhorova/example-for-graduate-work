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

    UserEntity testUser;
    AdEntity testAd;
    CommentEntity testCommentEntity;
    Comment testCommentDTO;
    Comments testCommentsDTO;
    CreateOrUpdateComment testCreateOrUpdateComment;

    /**
     * Подготовка тестовых данных перед каждым методом
     */
    @BeforeEach
    void setUp() {
        testUser = new UserEntity();
        testUser.setId(1);
        testUser.setFirstName("Antony");
        testUser.setLastName("Mackey");
        testUser.setEmail("anmac@example.com");
        testUser.setPassword("password");
        testUser.setPhoneNumber("+9876543210");
        testUser.setRole(Role.USER);
        testUser.setImagePath("/avatar.jpg");

        testAd = new AdEntity();
        testAd.setId(1);
        testAd.setAuthor(testUser);
        testAd.setTitle("Test Ad");
        testAd.setDescription("Test Ad Description");
        testAd.setImagePath("/avatar.jpg");
        testAd.setPrice(10000);

        testCommentEntity = new CommentEntity();
        testCommentEntity.setId(1);
        testCommentEntity.setText("text");
        testCommentEntity.setAuthor(testUser);
        testCommentEntity.setCreatedAt(LocalDateTime.now());
        testCommentEntity.setAd(testAd);

        testAd.setCommentsInAd(Set.of(testCommentEntity));

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
     * Тест на выброс исключения - CommentNotFoundException
     */
    @Test
    void shouldReturnResultOfGetCommentsWhenCommentsDoNotExists() {
        when(commentRepository.getAmountCommentsByAdID(2)).thenReturn(0);

        assertThrows(CommentNotFoundException.class, () -> commentService.getComments(2));
    }

    @Test
    void shouldReturnResultOfAddCommentWhenCommentWasAdded() {
        when(adRepository.findById(1))
                .thenReturn(Optional.of(testAd));
        when(securityService.getCurrentUser())
                .thenReturn(testUser);
        when(commentMapper.toCommentEntity(testCreateOrUpdateComment, testUser, testAd))
                .thenReturn(testCommentEntity);
        when(commentRepository.save(any(CommentEntity.class)))
                .thenReturn(testCommentEntity);
        when(commentMapper.toCommentDto(testCommentEntity)).thenReturn(testCommentDTO);

        assertEquals(testCommentDTO, commentService.addComment(1, testCreateOrUpdateComment));

    }
}
