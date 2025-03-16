package com.evan.p2pChess;

import java.util.LinkedList;
import java.util.Queue;

public class Player {
    private String playerName;
    private Integer playerPoints;
    private Color playerColor;
    private Queue<String> playerMoves;
    private Queue<String> playerCapturedPieces;

    public Player() {
        this.playerName = "";
        this.playerPoints = 0;
        this.playerColor = null;
        this.playerMoves = new LinkedList<>();
        this.playerCapturedPieces = new LinkedList<>();
    }

    public Player(String name, Integer points, Color color) {
        this.playerName = name;
        this.playerPoints = points;
        this.playerColor = color;
        this.playerMoves = new LinkedList<>();
        this.playerCapturedPieces = new LinkedList<>();
    }

    //Getters
    public Integer getPlayerPoints() {
        return playerPoints;
    }

    public String getPlayerName() {
        return playerName;
    }

    /**
     * addPlayerMove()
     * 
     * Adds a new move string to the move queue.
     * 
     * @param move
     */
    public void addPlayerMove(String move) {
        playerMoves.add(move);
    }

    /**
     * addPlayerCapturedPiece()
     * 
     * Adds a captured piece into the captured piece queue.
     * 
     * @param points
     */
    public void addPlayerCapturedPiece(String capturedPieceString) {
        playerCapturedPieces.add(capturedPieceString);
    }

    /**
     * addPoints()
     * 
     * Add the given points to the playerPoints variable.
     * 
     * @param points
     */
    public void addPoints(Integer points) {
        this.playerPoints += points;
    }

}
