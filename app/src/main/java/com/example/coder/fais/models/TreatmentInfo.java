package com.example.coder.fais.models;

/**
 * Created by Supriya on 11/19/17.
 */

public class TreatmentInfo {
    int infoId;
    int subCategoryId;
    String symptoms;
    String steps;
    String FAQs;


    public int getInfoId() {
        return infoId;
    }

    public void setInfoId(int infoId) {
        this.infoId = infoId;
    }

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getFAQs() {
        return FAQs;
    }

    public void setFAQs(String FAQs) {
        this.FAQs = FAQs;
    }
}
