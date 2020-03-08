package me.tmanti.flowaboat.errors.osu;

public class UserError extends Exception {
    public UserError(String errorMessage) {
        super(errorMessage);
    }
}