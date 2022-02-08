package com.MayaGembom.shiftchecklist.Objects;

public class Assignment {

    private String description = "";
    private boolean visibility;
    private String department;

    public Assignment() {
    }

    public String getDescription() {
        return description;
    }

    public Assignment setDescription(String description) {
        this.description = description;
        return this;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public Assignment setVisibility(boolean visibility) {
        this.visibility = visibility;
        return this;
    }
}
