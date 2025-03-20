package com.evan.p2pChess.Gui;

import javax.swing.*;

import com.evan.p2pChess.Game;

import java.awt.*;

public class Gui {
    private JFrame mainWindowFrame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Game game;

    public Gui() {
        this.mainWindowFrame = new JFrame("P2P Chess");
        this.mainWindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainWindowFrame.setBackground(Color.WHITE);
        this.cardLayout = new CardLayout();
        this.mainPanel = new JPanel(cardLayout);
        this.game = null;
    }

    private void setupGui() {
        StartScreen startScreen = new StartScreen(cardLayout, mainPanel);
        Settings settings = new Settings(cardLayout, mainPanel);
        P2PChess p2pChess = new P2PChess(cardLayout, mainPanel, settings, game);
        startScreen.runGUI();
        p2pChess.runGui();
        settings.runGUI();
        settings.setP2pChess(p2pChess);

        mainPanel.add(startScreen.getStartScreenPanel(), "Start Screen");
        mainPanel.add(p2pChess.getChessBoardPanel(), "P2P Chess");
        mainPanel.add(settings.getSettingsPanel(), "Settings");

        //Add objects to the main window frame
        mainWindowFrame.add(mainPanel);
        mainWindowFrame.setSize(1200, 800);
        mainWindowFrame.pack();
        mainWindowFrame.setLocationRelativeTo(null); //Center

        //Show the window
        cardLayout.show(mainPanel, "Start Screen");
    }

    /**
     * runGui()
     * 
     * Calls setupGui to load the mainPanel and cardLayout, then sets the main window frame to be visible.
     * 
     */
    public void runGui(Game game) {
        this.game = game;
        setupGui();
        this.mainWindowFrame.setVisible(true);
    }

}
