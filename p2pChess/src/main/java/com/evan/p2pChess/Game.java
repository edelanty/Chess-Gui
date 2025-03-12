package com.evan.p2pChess;

import com.evan.p2pChess.Gui.*;

public class Game {
    private Integer gameTime;
    private Integer gameWinner;

    public Game() {
        this.gameTime = 0;
        this.gameWinner = 0;
    }

    //Getters
    public Integer getGameWinner() {
        return gameWinner;
    }

    public Integer getGameTime() {
        return gameTime;
    }

    //Setters
    public void setGameWinner(Integer winner) {
        gameWinner = winner;
    }

    public void setGameTime(Integer time) {
        gameWinner = time;
    }

    public void gameStart() {
        //Creates and runs the panels and event listeners for the guis
        Gui gui = new Gui();
        gui.runGui();
    }

}
