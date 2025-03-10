package com.evan.p2pChess;

public class Player {
    private String playerName;
    private Integer playerPoints;
    private Color playerColor;

    public Player() {
        this.playerName = "";
        this.playerPoints = 0;
        this.playerColor = null;
    }

    public Player(String name, Integer points, Color color) {
        this.playerName = name;
        this.playerPoints = points;
        this.playerColor = color;
    }

    //Getters
    public Integer getPlayerPoints() {
        return playerPoints;
    }

    //Setters

    

    public void addPoints(Integer points) {
        this.playerPoints += points;
    }

}
