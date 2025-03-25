package com.evan.p2pChess;

import javax.swing.*;

import com.evan.p2pChess.Gui.Gui;
import com.evan.p2pChess.Gui.Settings;

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

    //Getters
    public Integer getGameWinner() {
        return gameWinner;
    }

    public Integer getGameTime() {
        return gameTime;
    }

    /**
     * Sets the winner of the game.
     */
    public void setGameWinner(Integer winner) {
        gameWinner = winner;
    }

    /**
     * setGameTime()
     * 
     * Sets the total game time in minutes.
     * 
     * @param time
     */
    public void setGameTime(Integer time) {
        gameTime = time;
    }

    /**
     * gameStart()
     * 
     * Starts the game GUI.
     * 
     */
    public void gameStart() {
        Gui gui = new Gui();
        gui.runGui(this);
    }

    /**
     * initializeTimers()
     * 
     * Initializes the game clocks with the given initial time and associates them with labels.
     * 
     */
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
                gameOver();
            }
        });

        whiteTimer = new Timer(1000, e -> {
            whiteTimeSeconds--;
            updateTimerLabel(whiteTimerLabel, whiteTimeSeconds);
            if (whiteTimeSeconds <= 0) {
                whiteTimer.stop();
                blackTimer.stop();
                gameOver();
            }
        });

        updateTimerLabel(blackTimerLabel, blackTimeSeconds);
        updateTimerLabel(whiteTimerLabel, whiteTimeSeconds);
    }

    /**
     * updateTimerLabel()
     * 
     * Updates a timer label text and visual styling.
     * 
     */
    private void updateTimerLabel(JLabel label, int seconds) {
        int mins = seconds / 60;
        int secs = seconds % 60;
        String prefix = label == blackTimerLabel ? "Black" : "White";
        label.setText(String.format("%s: %02d:%02d", prefix, mins, secs));

        boolean isActive = (label == blackTimerLabel && blackTimer.isRunning()) || (label == whiteTimerLabel && whiteTimer.isRunning());

        if (isActive) {
            //Active timer, darker background
            label.setBackground(Settings.GRAY_DARK_BOX_COLOR);
            label.setForeground(java.awt.Color.WHITE);

            if (seconds == 10) { //Play sound if at 10 seconds
                SoundManager.play(getClass().getResource("/com/evan/p2pChess/Gui/Sounds/tenseconds.wav"));
            }
    
            if (seconds <= 10) {
                //Low time: red text
                label.setForeground(Settings.RED_DARK_COLOR);
            }
        } else {
            //Inactive timer: lighter background
            label.setBackground(Settings.GRAY_LIGHT_BOX_COLOR);
            label.setForeground(Settings.GRAY_LIGHT_COLOR);
        }
    
        label.setOpaque(true);
    }

    /**
     * switchTurns()
     * 
     * Switches the active turn and updates which timer is running.
     * 
     */
    public void switchTurns(boolean isWhiteTurn) {
        if (isWhiteTurn) {
            blackTimer.stop();
            whiteTimer.start();
        } else {
            whiteTimer.stop();
            blackTimer.start();
        }
        updateTimerLabel(blackTimerLabel, blackTimeSeconds);
        updateTimerLabel(whiteTimerLabel, whiteTimeSeconds);
    }

    /**
     * startGameClock()
     * 
     * Starts the game clock for the player whose turn it is.
     * 
     * @param isWhiteTurn
     */
    public void startGameClock(boolean isWhiteTurn) {
        if (isWhiteTurn) {
            whiteTimer.start();
        } else {
            blackTimer.start();
        }

        updateTimerLabel(blackTimerLabel, blackTimeSeconds);
        updateTimerLabel(whiteTimerLabel, whiteTimeSeconds);
    }

    /**
     * pauseClocks()
     * 
     * Pauses both player clocks.
     * 
     */
    public void pauseClocks() {
        whiteTimer.stop();
        blackTimer.stop();
    }

    /**
     * resumeClock()
     * 
     * Resumes the clock for the active player.
     * 
     */
    public void resumeClock(boolean isWhiteTurn) {
        if (isWhiteTurn) {
            whiteTimer.start();
        } else {
            blackTimer.start();
        }
    }

    /**
     * gameOver()
     * 
     * Stops the clocks.
     * 
     */
    public void gameOver() {
        whiteTimer.stop();
        blackTimer.stop();
    }

    /**
     * getBlackTimeSeconds()
     * 
     * Returns the black player's remaining time in seconds.
     * 
     */
    public int getBlackTimeSeconds() {
        return blackTimeSeconds;
    }

    /**
     * getWhiteTimeSeconds()
     * 
     * Returns the white player's remaining time in seconds.
     * 
     */
    public int getWhiteTimeSeconds() {
        return whiteTimeSeconds;
    }
}
