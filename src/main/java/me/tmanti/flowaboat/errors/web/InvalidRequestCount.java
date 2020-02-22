package me.tmanti.flowaboat.errors.web;

public class InvalidRequestCount extends Exception {
    long clear_in;
    public InvalidRequestCount(String errorMessage, long free_time) {
        super(errorMessage);
        this.clear_in = free_time;
    }
}
