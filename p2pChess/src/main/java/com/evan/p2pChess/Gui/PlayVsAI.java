package com.evan.p2pChess.Gui;

import javax.swing.*;
import com.evan.p2pChess.SoundManager;
import com.evan.p2pChess.Uci;
import java.awt.*;
import java.awt.event.*;

public class PlayVsAI {
    private JPanel aiPanel;
    private JComboBox<String> colorChoice;
    private JComboBox<String> eloChoice;
    private JButton startGameButton;
    private JButton backButton;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Uci uci;
    private com.evan.p2pChess.Color playercolor;
    private P2PChess ai2PChess;
    private StartScreen startScreen;
    private String engineChoice;

    public PlayVsAI(CardLayout cardLayout, JPanel mainPanel, Uci uci, com.evan.p2pChess.Color playerColor, P2PChess ai2PChess, StartScreen startScreen) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.uci = uci;
        this.playercolor = playerColor;
        this.ai2PChess = ai2PChess;
        this.engineChoice = "stockfish";
        this.startScreen = startScreen;
        this.backButton = new JButton("Back");
        this.aiPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon(getClass().getResource("Images/Start Screen/start_screen.jpg"));
                if (background.getImage() != null) {
                    g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
    }

    public JPanel getAiPanel() {
        return aiPanel;
    }

    public void runGui() {
        setupGui();
        setupListeners();
    }

    private void setupGui() {
        aiPanel.setLayout(new BorderLayout());
        aiPanel.setPreferredSize(new Dimension(1000, 750));

        aiPanel.add(BorderLayout.NORTH, titlePanel());
        aiPanel.add(BorderLayout.CENTER, optionsPanel());
        aiPanel.add(BorderLayout.SOUTH, buttonPanel());
    }

    private JPanel titlePanel() {
        JPanel newPanel = new JPanel();
        JLabel titleLabel = new JLabel("Play vs AI");
        JLabel subtitleLabel = new JLabel("Challenge the computer");

        newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
        newPanel.setOpaque(false);

        titleLabel.setFont(new Font("Georgia", Font.BOLD, 72));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        subtitleLabel.setFont(new Font("Georgia", Font.PLAIN, 36));
        subtitleLabel.setForeground(Color.LIGHT_GRAY.brighter());
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        newPanel.add(Box.createVerticalStrut(50));
        newPanel.add(titleLabel);
        newPanel.add(Box.createVerticalStrut(10));
        newPanel.add(subtitleLabel);
        newPanel.add(Box.createVerticalStrut(20));

        return newPanel;
    }

    private JPanel optionsPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel colorLabel = new JLabel("Select Your Color");
        colorLabel.setFont(new Font("Arial", Font.BOLD, 24));
        colorLabel.setForeground(Color.WHITE);
        colorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        colorChoice = new JComboBox<>(new String[]{"White", "Black"});
        colorChoice.setMaximumSize(new Dimension(250, 50));
        colorChoice.setFont(new Font("Arial", Font.PLAIN, 20));
        colorChoice.setBackground(Settings.GRAY_LIGHT_BOX_COLOR);
        colorChoice.setForeground(Color.WHITE);
        colorChoice.setAlignmentX(Component.CENTER_ALIGNMENT);
        ((JComponent) colorChoice.getRenderer()).setOpaque(true);

        JLabel eloLabel = new JLabel("Select AI Difficulty");
        eloLabel.setFont(new Font("Arial", Font.BOLD, 24));
        eloLabel.setForeground(Color.WHITE);
        eloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        eloChoice = new JComboBox<>(new String[]{"800", "1000", "1200", "1400", "1600", "1800", "2000", "2200", "2500", "2800"});
        eloChoice.setMaximumSize(new Dimension(250, 50));
        eloChoice.setFont(new Font("Arial", Font.PLAIN, 20));
        eloChoice.setBackground(Settings.GRAY_LIGHT_BOX_COLOR);
        eloChoice.setForeground(Color.WHITE);
        eloChoice.setAlignmentX(Component.CENTER_ALIGNMENT);
        ((JComponent) eloChoice.getRenderer()).setOpaque(true);

        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(colorLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(colorChoice);
        mainPanel.add(Box.createVerticalStrut(40));
        mainPanel.add(eloLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(eloChoice);
        mainPanel.add(Box.createVerticalGlue());

        return mainPanel;
    }

    private JPanel buttonPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel buttonsCol = new JPanel();
        buttonsCol.setOpaque(false);
        buttonsCol.setLayout(new BoxLayout(buttonsCol, BoxLayout.Y_AXIS));
        buttonsCol.setAlignmentX(Component.CENTER_ALIGNMENT);

        startGameButton = new JButton("Start Game");
        startGameButton.setMaximumSize(new Dimension(300, 60));
        startGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startGameButton.setFont(new Font("Arial", Font.BOLD, 24));
        startGameButton.setBackground(Settings.WHITE_COLOR);
        startGameButton.setForeground(Color.BLACK);
        startGameButton.setFocusPainted(false);
        startGameButton.setBorder(BorderFactory.createRaisedBevelBorder());

        buttonsCol.add(startGameButton);

        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setMaximumSize(new Dimension(300, 60));
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setFont(new Font("Arial", Font.BOLD, 24));
        backButton.setBackground(Settings.WHITE_COLOR);
        backButton.setForeground(Color.BLACK);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createRaisedBevelBorder());

        buttonsCol.add(Box.createVerticalStrut(30));
        buttonsCol.add(backButton);

        panel.add(buttonsCol);
        panel.add(Box.createVerticalStrut(40));

        return panel;
    }

    private void setupListeners() {
        startGameButton.addActionListener(e -> {
            SoundManager.play(getClass().getResource("/com/evan/p2pChess/Gui/Sounds/select.wav"));

            String selectedColor = colorChoice.getSelectedItem().toString();
            int selectedElo = Integer.parseInt(eloChoice.getSelectedItem().toString());

            //Initialize and configure UCI engine
            uci.start(engineChoice);
            uci.setAIDifficulty(selectedElo);

            //Set the color
            if (selectedColor.equals("White")) {
                playercolor = com.evan.p2pChess.Color.WHITE;
                ai2PChess.setPlayerColor(playercolor);
            } else {
                playercolor = com.evan.p2pChess.Color.BLACK;
                ai2PChess.setPlayerColor(playercolor);
                ai2PChess.playAIMove();
            }

            startScreen.sethasAIGameStarted(true);

            //Pass selectedColor and uci into game logic
            cardLayout.show(mainPanel, "AI2P Chess");
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundManager.play(getClass().getResource("/com/evan/p2pChess/Gui/Sounds/select.wav"));
                cardLayout.show(mainPanel, "Start Screen");
            }
        });

        setupMouseListeners(startGameButton);
        setupMouseListeners(backButton);
    }

    private void setupMouseListeners(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                SoundManager.play(getClass().getResource("/com/evan/p2pChess/Gui/Sounds/hover.wav"));
                button.setBackground(Settings.SELECTED_COLOR);
                aiPanel.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(Settings.WHITE_COLOR);
            }
        });
    }
}
