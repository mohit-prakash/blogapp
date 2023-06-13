package com.mps.blogapp.exception;

public class NotValidUser extends RuntimeException{
    public NotValidUser(){
        super();
    }
    public NotValidUser(String message){
        super(message);
    }
}
