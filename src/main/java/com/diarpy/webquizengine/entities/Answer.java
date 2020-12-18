package com.diarpy.webquizengine.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mack_TB
 * @version 1.0.7
 * @since 12/11/2020
 */

public class Answer {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int[] answer;
    private boolean success;
    private String feedback;

    public Answer() { }

    public Answer(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }

    public int[] getAnswer() {
        return answer;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer.clone();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
