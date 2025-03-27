package com.evan.p2pChess.Gui;

import javax.swing.*;

import com.evan.p2pChess.Board;
import com.evan.p2pChess.SoundManager;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class EndGameDialog extends JDialog {
    private ConfettiPanel confettiPanel;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private P2PChess gui;
    private Board board;

    public EndGameDialog(Frame parent, String gameOutcome, String winner, JPanel mainPanel, CardLayout cardLayout, P2PChess gui, Board board) {
        super(parent, "Game Over", true);
        
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.gui = gui;
        this.board = board;
        
        //Create a layered pane to handle confetti
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(500, 400));
        
        //Confetti panel goes behind the main dialog content
        confettiPanel = new ConfettiPanel();
        confettiPanel.setBounds(0, 0, 500, 400);
        layeredPane.add(confettiPanel, Integer.valueOf(0));
        
        //Main dialog content
        JPanel panel = createMainPanel(gameOutcome, winner);
        panel.setBounds(0, 0, 500, 400);
        layeredPane.add(panel, Integer.valueOf(1));
        
        //Dialog setup
        setContentPane(layeredPane);
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setUndecorated(true);
        getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        
        //Start confetti animation
        confettiPanel.startConfetti();
    }
    
    private JPanel createMainPanel(String gameOutcome, String winner) {
        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                //Make panel background slightly transparent
                g.setColor(new Color(255, 255, 255, 230));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setOpaque(false);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        //Game Outcome Label
        JLabel outcomeLabel = new JLabel(gameOutcome);
        outcomeLabel.setFont(new Font("Arial", Font.BOLD, 36));
        outcomeLabel.setForeground(Color.DARK_GRAY);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(outcomeLabel, gbc);
        
        //Winner Label
        JLabel winnerLabel = new JLabel(winner + " wins!");
        winnerLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        winnerLabel.setForeground(Color.GRAY);
        gbc.gridy = 1;
        panel.add(winnerLabel, gbc);
        
        //Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setOpaque(false);
        
        JButton rematchButton = createStyledButton("Rematch", Color.GREEN);
        JButton exitButton = createStyledButton("Exit", Color.RED);
        
        rematchButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                dispose();
                gui.newGame();
                board.playGameStartSound();
            });
        });
        
        exitButton.addActionListener(e -> {
            gui.newGame();
            cardLayout.show(mainPanel, "Start Screen");
            dispose();
        });
        
        gbc.gridy = 2;
        buttonPanel.add(rematchButton);
        buttonPanel.add(exitButton);
        panel.add(buttonPanel, gbc);
        
        return panel;
    }
    
    private JButton createStyledButton(String text, Color baseColor) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(300, 60));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setBackground(Settings.WHITE_COLOR);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        
        //Hover effects
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                SoundManager.play(getClass().getResource("/com/evan/p2pChess/Gui/Sounds/hover.wav"));
                button.setBackground(Settings.SELECTED_COLOR);
            }

            @Override 
            public void mouseExited(MouseEvent e) {
                button.setBackground(Settings.WHITE_COLOR);
            }
        });
        
        return button;
    }
    
    //Confetti Panel inner class
    private class ConfettiPanel extends JPanel {
        private ArrayList<Confetti> confettiList = new ArrayList<>();
        private Timer confettiTimer;
        
        public ConfettiPanel() {
            setOpaque(false);
        }
        
        public void startConfetti() {
            //Generate 100 confetti pieces
            for (int i = 0; i < 100; i++) {
                confettiList.add(new Confetti());
            }
            
            //Animation timer
            confettiTimer = new Timer(30, new ActionListener() {
                private int elapsedTime = 0;
                
                @Override
                public void actionPerformed(ActionEvent e) {
                    elapsedTime += 30;
                    
                    for (Confetti c : confettiList) {
                        c.move();
                    }
                    repaint();
                    
                    //Stop confetti after 3.5 seconds
                    if (elapsedTime > 3500) {
                        ((Timer)e.getSource()).stop();
                    }
                }
            });
            confettiTimer.start();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Confetti c : confettiList) {
                c.draw(g);
            }
        }
        
        //Inner class for individual confetti pieces
        private class Confetti {
            private double x, y;
            private int size;
            private Color color;
            private double velocityX, velocityY;
            
            public Confetti() {
                //Random starting position
                x = Math.random() * getWidth();
                y = -20; // Start above the visible area
                
                //Random size between 5 and 15
                size = (int)(Math.random() * 11) + 5;
                
                // Random vibrant colors
                color = new Color(
                    (int)(Math.random() * 256),
                    (int)(Math.random() * 256),
                    (int)(Math.random() * 256)
                );
                
                //Random velocity
                velocityX = (Math.random() - 0.5) * 5;
                velocityY = Math.random() * 5 + 2;
            }
            
            public void move() {
                x += velocityX;
                y += velocityY;
                
                //Gravity and slight wind effect
                velocityY += 0.2;
                velocityX *= 0.99;
            }
            
            public void draw(Graphics g) {
                g.setColor(color);
                g.fillRect((int)x, (int)y, size, size);
            }
        }
    }
}
