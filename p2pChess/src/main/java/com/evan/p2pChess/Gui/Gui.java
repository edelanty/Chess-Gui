package com.evan.p2pChess.Gui;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.evan.p2pChess.Game;
import com.evan.p2pChess.Gamemode;
import com.evan.p2pChess.Uci;

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
        P2PChess onlineP2PChess = new P2PChess(cardLayout, mainPanel, settings, game, Gamemode.HUMAN_VS_HUMAN, uci);
        PlayVsAI selectScreen = new PlayVsAI(cardLayout, mainPanel, uci, playerColor, ai2pChess, startScreen);
        PlayOnline connectionScreen = new PlayOnline(cardLayout, mainPanel, onlineP2PChess, startScreen);
        startScreen.runGui();
        selectScreen.runGui();
        connectionScreen.runGui();
        p2pChess.runGui();
        ai2pChess.runGui();
        onlineP2PChess.runGui();
        settings.runGui();
        settings.setP2pChess(p2pChess);
        settings.setAi2PChess(ai2pChess);
        settings.setOnlineP2pChess(onlineP2PChess);
        ai2pChess.setPlayerColor(playerColor);

        mainPanel.add(startScreen.getStartScreenPanel(), "Start Screen");
        mainPanel.add(ai2pChess.getChessBoardPanel(), "AI2P Chess");
        mainPanel.add(p2pChess.getChessBoardPanel(), "P2P Chess");
        mainPanel.add(onlineP2PChess.getChessBoardPanel(), "Online Chess");
        mainPanel.add(selectScreen.getAiPanel(), "AI Select Screen");
        mainPanel.add(settings.getSettingsPanel(), "Settings");
        mainPanel.add(connectionScreen.getAiPanel(), "Connect Screen");

        //Add objects to the main window frame
        mainWindowFrame.add(mainPanel);
        mainWindowFrame.setSize(1200, 800);
        mainWindowFrame.pack();
        mainWindowFrame.setLocationRelativeTo(null); //Center
        setAppIcon();

        //Show the window
        cardLayout.show(mainPanel, "Start Screen");
    }

    /**
     * setAppIcon()
     * 
     * Set's the frames icon.
     * 
     */
    private void setAppIcon() {
        ImageIcon icon = new ImageIcon("packing/chessIcon.png");
        mainWindowFrame.setIconImage(icon.getImage());
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
