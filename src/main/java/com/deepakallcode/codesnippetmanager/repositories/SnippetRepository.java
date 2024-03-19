package com.deepakallcode.codesnippetmanager.repositories;

import com.deepakallcode.codesnippetmanager.entities.Snippet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SnippetRepository extends JpaRepository<Snippet, Long> {

    @Query("SELECT s FROM Snippet s WHERE s.userId = :userId")
    List<Snippet> findSnippetsByUserId(@Param("userId") Long userId);


    List<Snippet> findByType(String type);

}
