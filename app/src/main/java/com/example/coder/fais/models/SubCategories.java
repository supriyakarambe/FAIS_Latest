package com.example.coder.fais.models;

/**
 * Created by Supriya on 11/19/17.
 */

public class SubCategories {

    int subCategoryId;
    int categoryId;
    String subCategoryName;
    public SubCategories(int id,String name)
    {
        this.subCategoryId=id;
        this.subCategoryName=name;
    }


    public int getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }


}
