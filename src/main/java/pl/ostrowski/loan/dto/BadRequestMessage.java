package pl.ostrowski.loan.dto;

import org.springframework.http.HttpStatus;

public class BadRequestMessage {

    private String message;

    public HttpStatus getCode() {
        return HttpStatus.BAD_REQUEST;
    }

}
