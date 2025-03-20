package com.evan.p2pChess;

import com.evan.p2pChess.Gui.*;

import java.awt.CardLayout;

import javax.swing.*;

public class Game {
    private Integer gameTime;
    private Integer gameWinner;

    private Timer blackTimer;
    private Timer whiteTimer;
    private int blackTimeSeconds;
    private int whiteTimeSeconds;

    private JLabel blackTimerLabel;
    private JLabel whiteTimerLabel;

    public Game() {
        this.gameTime = 0;
        this.gameWinner = 0;
    }

    // Getters
    public Integer getGameWinner() {
        return gameWinner;
    }

    public Integer getGameTime() {
        return gameTime;
    }

    // Setters
    public void setGameWinner(Integer winner) {
        gameWinner = winner;
    }

    public void setGameTime(Integer time) {
        gameTime = time;
    }

    public void gameStart() {
        //Creates and runs the panels and event listeners for the GUIs
        Gui gui = new Gui();
        gui.runGui(this);
    }

    public void initializeTimers(JLabel blackTimerLabel, JLabel whiteTimerLabel, int initialMinutes) {
        this.blackTimerLabel = blackTimerLabel;
        this.whiteTimerLabel = whiteTimerLabel;

        blackTimeSeconds = initialMinutes * 60;
        whiteTimeSeconds = initialMinutes * 60;

        blackTimer = new Timer(1000, e -> {
            blackTimeSeconds--;
            updateTimerLabel(blackTimerLabel, blackTimeSeconds);
            if (blackTimeSeconds <= 0) {
                blackTimer.stop();
                whiteTimer.stop();
                showGameOver("White wins on time!");
            }
        });

        whiteTimer = new Timer(1000, e -> {
            whiteTimeSeconds--;
            updateTimerLabel(whiteTimerLabel, whiteTimeSeconds);
            if (whiteTimeSeconds <= 0) {
                whiteTimer.stop();
                blackTimer.stop();
                showGameOver("Black wins on time!");
            }
        });

        updateTimerLabel(blackTimerLabel, blackTimeSeconds);
        updateTimerLabel(whiteTimerLabel, whiteTimeSeconds);
    }

    private void updateTimerLabel(JLabel label, int seconds) {
        int mins = seconds / 60;
        int secs = seconds % 60;
        String prefix = label == blackTimerLabel ? "Black" : "White";
        label.setText(String.format("%s: %02d:%02d", prefix, mins, secs));
    }

    public void switchTurns(boolean isWhiteTurn) {
        if (isWhiteTurn) {
            blackTimer.stop();
            whiteTimer.start();
        } else {
            whiteTimer.stop();
            blackTimer.start();
        }
    }

    public void startGameClock(boolean isWhiteTurn) {
        if (isWhiteTurn) {
            whiteTimer.start();
        } else {
            blackTimer.start();
        }
    }

    public void pauseClocks() {
        whiteTimer.stop();
        blackTimer.stop();
    }

    public void resumeClock(boolean isWhiteTurn) {
        if (isWhiteTurn) {
            whiteTimer.start();
        } else {
            blackTimer.start();
        }
    }

    public void showGameOver(String message) {
        whiteTimer.stop();
        blackTimer.stop();
        JOptionPane.showMessageDialog(null, message);
    }

    public int getBlackTimeSeconds() {
        return blackTimeSeconds;
    }

    public int getWhiteTimeSeconds() {
        return whiteTimeSeconds;
    }
} 
