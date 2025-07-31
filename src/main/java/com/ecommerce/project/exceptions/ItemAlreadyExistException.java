package com.ecommerce.project.exceptions;

public class ItemAlreadyExistException extends RuntimeException{

    String message;

    public ItemAlreadyExistException(){}

    public ItemAlreadyExistException(String message){
        super(message);
        this.message=message;
    }
}
