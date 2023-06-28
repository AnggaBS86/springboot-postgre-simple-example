package com.angga.springbootjwtmysqlsimple.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFoundException {
    public RecordNotFoundException(String exception) {
        super();
    }
}
