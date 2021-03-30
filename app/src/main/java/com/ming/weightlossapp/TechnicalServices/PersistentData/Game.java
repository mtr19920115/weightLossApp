package com.ming.weightlossapp.TechnicalServices.PersistentData;

import java.io.Serializable;

public class Game implements Serializable {
    double bmi;
    int playNumber;
    int gameId;
    int holsterId;

    public int getHolsterId() {
        return holsterId;
    }

    public void setHolsterId(int holsterId) {
        this.holsterId = holsterId;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public int getPlayNumber() {
        return playNumber;
    }

    public void setPlayNumber(int playNumber) {
        this.playNumber = playNumber;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}
