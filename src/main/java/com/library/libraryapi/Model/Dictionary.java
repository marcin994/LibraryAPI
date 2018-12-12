package com.library.libraryapi.Model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Dictionary {

    @Id
    @GeneratedValue
    @NotNull
    private long id;

    @NotNull
    private String name;

    @NotNull
    private String code;

    @OneToMany (fetch = FetchType.LAZY)
    private List<DictionaryItem> items;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<DictionaryItem> getItems() {
        return items;
    }

    public void setItems(List<DictionaryItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Dictionary{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
