package com.deepakallcode.codesnippetmanager.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class BaseController {

    protected <T> ResponseEntity<T> buildOk(T t) {
        return new ResponseEntity<>(t, HttpStatus.OK);
    }

    protected ResponseEntity buildError(Throwable t) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(t);
    }
}
