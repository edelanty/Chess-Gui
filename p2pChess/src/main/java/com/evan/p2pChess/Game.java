package com.evan.p2pChess;

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
        Board gameBoard = new Board();
        gameBoard.resetBoard();
        gameBoard.printBoardToTerminal();
    }

    /**
     * 
     * 
     * @return The winner of the game: 1 = P1, 2 = P2, ..., n = Pn
     */
    public Integer isGameWon() {
        //TODO If King is dead opposite side wins

        return getGameWinner();
    }

}
