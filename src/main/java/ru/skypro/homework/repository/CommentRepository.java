package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.CommentEntity;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

    @Query(value = "SELECT COUNT(*) FROM comments WHERE ads_id =: adId", nativeQuery = true)
    Integer getAmountCommentsByAdID(@Param("adId") Integer adId);

    @Query(value = "SELECT * FROM comments WHERE ads_id =: adId", nativeQuery = true)
    List<CommentEntity> getListCommentEntityByAdID(@Param("adId") Integer adId);
}
