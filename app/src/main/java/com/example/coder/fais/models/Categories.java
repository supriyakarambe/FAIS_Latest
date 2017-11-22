package com.example.coder.fais.models;

/**
 * Created by Supriya on 11/19/17.
 */

public class Categories {
    int categoryid;
    String categoryName;
    public Categories() {
    }

    public Categories(int CategoryId,String Name)
    {
        categoryid=CategoryId;
        categoryName=Name;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
