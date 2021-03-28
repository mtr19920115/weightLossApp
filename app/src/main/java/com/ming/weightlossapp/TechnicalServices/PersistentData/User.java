package com.ming.weightlossapp.TechnicalServices.PersistentData;

import java.io.Serializable;

public class User implements Serializable {
    int uid;
    String userName;
    String passWord;
    String email;
    String twitterAccount;
    double height;
    double weight;
    double BMI;
    boolean joinedGame;
    int joinedGameId;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTwitterAccount() {
        return twitterAccount;
    }

    public void setTwitterAccount(String twitterAccount) {
        this.twitterAccount = twitterAccount;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getBMI() {
        return BMI;
    }

    public void setBMI(double BMI) {
        this.BMI = BMI;
    }

    public boolean isJoinedGame() {
        return joinedGame;
    }

    public void setJoinedGame(boolean joinedGame) {
        this.joinedGame = joinedGame;
    }

    public int getJoinedGameId() {
        return joinedGameId;
    }

    public void setJoinedGameId(int joinedGameId) {
        this.joinedGameId = joinedGameId;
    }
}
