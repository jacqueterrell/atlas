package com.team.mamba.atlas.data.model.api;


import com.google.firebase.firestore.IgnoreExtraProperties;
import com.team.mamba.atlas.utils.formatData.AppFormatter;

@IgnoreExtraProperties
public class CrmNotes {

    public String id;
    public String subjectID;
    public String authorID;
    public String noteName;
    public String poc;
    public String whereMetCitySt;
    public String whereMetEventName;
    public int howMet;
    public int stage;
    public int type;
    public int oppGoal;
    public String desc;
    public int nextStep;
    public double timestamp; //creation time
    public double closeTimestamp; //close time


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public String getPoc() {
        return poc;
    }

    public void setPoc(String poc) {
        this.poc = poc;
    }

    public String getWhereMetCitySt() {
        return whereMetCitySt;
    }

    public void setWhereMetCitySt(String whereMetCitySt) {
        this.whereMetCitySt = whereMetCitySt;
    }

    public String getWhereMetEventName() {
        return whereMetEventName;
    }

    public void setWhereMetEventName(String whereMetEventName) {
        this.whereMetEventName = whereMetEventName;
    }

    public int getHowMet() {
        return howMet;
    }

    public void setHowMet(int howMet) {
        this.howMet = howMet;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getOppGoal() {
        return oppGoal;
    }

    public void setOppGoal(int oppGoal) {
        this.oppGoal = oppGoal;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getNextStep() {
        return nextStep;
    }

    public void setNextStep(int nextStep) {
        this.nextStep = nextStep;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(double timestamp) {
        this.timestamp = timestamp;
    }

    public double getCloseTimestamp() {
        return closeTimestamp;
    }

    public long getAdjustedTimeStamp(){

        String adjustedTime = AppFormatter.timeStampFormatter.format(timestamp);

        return Long.parseLong(adjustedTime);
    }

    public long getAdjustedCloseTimeStamp(){

        String adjustedTime = AppFormatter.timeStampFormatter.format(closeTimestamp);

        return Long.parseLong(adjustedTime);
    }

    public void setCloseTimestamp(double closeTimestamp) {
        this.closeTimestamp = closeTimestamp;
    }
}
