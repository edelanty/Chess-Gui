package com.evan.p2pChess.Gui;

import javax.swing.*;

import com.evan.p2pChess.SoundManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
    private JButton playLocalButton;
    private JButton playOnlineButton;
    private JButton settingsButton;
    private CardLayout cardLayout;
    private boolean hasAIGameStarted;
    private boolean hasOnlineGameStarted;

    public StartScreen(CardLayout cardLayout, JPanel mainPanel) {
        this.mainPanel = mainPanel;
        this.startScreenPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon(getClass().getResource("Images/Startscreen/start_screen.jpg"));
                if (background.getImage() != null) {
                    g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
                } else {
                    System.out.println("Image not properly loaded.");
                }
            }
        };
        this.playVsAIButton = new JButton("Play Vs Bots");
        this.playLocalButton = new JButton("Play co-op Local");
        this.playOnlineButton = new JButton("Play co-op Online");
        this.settingsButton = new JButton("Settings");
        this.cardLayout = cardLayout;
        this.hasAIGameStarted = false;
        this.hasOnlineGameStarted = false;
    }

    public void sethasOnlineGameStarted(boolean hasOnlineGameStarted) {
        this.hasOnlineGameStarted = hasOnlineGameStarted;
    }

    public void sethasAIGameStarted(boolean hasAIGameStarted) {
        this.hasAIGameStarted = hasAIGameStarted;
    }

    public JPanel getStartScreenPanel() {
        return startScreenPanel;
    }

    private void setupGui() {
        startScreenPanel.setLayout(new BorderLayout());
        startScreenPanel.setPreferredSize(new Dimension(1000, 750));
        startScreenPanel.add(BorderLayout.NORTH, titlePanel());
        startScreenPanel.add(BorderLayout.CENTER, buttonPanel());
    }

    private JPanel titlePanel() {
        JPanel newPanel = new JPanel();
        JLabel titleLabel = new JLabel("Chess Gui");
        JLabel madeBy = new JLabel("Created by Evan Delanty");

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
        setupButton(playLocalButton);
        setupButton(playOnlineButton);
        setupButton(settingsButton);

        newPanel.add(Box.createVerticalGlue());
        newPanel.add(playVsAIButton);
        newPanel.add(Box.createVerticalStrut(20));
        newPanel.add(playOnlineButton);
        newPanel.add(Box.createVerticalStrut(20));
        newPanel.add(playLocalButton);
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

    public void runGui() {
        setupGui();
        setupListeners();
    }

    private void setupListeners() {
        playVsAIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundManager.play(getClass().getResource("/com/evan/p2pChess/Gui/Sounds/select.wav"));
                if (!hasAIGameStarted) {
                    cardLayout.show(mainPanel, "AI Select Screen");
                } else {
                    cardLayout.show(mainPanel, "AI2P Chess");
                }
            }
        });

        setupMouseListeners(playVsAIButton);

        playOnlineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundManager.play(getClass().getResource("/com/evan/p2pChess/Gui/Sounds/select.wav"));
                if (!hasOnlineGameStarted) {
                    cardLayout.show(mainPanel, "Connect Screen");
                } else {
                    cardLayout.show(mainPanel, "Online Chess");
                }
            }
        });

        setupMouseListeners(playOnlineButton);

        playLocalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundManager.play(getClass().getResource("/com/evan/p2pChess/Gui/Sounds/select.wav"));
                cardLayout.show(mainPanel, "P2P Chess");
            }
        });

        setupMouseListeners(playLocalButton);

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundManager.play(getClass().getResource("/com/evan/p2pChess/Gui/Sounds/select.wav"));
                cardLayout.show(mainPanel, "Settings");
            }
        });

        setupMouseListeners(settingsButton);
    }

    private void setupMouseListeners(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                SoundManager.play(getClass().getResource("/com/evan/p2pChess/Gui/Sounds/hover.wav"));
                button.setBackground(Settings.SELECTED_COLOR);
                startScreenPanel.repaint();
            }

            @Override 
            public void mouseExited(MouseEvent e) {
                button.setBackground(Settings.WHITE_COLOR);
            }
        });
    }
}
