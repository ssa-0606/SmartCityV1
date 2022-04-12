package com.example.ui_test.pojo;

import java.io.Serializable;
import java.util.List;

public class ItemNewsCategory implements Serializable {

    private int id;
    private String name;
    private int sort;
    private List<ItemNewsList> newsLists;

    public ItemNewsCategory(int id, String name, int sort, List<ItemNewsList> newsLists) {
        this.id = id;
        this.name = name;
        this.sort = sort;
        this.newsLists = newsLists;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public List<ItemNewsList> getNewsLists() {
        return newsLists;
    }

    public void setNewsLists(List<ItemNewsList> newsLists) {
        this.newsLists = newsLists;
    }

    @Override
    public String toString() {
        return "ItemNewsCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", newsLists=" + newsLists +
                '}';
    }
}
