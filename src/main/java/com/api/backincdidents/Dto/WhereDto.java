package com.api.backincdidents.Dto;

import java.util.List;

public class WhereDto {
    private String field;

    private String operator;

    private List<String> modalities;

 

    public String getField() {

        return this.field;

    }

 

    public void setField(String field) {

        this.field = field;

    }

 

    public String getOperator() {

        return this.operator;

    }

 

    public void setOperator(String operator) {

        this.operator = operator;

    }

 

    public List<String> getModalities() {

        return this.modalities;

    }

 

    public void setModalities(List<String> modalities) {

        this.modalities = modalities;

    }
}
