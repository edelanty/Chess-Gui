package com.evan.p2pChess.Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * StartScreen Class
 * 
 * This class creates the panels necessary for the start screen for P2P Chess.
 * 
 * @author Evan Delanty
 */
public class StartScreen {
    private JPanel startScreenPanel;
    private JPanel mainPanel;
    private JButton playButton;
    private CardLayout cardLayout;

    public StartScreen(CardLayout cardLayout, JPanel mainPanel) {
        this.mainPanel = mainPanel;
        this.startScreenPanel = new JPanel();
        this.playButton = new JButton();
        this.cardLayout = cardLayout;
    }

    //Getters
    public JPanel getStartScreenPanel() {
        return startScreenPanel;
    }

    private void setupGUI() {
        startScreenPanel.setLayout(new BorderLayout());
        startScreenPanel.setPreferredSize(new Dimension(1000, 750));
        startScreenPanel.setBackground(Color.ORANGE.darker().darker().darker().darker());
        startScreenPanel.add(BorderLayout.NORTH, titlePanel());
        startScreenPanel.add(BorderLayout.CENTER, playPanel());
    }

    /**
     * titlePanel()
     * 
     * Creates and sets the attributes for the title and made by lines.
     * 
     * @return Title card and made by panel.
     */
    private JPanel titlePanel() {
        JPanel newPanel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel madeBy = new JLabel();

        newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
        newPanel.setPreferredSize(new Dimension(300, 350));
        newPanel.setBackground(Color.ORANGE.darker().darker().darker().darker());

        titleLabel.setText("P2P Chess");
        titleLabel.setFont(new Font("Comic Sans", 0, 75));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        madeBy.setText("Evan Delanty");
        madeBy.setFont(new Font("Serif", 0, 45));
        madeBy.setForeground(Color.BLACK);
        madeBy.setAlignmentX(Component.CENTER_ALIGNMENT);

        newPanel.add(titleLabel);
        newPanel.add(madeBy);

        return newPanel;
    }

    /**
     * playPanel()
     * 
     * Creates and sets the panel which contains the play button.
     * When clicked this button takes the player to the main board.
     * 
     * @return Play button panel
     */
    private JPanel playPanel() {
        JPanel newPanel = new JPanel();

        newPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
        newPanel.setBackground(Color.ORANGE.darker().darker().darker().darker());

        playButton.setBackground(Color.GRAY.darker());
        playButton.setText("Play");
        playButton.setForeground(Color.BLACK);
        playButton.setFont(new Font("Papyrus", 0, 30));
        playButton.setBorderPainted(false);
        playButton.setPreferredSize(new Dimension(250, 250));
        playButton.setFocusPainted(false);

        newPanel.add(playButton);

        return newPanel;
    }

    public void runGUI() {
        setupGUI();
        playButtonEventListener();
    }

    //Event listeners

    /**
     * playButtonEventListener()
     * 
     * Creates the event listener and action for pressing the play button.
     * Switches the panel to the chess board.
     * 
     */
    private void playButtonEventListener() {
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "P2P Chess");
            }
        });
    }
}
