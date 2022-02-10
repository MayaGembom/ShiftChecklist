package com.MayaGembom.shiftchecklist.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class Assignment {

    private String title = "";
    private boolean visibility;
    private boolean status;
    private String notes;

    public Assignment() {
    }

    public String getTitle() {
        return title;
    }

    public Assignment setTitle(String title) {
        this.title = title;
        return this;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public Assignment setVisibility(boolean visibility) {
        this.visibility = visibility;
        return this;
    }

    public boolean isStatus() {
        return status;
    }

    public Assignment setStatus(boolean status) {
        this.status = status;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public Assignment setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "title='" + title + '\'' +
                ", visibility=" + visibility +
                ", status=" + status +
                ", notes='" + notes + '\'' +
                '}';
    }

}
