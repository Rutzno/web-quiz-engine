package com.diarpy.webquizengine.dao;

import com.diarpy.webquizengine.entities.Completion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Mack_TB
 * @version 1.0.7
 * @since 12/11/2020
 */

@Repository
public interface CompletionRepository extends JpaRepository<Completion, Long> {

}
