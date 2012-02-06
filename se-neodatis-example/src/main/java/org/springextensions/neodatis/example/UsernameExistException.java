package org.springextensions.neodatis.example;

@SuppressWarnings("serial")
public class UsernameExistException extends RuntimeException {

    public UsernameExistException(String message) {
        super(message);
    }

}
