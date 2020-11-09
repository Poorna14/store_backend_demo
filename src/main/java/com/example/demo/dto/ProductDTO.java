package com.example.demo.dto;

public class ProductDTO {

    private static final long serialVersionUID = -329404315134592310L;

    private long id;
    private String name;
    private String code;

    public ProductDTO(long id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "{" +
                "id = " + id +
                ", name = \"" + name + '\"' +
                ", code = \"" + code + '\"' +
                '}';
    }

}
