package com.example.demo.dto;

public class OrderDTO {

    private String code;
    private Integer numOfCartons;
    private Integer numOfUnits;

    public OrderDTO(String code, Integer numOfCartons, Integer numOfUnits) {
        this.code = code;
        this.numOfCartons = numOfCartons;
        this.numOfUnits = numOfUnits;
    }

    public String getCode() {
        return code;
    }

    public Integer getNumOfCartons() {
        return numOfCartons;
    }

    public Integer getNumOfUnits() {
        return numOfUnits;
    }

    @Override
    public String toString() {
        return "{" +
                "code = \"" + code + '\"' +
                ", numOfCartons = " + numOfCartons +
                ", numOfUnits = " + numOfUnits +
                '}';
    }
}
