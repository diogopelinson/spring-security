package br.com.devpelinson.spring_boot_essentials.exception;

public class BadRequestException extends Exception{

    public BadRequestException(String message){
        super(message);
    }

}
