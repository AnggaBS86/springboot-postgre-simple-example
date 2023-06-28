package com.angga.springbootjwtmysqlsimple.api.payloads.response;

public class SignupResponse {

    private String success;

    public SignupResponse(String success) {
        super();
        this.success = success;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

}
