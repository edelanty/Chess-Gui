package com.evan.p2pChess.Gui;

import javax.swing.*;

import com.evan.p2pChess.Settings;

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
    private JButton playVsAIButton;
    private JButton playVsHumanButton;
    private JButton settingsButton;
    private CardLayout cardLayout;

    public StartScreen(CardLayout cardLayout, JPanel mainPanel) {
        this.mainPanel = mainPanel;
        this.startScreenPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon(getClass().getResource("Images/start_screen.jpg"));
                if (background.getImage() != null) {
                    g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
                } else {
                    System.out.println("Image not properly loaded.");
                }
            }
        };
        this.playVsAIButton = new JButton("Play vs AI");
        this.playVsHumanButton = new JButton("Play vs Human");
        this.settingsButton = new JButton("Settings");
        this.cardLayout = cardLayout;
    }

    public JPanel getStartScreenPanel() {
        return startScreenPanel;
    }

    private void setupGUI() {
        startScreenPanel.setLayout(new BorderLayout());
        startScreenPanel.setPreferredSize(new Dimension(1000, 750));
        startScreenPanel.add(BorderLayout.NORTH, titlePanel());
        startScreenPanel.add(BorderLayout.CENTER, buttonPanel());
    }

    private JPanel titlePanel() {
        JPanel newPanel = new JPanel();
        JLabel titleLabel = new JLabel("P2P Chess");
        JLabel madeBy = new JLabel("Evan Delanty");

        newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
        newPanel.setOpaque(false);

        titleLabel.setFont(new Font("Georgia", Font.BOLD, 72));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        madeBy.setFont(new Font("Georgia", Font.PLAIN, 36));
        madeBy.setForeground(Color.LIGHT_GRAY.brighter());
        madeBy.setAlignmentX(Component.CENTER_ALIGNMENT);

        newPanel.add(Box.createVerticalStrut(50));
        newPanel.add(titleLabel);
        newPanel.add(Box.createVerticalStrut(10));
        newPanel.add(madeBy);
        newPanel.add(Box.createVerticalStrut(20));

        return newPanel;
    }

    private JPanel buttonPanel() {
        JPanel newPanel = new JPanel();
        newPanel.setOpaque(false);
        newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));

        setupButton(playVsAIButton);
        setupButton(playVsHumanButton);
        setupButton(settingsButton);

        newPanel.add(Box.createVerticalGlue());
        newPanel.add(playVsAIButton);
        newPanel.add(Box.createVerticalStrut(20));
        newPanel.add(playVsHumanButton);
        newPanel.add(Box.createVerticalStrut(20));
        newPanel.add(settingsButton);
        newPanel.add(Box.createVerticalGlue());

        return newPanel;
    }

    private void setupButton(JButton button) {
        button.setMaximumSize(new Dimension(300, 60));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setBackground(Settings.WHITE_COLOR);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
    }

    public void runGUI() {
        setupGUI();
        setupListeners();
    }

    private void setupListeners() {
        playVsAIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "P2P Chess AI");
            }
        });

        playVsHumanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "P2P Chess");
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Settings");
            }
        });
    }
}
