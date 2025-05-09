package com.evan.p2pChess.Gui;

import javax.swing.*;

import com.evan.p2pChess.SoundManager;
import com.evan.p2pChess.Online.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * Handles creating the Gui for playing online and outputs all information needed for two players to connect to each other.
 * 
 * @author Evan Delanty
 * @version v1.0.0
 * @since 4/1/25
 */
public class PlayOnline {
    private JPanel onlinePanel;
    private JButton startGameButton;
    private JButton backButton;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private P2PChess p2pChess;
    private StartScreen startScreen;

    public PlayOnline(CardLayout cardLayout, JPanel mainPanel, P2PChess p2pChess, StartScreen startScreen) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.p2pChess = p2pChess;
        this.startScreen = startScreen;
        this.backButton = new JButton("Back");
        this.onlinePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon(getClass().getResource("Images/Startscreen/start_screen.jpg"));
                if (background.getImage() != null) {
                    g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
    }

    public JPanel getAiPanel() {
        return onlinePanel;
    }

    public void runGui() {
        setupGui();
        setupListeners();
    }

    private void setupGui() {
        onlinePanel.setLayout(new BorderLayout());
        onlinePanel.setPreferredSize(new Dimension(1000, 750));

        onlinePanel.add(BorderLayout.NORTH, titlePanel());
        onlinePanel.add(BorderLayout.CENTER, optionsPanel());
        onlinePanel.add(BorderLayout.SOUTH, buttonPanel());
    }

    private JPanel titlePanel() {
        JPanel newPanel = new JPanel();
        JLabel titleLabel = new JLabel("Play Online Co-op");
        JLabel subtitleLabel = new JLabel("Challenge your friends");

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

    /**
     * Option for I want to host, and invite screen.
     * 
     * @return
     */
    private JPanel optionsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        
        //Create host/join option panel
        JPanel optionsContainer = new JPanel();
        optionsContainer.setOpaque(false);
        optionsContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
        optionsContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel hostPanel = createOptionPanel("Host a Game", "Create a new game and invite a friend");
        hostPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Server server = new Server(5000);
                    String ipAddresses = server.getServerIPAddresses();
                    
                    //Create a dialog with IP information
                    JDialog ipDialog = new JDialog();
                    ipDialog.setTitle("Your IP Addresses");
                    ipDialog.setSize(400, 300);
                    ipDialog.setLocationRelativeTo(null);
                    ipDialog.setModal(true);
                    ipDialog.setLayout(new BorderLayout());
                    
                    //Create a panel for the IP information
                    JPanel ipPanel = new JPanel();
                    ipPanel.setLayout(new BorderLayout());
                    ipPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                    
                    JLabel headerLabel = new JLabel("Share one to connect!");
                    headerLabel.setFont(new Font("Georgia", Font.BOLD, 16));
                    
                    //Create a text area to display the IPs
                    JTextArea ipTextArea = new JTextArea(ipAddresses);
                    ipTextArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
                    ipTextArea.setEditable(false);
                    ipTextArea.setBackground(ipPanel.getBackground());
                    
                    JLabel waitingLabel = new JLabel("Waiting for player to connect...");
                    waitingLabel.setFont(new Font("Georgia", Font.ITALIC, 14));
                    waitingLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    
                    //Add components to the IP panel
                    ipPanel.add(headerLabel, BorderLayout.NORTH);
                    ipPanel.add(new JScrollPane(ipTextArea), BorderLayout.CENTER);
                    ipPanel.add(waitingLabel, BorderLayout.SOUTH);
                    
                    //Add the IP panel to the dialog
                    ipDialog.add(ipPanel, BorderLayout.CENTER);
                    
                    //Start a thread to wait for connection
                    new Thread(() -> {
                        try {
                            server.waitForConnection();
                            SwingUtilities.invokeLater(() -> {
                                ipDialog.dispose();
                                JOptionPane.showMessageDialog(null, "Player connected! Starting game...");
                                p2pChess.startOnlineGame(server, true);
                                cardLayout.show(mainPanel, "Online Chess");
                            });
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            SwingUtilities.invokeLater(() -> {
                                ipDialog.dispose();
                                JOptionPane.showMessageDialog(null, "Connection error: " + ex.getMessage(), 
                                    "Error", JOptionPane.ERROR_MESSAGE);
                            });
                        }
                    }).start();
                    
                    ipDialog.setVisible(true);
                    
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Failed to start server: " + ex.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                hostPanel.setBackground(Settings.SELECTED_COLOR);
                SoundManager.play(getClass().getResource("/com/evan/p2pChess/Gui/Sounds/hover.wav"));
                hostPanel.repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                hostPanel.setBackground(Settings.WHITE_COLOR);
                hostPanel.repaint();
            }
        });
        
        JPanel joinPanel = createOptionPanel("Join a Game", "Connect to an existing game");
        joinPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String serverIp = JOptionPane.showInputDialog(null, 
                    "Enter your friend's IP address:", 
                    "Join Game", JOptionPane.QUESTION_MESSAGE);
                
                if (serverIp != null && !serverIp.trim().isEmpty()) {
                    try {
                        Client client = new Client(serverIp, 5000);
                        JOptionPane.showMessageDialog(null, "Connected to " + serverIp);
                        p2pChess.startOnlineGame(client, false);
                        cardLayout.show(mainPanel, "Online Chess");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Failed to connect: " + ex.getMessage(), 
                            "Connection Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                joinPanel.setBackground(Settings.SELECTED_COLOR);
                SoundManager.play(getClass().getResource("/com/evan/p2pChess/Gui/Sounds/hover.wav"));
                joinPanel.repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                joinPanel.setBackground(Settings.WHITE_COLOR);
                joinPanel.repaint();
            }
        });
        
        optionsContainer.add(hostPanel);
        optionsContainer.add(joinPanel);
        
        panel.add(Box.createVerticalGlue());
        panel.add(optionsContainer);
        panel.add(Box.createVerticalGlue());
        
        return panel;
    }
    
    /**
     * createOptionPanel()
     * 
     * Styles panels passed in.
     * 
     * @param title
     * @param subtitle
     * @return
     */
    private JPanel createOptionPanel(String title, String subtitle) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(300, 200));
        panel.setBackground(Settings.WHITE_COLOR);
        panel.setBorder(BorderFactory.createRaisedBevelBorder());
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 24));
        titleLabel.setForeground(Color.DARK_GRAY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(new Font("Georgia", Font.PLAIN, 16));
        subtitleLabel.setForeground(Color.BLACK);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(subtitleLabel);
        panel.add(Box.createVerticalGlue());
        
        return panel;
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
        setupMouseListeners(backButton);

        buttonsCol.add(Box.createVerticalStrut(30));
        buttonsCol.add(backButton);

        panel.add(buttonsCol);
        panel.add(Box.createVerticalStrut(40));

        return panel;
    }

    private void setupListeners() {
        startGameButton.addActionListener(e -> {
            try {
                String mode = JOptionPane.showInputDialog("Enter 'host' to start a game or opponent's IP to join:");
    
                if (mode != null && !mode.isEmpty()) {
                    if (mode.equalsIgnoreCase("host")) {
                        Server server = new Server(5000);
                        JOptionPane.showMessageDialog(null, "Server started! Waiting for a player...");
    
                        new Thread(() -> {
                            try {
                                server.waitForConnection();
                                SwingUtilities.invokeLater(() -> {
                                    JOptionPane.showMessageDialog(null, "Player connected! Starting game...");
                                    p2pChess.startOnlineGame(server, true);
                                    startScreen.sethasOnlineGameStarted(true);
                                    cardLayout.show(mainPanel, "Online Chess");
                                });
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }).start();
                    } else {
                        Client client = new Client(mode, 5000);
                        JOptionPane.showMessageDialog(null, "Connected to " + mode);
                        p2pChess.startOnlineGame(client, false);
                        startScreen.sethasOnlineGameStarted(true);
                        cardLayout.show(mainPanel, "Online Chess");
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    
        setupMouseListeners(startGameButton);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundManager.play(getClass().getResource("/com/evan/p2pChess/Gui/Sounds/select.wav"));
                cardLayout.show(mainPanel, "Start Screen");
            }
        });
    }

    private void setupMouseListeners(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                SoundManager.play(getClass().getResource("/com/evan/p2pChess/Gui/Sounds/hover.wav"));
                button.setBackground(Settings.SELECTED_COLOR);
                onlinePanel.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(Settings.WHITE_COLOR);
            }
        });
    }
}
