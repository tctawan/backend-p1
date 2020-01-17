package com.scalablebackend.project1;

import org.springframework.http.HttpStatus;

import java.util.List;

public class Dto {

    public Dto(List<String> top10, Integer total, String etag, HttpStatus status) {
        this.top10 = top10;
        this.total = total;
        this.etag = etag;
        this.status = status;
    }

    private List<String> top10;
    private Integer total;
    private String etag;
    private HttpStatus status;

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

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "{" +
                "top10=" + top10 +
                ", total=" + total +
                '}';
    }
}
