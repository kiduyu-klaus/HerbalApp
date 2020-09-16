package com.kiduyu.joshuaproject.HerbalApp.Models;

/**
 * Created by Kiduyu klaus
 * on 13/09/2020 23:14 2020
 */
public class ChatMessage {
    private boolean isImage, isMine;
    private String content;

    public ChatMessage(String message, boolean mine, boolean image) {
        content = message;
        isMine = mine;
        isImage = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setIsMine(boolean isMine) {
        this.isMine = isMine;
    }

    public boolean isImage() {
        return isImage;
    }

    public void setIsImage(boolean isImage) {
        this.isImage = isImage;
    }
}
