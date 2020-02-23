package me.tmanti.flowaboat.utils;

import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

public class ApiResponse{
    private String content;
    private Response response;
    public ApiResponse(Response response) {
        this.response = response;
    }
    public String getContent() throws IOException {
        if (this.content == null){
            assert this.response.body() != null;
            this.content = this.response.body().string();
        }
        return this.content;
    }
    public boolean isSuccessful(){
        return this.response.isSuccessful();
    }
    public ResponseBody body(){
        return this.response.body();
    }
    // add more of these methods if you need something else
}
