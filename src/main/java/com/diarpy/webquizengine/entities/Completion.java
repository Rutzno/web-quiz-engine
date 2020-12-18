package com.diarpy.webquizengine.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Mack_TB
 * @version 1.0.7
 * @since 12/11/2020
 */

@Entity
public class Completion implements Serializable {

    @Id @GeneratedValue
    @JsonIgnore
    private Long generatedId;

    @JsonProperty("id")
    private Long quizId;

    private Date completedAt;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Completion() { }

    public Completion(User user, Long quizId, Date completedAt) {
        this.user = user;
        this.quizId = quizId;
        this.completedAt = completedAt;
    }


    public Long getGeneratedId() {
        return generatedId;
    }

    public void setGeneratedId(Long generatedId) {
        this.generatedId = generatedId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonProperty("id")
    public Long getQuizId() {
        return quizId;
    }

    @JsonProperty("id")
    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }
}
