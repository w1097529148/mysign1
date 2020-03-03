package com.mysign.service.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ExceptionResult implements Serializable {
    private int status;
    private String message;
    private Long timestamp;

    public ExceptionResult(ExceptionEnum em) {
        this.status = em.getStatus();
        this.message = em.getMessage();
        this.timestamp=System.currentTimeMillis();
    }
}
