package com.scalablebackend.project1;

import java.util.List;

public class Dto {

    public Dto(List<String> top10, Integer total) {
        this.top10 = top10;
        this.total = total;
    }

    private List<String> top10;
    private Integer total;

    public void setTop10(List<String> top10) {
        this.top10 = top10;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<String> getTop10() {
        return top10;
    }

    public Integer getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "{" +
                "top10=" + top10 +
                ", total=" + total +
                '}';
    }
}
