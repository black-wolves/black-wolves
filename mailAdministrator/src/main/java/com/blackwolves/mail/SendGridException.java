package com.blackwolves.mail;

public class SendGridException extends Exception {
    public SendGridException(Exception e) {
        super(e);
    }
}
