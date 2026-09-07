package com.aicodereview.platform.submission;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateSubmissionRequest {

    @NotBlank(message = "Code is required")
    @Size(min = 1, max = 51200, message = "Code must be 1-51200 characters")
    private String code;

    public CreateSubmissionRequest() {}

    public CreateSubmissionRequest(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
