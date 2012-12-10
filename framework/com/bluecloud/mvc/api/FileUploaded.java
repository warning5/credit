package com.bluecloud.mvc.api;

/**
 * 用来装上传的文件
 */
public class FileUploaded {

    private String filename;
    private byte[] content;

    public FileUploaded(String filename, byte[] content) {
        this.filename = filename;
        this.content = content;
    }

    public String getFilename() {
        return filename;
    }

    public byte[] getContent() {
        return content;
    }

    public String toString() {
        return "Uploaded file, name=" + getFilename();
    }

    public static void main(String[] args) {

    }
}
