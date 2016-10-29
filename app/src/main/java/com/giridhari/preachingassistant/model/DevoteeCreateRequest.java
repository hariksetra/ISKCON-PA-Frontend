package com.giridhari.preachingassistant.model;

import java.util.Date;

/**
 * Created by shyam on 29/10/16.
 */
public class DevoteeCreateRequest {

    private String initiatedName;
    private Date dob;
    private String gender;
    private String maritalStatus;
    private String familyInfo;
    private String education;
    private String occupation;
    private String organization;
    private String designation;
    private String incomeScale;
    private String smsPhone; //TODO: Create a separate table for phone numbers, which contains number, type of number eg:whatsapp or work, status eg: working or not working
    private Date introDate;
    private String area; //TODO: create a table for area later
    private String address;
    private String email;
    private String capturedFor;
    private String booksRead; //TODO: create a separate table to maintain the list of books read
    private Integer monthlyContribution;
    private String sikshaLevel; //TODO: create an enum for siksha level

    public String getInitiatedName() {
        return initiatedName;
    }

    public void setInitiatedName(String initiatedName) {
        this.initiatedName = initiatedName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getFamilyInfo() {
        return familyInfo;
    }

    public void setFamilyInfo(String familyInfo) {
        this.familyInfo = familyInfo;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getIncomeScale() {
        return incomeScale;
    }

    public void setIncomeScale(String incomeScale) {
        this.incomeScale = incomeScale;
    }

    public String getSmsPhone() {
        return smsPhone;
    }

    public void setSmsPhone(String smsPhone) {
        this.smsPhone = smsPhone;
    }

    public Date getIntroDate() {
        return introDate;
    }

    public void setIntroDate(Date introDate) {
        this.introDate = introDate;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCapturedFor() {
        return capturedFor;
    }

    public void setCapturedFor(String capturedFor) {
        this.capturedFor = capturedFor;
    }

    public String getBooksRead() {
        return booksRead;
    }

    public void setBooksRead(String booksRead) {
        this.booksRead = booksRead;
    }

    public Integer getMonthlyContribution() {
        return monthlyContribution;
    }

    public void setMonthlyContribution(Integer monthlyContribution) {
        this.monthlyContribution = monthlyContribution;
    }

    public String getSikshaLevel() {
        return sikshaLevel;
    }

    public void setSikshaLevel(String sikshaLevel) {
        this.sikshaLevel = sikshaLevel;
    }
}
