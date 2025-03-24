package com.evan.p2pChess.Gui;

import javax.swing.*;

import com.evan.p2pChess.Game;
import com.evan.p2pChess.Gamemode;
import com.evan.p2pChess.Uci;

import java.awt.*;

public class Gui {
    private JFrame mainWindowFrame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Game game;
    private Uci uci;
    private com.evan.p2pChess.Color playerColor;

    public Gui() {
        this.mainWindowFrame = new JFrame("P2P Chess");
        this.mainWindowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainWindowFrame.setBackground(Color.WHITE);
        this.cardLayout = new CardLayout();
        this.mainPanel = new JPanel(cardLayout);
        this.game = null;
        this.uci = new Uci();
        this.playerColor = com.evan.p2pChess.Color.WHITE;
    }

    private void setupGui() {
        StartScreen startScreen = new StartScreen(cardLayout, mainPanel);
        Settings settings = new Settings(cardLayout, mainPanel);
        P2PChess p2pChess = new P2PChess(cardLayout, mainPanel, settings, game, Gamemode.HUMAN_VS_HUMAN, uci);
        P2PChess ai2pChess = new P2PChess(cardLayout, mainPanel, settings, game, Gamemode.HUMAN_VS_AI, uci);
        PlayVsAI selectScreen = new PlayVsAI(cardLayout, mainPanel, uci, playerColor, ai2pChess);
        startScreen.runGui();
        selectScreen.runGui();
        p2pChess.runGui();
        ai2pChess.runGui();
        settings.runGui();
        settings.setP2pChess(p2pChess);
        settings.setAi2PChess(ai2pChess);
        ai2pChess.setPlayerColor(playerColor);

        mainPanel.add(startScreen.getStartScreenPanel(), "Start Screen");
        mainPanel.add(ai2pChess.getChessBoardPanel(), "AI2P Chess");
        mainPanel.add(p2pChess.getChessBoardPanel(), "P2P Chess");
        mainPanel.add(selectScreen.getAiPanel(), "AI Select Screen");
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
