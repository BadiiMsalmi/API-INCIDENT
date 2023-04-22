package com.api.backincdidents.Dto;

import java.util.List;

public class FilterDto {

    private List<WhereDto> where;

    private Integer skip;

    //private Integer limit;

    public Integer getSkip() {

        return this.skip;

    }

    public void setSkip(Integer skip) {

        this.skip = skip;

    }

    /*public Integer getLimit() {

        return this.limit;

    }*/

    /*public void setLimit(Integer limit) {

        this.limit = limit;

    }*/

    public List<WhereDto> getWhere() {

        return this.where;

    }

    public void setWhere(List<WhereDto> where) {

        this.where = where;

    }

}