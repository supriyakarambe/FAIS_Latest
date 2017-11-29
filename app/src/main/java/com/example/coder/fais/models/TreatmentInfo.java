package com.example.coder.fais.models;

import java.util.ArrayList;

/**
 * Created by Supriya on 11/19/17.
 */

public class TreatmentInfo {
    int infoId;
    int subCategoryId;
    ArrayList<String> symptoms;
    ArrayList<String> steps;
    ArrayList<String> FAQs;


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

    public ArrayList<String> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(ArrayList<String> symptoms) {
        this.symptoms = symptoms;
    }

    public ArrayList<String> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<String> steps) {
        this.steps = steps;
    }

    public ArrayList<String> getFAQs() {
        return FAQs;
    }

    public void setFAQs(ArrayList<String> FAQs) {
        this.FAQs = FAQs;
    }
}
