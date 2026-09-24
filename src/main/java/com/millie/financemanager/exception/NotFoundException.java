package com.millie.financemanager.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiException {

    public NotFoundException(Class<?> classType, Long resourceId) {

        super(classType.getSimpleName() + " " + resourceId +" not found.", HttpStatus.NOT_FOUND);
    }
}
