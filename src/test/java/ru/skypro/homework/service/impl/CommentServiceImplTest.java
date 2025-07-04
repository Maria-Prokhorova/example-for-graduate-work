package ru.skypro.homework.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.homework.dto.comment.Comment;
import ru.skypro.homework.dto.comment.Comments;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.Role;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.CommentRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTest {

    @Mock
    CommentRepository commentRepository;

    @Mock
    CommentMapper commentMapper;

    @InjectMocks
    CommentServiceImpl commentService;

    UserEntity testUser;
    AdEntity testAd;
    CommentEntity testComment;
    Comment testCommentDTO;
    Comments testCommentsDTO;

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

        testComment = new CommentEntity();
        testComment.setId(1);
        testComment.setText("text");
        testComment.setAuthor(testUser);
        testComment.setCreatedAt(LocalDateTime.now());
        testComment.setAd(testAd);

        testAd.setCommentsInAd(Set.of(testComment));

        testCommentDTO = new Comment();
        testCommentDTO.setAuthor(testComment.getAuthor().getId());
        testCommentDTO.setText(testComment.getText());
        testCommentDTO.setAuthorImage(testComment.getAuthor().getImagePath());
        testCommentDTO.setPk(testComment.getId());
        testCommentDTO.setAuthorFirstName(testComment.getAuthor().getFirstName());
        testCommentDTO.setCreatedAt(testComment.getCreatedAt());

        testCommentsDTO = new Comments();
        testCommentsDTO.setCount(1);
        testCommentsDTO.setResults(List.of(testCommentDTO));
    }

    @Test
    void shouldReturnResultOfGetCommentsWhenCommentsIsExists() {
        when(commentRepository.getAmountCommentsByAdID(1))
                .thenReturn(1);
        when(commentRepository.getListCommentEntityByAdID(1))
                .thenReturn(List.of(testComment));
        when(commentMapper.toCommentDto(testComment)).thenReturn(testCommentDTO);

        Comments result = commentService.getComments(1);

        assertEquals(testCommentsDTO, result);
        verify(commentRepository).getAmountCommentsByAdID(1);
        verify(commentRepository).getListCommentEntityByAdID(1);
        verify(commentMapper).toCommentDto(testComment);
    }
}
