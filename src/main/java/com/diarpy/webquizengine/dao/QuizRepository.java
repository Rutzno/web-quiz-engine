package com.diarpy.webquizengine.dao;

import com.diarpy.webquizengine.entities.Quiz;
import com.diarpy.webquizengine.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Mack_TB
 * @version 1.0.7
 * @since 12/11/2020
 */

@Repository
public interface QuizRepository extends CrudRepository<Quiz, Long> {

    Page<Quiz> findAll(Pageable pageable);

    @Query("select c from Completion c where c.user = :x order by c.completedAt desc")
    Page<Quiz> findCompletedQuizzesById(@Param("x") User user, Pageable pageable);
}
