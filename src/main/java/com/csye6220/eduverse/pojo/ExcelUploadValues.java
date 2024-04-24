package com.csye6220.eduverse.pojo;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ExcelUploadValues {
    boolean isEmptySpreadSheet;
    boolean moreThanOneColumn;
    List<String> emailList;
    MultipartFile data;
    Long courseOfferingId;
    public ExcelUploadValues() {
    }

    public ExcelUploadValues(boolean isEmptySpreadSheet, boolean moreThanOneColumn, List<String> emailList, MultipartFile data, Long courseOfferingId) {
        this.isEmptySpreadSheet = isEmptySpreadSheet;
        this.moreThanOneColumn = moreThanOneColumn;
        this.emailList = emailList;
        this.data = data;
        this.courseOfferingId = courseOfferingId;
    }

    public boolean isEmptySpreadSheet() {
        return isEmptySpreadSheet;
    }

    public void setEmptySpreadSheet(boolean emptySpreadSheet) {
        isEmptySpreadSheet = emptySpreadSheet;
    }

    public boolean isMoreThanOneColumn() {
        return moreThanOneColumn;
    }

    public void setMoreThanOneColumn(boolean moreThanOneColumn) {
        this.moreThanOneColumn = moreThanOneColumn;
    }

    public List<String> getEmailList() {
        return emailList;
    }

    public void setEmailList(List<String> emailList) {
        this.emailList = emailList;
    }

    public MultipartFile getData() {
        return data;
    }

    public void setData(MultipartFile data) {
        this.data = data;
    }

    public Long getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(Long courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }
}
