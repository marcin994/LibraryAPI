package com.library.libraryapi.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Resources {

    @Id
    @GeneratedValue
    @NotNull
    private long id;

    @NotNull
    private String name;

    @NotNull
    private String languageCode;

    @NotNull
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Resources{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", languageCode='" + languageCode + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
