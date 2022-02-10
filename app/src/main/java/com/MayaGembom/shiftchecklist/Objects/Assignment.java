package com.MayaGembom.shiftchecklist.Objects;

public class Assignment {

    private String title;
    private boolean visibility;
    private String status;
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

    public String getStatus() {
        return status;
    }

    public Assignment setStatus(String status) {
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
