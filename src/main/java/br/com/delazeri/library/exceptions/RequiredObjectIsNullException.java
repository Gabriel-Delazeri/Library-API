package br.com.delazeri.library.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredObjectIsNullException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    public RequiredObjectIsNullException(String ex) {
        super(ex);
    }

    public RequiredObjectIsNullException() {
        super("It is not allowed to persist a null object");
    }

}
