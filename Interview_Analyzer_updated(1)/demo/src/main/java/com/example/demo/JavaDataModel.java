package com.example.demo;

import java.util.Date;

public class JavaDataModel {
    private Date date;
    private String month;
    private String team;
    private String panelName;
    private String round;
    private String skill;
    private String time;
    private String candidateCurrentLoc;
    private String candidatePreferredLoc;
    private String candidateName;

    public void setMonth(String month) {
        this.month = month;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void setPanelName(String panelName) {
        this.panelName = panelName;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setCandidateCurrentLoc(String candidateCurrentLoc) {
        this.candidateCurrentLoc = candidateCurrentLoc;
    }



    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public Date getDate() {

        return date;
    }
    public java.sql.Date getSqlDate()
    {
        long utilDateMillis = date.getTime();
        java.sql.Date sqlDate = new java.sql.Date(utilDateMillis);
        return  sqlDate;
    }

    public String getMonth() {
        return month;
    }

    public String getTeam() {
        return team;
    }

    public String getPanelName() {
        return panelName;
    }


    public String getRound() {
        return round;
    }

    public String getSkill() {
        return skill;
    }


    public String getTime() {
        return time;
    }

    public String getCandidateCurrentLoc() {
        return candidateCurrentLoc;
    }

    public String getCandidatePreferredLoc() {
        return candidatePreferredLoc;
    }


    public String getCandidateName() {
        return candidateName;
    }


    public void setCandidatePreferredLOC(String valueOfCell) {
        this.candidatePreferredLoc=valueOfCell;
    }

    public void setCandidateCurrentLOC(String valueOfCell) {
        this.candidateCurrentLoc=valueOfCell;
    }

    public void setCandidatePreferredLoc(String cellValue) {
        this.candidatePreferredLoc=cellValue;
    }
}
