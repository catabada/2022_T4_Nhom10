package entity;

import constant.StatusFileLog;

import java.io.Serializable;

public class FileLog implements Serializable {
    private int id;
    private int fileConfigId;
    private String fileName;
    private String fileDate;
    private String time;
    private StatusFileLog status;
    private String author;

    public FileLog() {

    }

    public FileLog(int fileConfigId, String fileDate, String time, String author) {
        this.fileConfigId = fileConfigId;
        this.fileName = "";
        this.fileDate = fileDate;
        this.time = time;
        this.author = author;
    }

    public FileLog(int id, int fileConfigId, String fileName, String fileDate, String time, StatusFileLog status, String author) {
        this.id = id;
        this.fileConfigId = fileConfigId;
        this.fileName = fileName;
        this.fileDate = fileDate;
        this.time = time;
        this.status = status;
        this.author = author;
    }

    public String getFileDate() {
        return fileDate;
    }

    public void setFileDate(String fileDate) {
        this.fileDate = fileDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFileConfigId() {
        return fileConfigId;
    }

    public void setFileConfigId(int fileConfigId) {
        this.fileConfigId = fileConfigId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public StatusFileLog getStatus() {
        return status;
    }

    public void setStatus(StatusFileLog status) {
        this.status = status;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
