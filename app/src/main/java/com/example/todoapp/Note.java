package com.example.todoapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "my_notes")
public class Note {
    private String title;
    private String disp;

    @PrimaryKey(autoGenerate=true)
    private Integer id;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Note(String title, String disp) {
        this.title = title;
        this.disp = disp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDisp() {
        return disp;
    }

    public void setDisp(String disp) {
        this.disp = disp;
    }
}
