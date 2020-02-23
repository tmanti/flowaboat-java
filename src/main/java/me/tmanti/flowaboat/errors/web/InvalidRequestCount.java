package me.tmanti.flowaboat.errors.web;

public class InvalidRequestCount extends Exception {
    public InvalidRequestCount(String errorMessage, long free_time) {
        super(errorMessage, new Throwable(String.valueOf(free_time)));
    }
}
