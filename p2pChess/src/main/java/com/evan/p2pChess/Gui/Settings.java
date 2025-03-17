package com.evan.p2pChess.Gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.evan.p2pChess.SoundManager;

public class Settings {
    public static final Color WHITE_COLOR = new Color(240, 217, 181);
    public static final Color BROWN_COLOR = new Color(181, 136, 99);
    public static final Color BLACK = new Color(0, 0, 0, 180);
    public static final Color WHITE = new Color(255, 255, 255, 180);
    public static final Color RED_COLOR = new Color(255, 0, 0, 128);
    public static final Color HIGHLIGHT_YELLOW = new Color(255, 165, 0, 128);
    public static final Color DARKER_HIGHLIGHT_YELLOW = new Color(255, 135, 0, 128);
    public static final Color SELECTED_COLOR = new Color(0, 150, 255);
    public static final Color BLUE_LIGHT_COLOR = new Color(220, 220, 220);
    public static final Color BLUE_DARK_COLOR = new Color(86, 119, 153);
    public static final Color GREEN_LIGHT_COLOR = new Color(240, 240, 210);
    public static final Color GREEN_DARK_COLOR = new Color(118, 150, 86);
    public static final Color RED_LIGHT_COLOR = new Color(240, 214, 214);
    public static final Color RED_DARK_COLOR = new Color(168, 81, 81);
    public static final Color WOODEN_LIGHT_COLOR = new Color(255, 228, 163);
    public static final Color WOODEN_DARK_COLOR = new Color(139, 87, 37);
    public static final Color GRAY_LIGHT_COLOR = new Color(235, 235, 235);
    public static final Color GRAY_DARK_COLOR = new Color(125, 125, 125);
    public static final Color CORAL_LIGHT_COLOR = new Color(255, 233, 197);
    public static final Color CORAL_DARK_COLOR = new Color(143, 96, 79);
    public static final Color PURPLE_LIGHT_COLOR = new Color(213, 192, 255);
    public static final Color PURPLE_DARK_COLOR = new Color(118, 90, 151);

    private JPanel settingsPanel;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JButton backButton;
    private JCheckBox soundCheckbox;
    private P2PChess p2pChess;
    private Color primaryColor;
    private Color alternativeColor;
    private static boolean isSoundEnabled;

    public Settings(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.settingsPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon(getClass().getResource("Images/Start Screen/start_screen.jpg"));
                if (background.getImage() != null) {
                    g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        this.p2pChess = null;
        this.backButton = new JButton("Back");
        this.soundCheckbox = new JCheckBox("Enable Sound");
        this.primaryColor = WHITE_COLOR;
        this.alternativeColor = BROWN_COLOR;
        Settings.isSoundEnabled = true;
    }

    //Getters
    public JPanel getSettingsPanel() {
        return settingsPanel;
    }
    
    public Color getPrimaryColor() {
        return primaryColor;
    }

    public Color getAlternativeColor() {
        return alternativeColor;
    }

    public static boolean getIsSoundEnabled() {
        return isSoundEnabled;
    }

    //Setters
    public void setP2pChess(P2PChess p2pChess) {
        this.p2pChess = p2pChess;
    }

    public void setIsSoundEnabled(boolean isSoundEnabled) {
        Settings.isSoundEnabled = isSoundEnabled;
    }

    public void runGUI() {
        setupGUI();
        setupListeners();
    }

    private void setupGUI() {
        settingsPanel.setLayout(new BorderLayout());
        settingsPanel.setPreferredSize(new Dimension(1000, 750));

        settingsPanel.add(BorderLayout.NORTH, titlePanel());
        settingsPanel.add(BorderLayout.CENTER, settingsOptionsPanel());
        settingsPanel.add(BorderLayout.SOUTH, backButtonPanel());
    }

    private JPanel titlePanel() {
        JPanel newPanel = new JPanel();
        JLabel titleLabel = new JLabel("Settings");
        JLabel madeBy = new JLabel("");

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

    private JPanel settingsOptionsPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        //Sound checkbox
        soundCheckbox.setFont(new Font("Arial", Font.PLAIN, 20));
        soundCheckbox.setFocusPainted(false);
        soundCheckbox.setBackground(new Color(0, 0, 0, 0));
        soundCheckbox.setForeground(Color.WHITE);
        soundCheckbox.setOpaque(false);
        soundCheckbox.setAlignmentX(Component.CENTER_ALIGNMENT);
        soundCheckbox.setSelected(true);
        
        //Title for color options
        JLabel colorOptionsLabel = new JLabel("Board Color Options");
        colorOptionsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        colorOptionsLabel.setForeground(Color.WHITE);
        colorOptionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //Color options grid
        JPanel colorGrid = new JPanel(new GridLayout(3, 3, 10, 10));
        colorGrid.setOpaque(false);
        colorGrid.setAlignmentX(Component.CENTER_ALIGNMENT);
        colorGrid.setMaximumSize(new Dimension(300, 300));
        
        //Add color options to the grid
        addColorOption(colorGrid, WHITE_COLOR, BROWN_COLOR, "Classic");
        addColorOption(colorGrid, WHITE, BLACK, "Standard");
        addColorOption(colorGrid, BLUE_LIGHT_COLOR, BLUE_DARK_COLOR, "Blue");
        addColorOption(colorGrid, GREEN_LIGHT_COLOR, GREEN_DARK_COLOR, "Green");
        addColorOption(colorGrid, RED_LIGHT_COLOR, RED_DARK_COLOR, "Red");
        addColorOption(colorGrid, WOODEN_LIGHT_COLOR, WOODEN_DARK_COLOR, "Wooden");
        addColorOption(colorGrid, GRAY_LIGHT_COLOR, GRAY_DARK_COLOR, "Gray");
        addColorOption(colorGrid, CORAL_LIGHT_COLOR, CORAL_DARK_COLOR, "Coral");
        addColorOption(colorGrid, PURPLE_LIGHT_COLOR, PURPLE_DARK_COLOR, "Purple");

        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(soundCheckbox);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(colorOptionsLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(colorGrid);
        mainPanel.add(Box.createVerticalStrut(20));
        
        return mainPanel;
    }
    
    private void addColorOption(JPanel parent, final Color lightColor, final Color darkColor, String tooltip) {
        JPanel colorPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int width = getWidth();
                int height = getHeight();
                
                //Draw the light color in the bottom right triangle
                g.setColor(lightColor);
                int[] xPointsLight = {0, width, width};
                int[] yPointsLight = {height, 0, height};
                g.fillPolygon(xPointsLight, yPointsLight, 3);
                
                //Draw the dark color in the top left triangle
                g.setColor(darkColor);
                int[] xPointsDark = {0, width, 0};
                int[] yPointsDark = {0, 0, height};
                g.fillPolygon(xPointsDark, yPointsDark, 3);
                
                //Draw a border around the square
                g.setColor(Color.BLACK);
                g.drawRect(0, 0, width - 1, height - 1);
            }
        };
        
        colorPanel.setPreferredSize(new Dimension(80, 80));
        colorPanel.setToolTipText(tooltip);
        colorPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        colorPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        
        //Add mouse listener to handle selection
        colorPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                //Play sound
                SoundManager.play(getClass().getResource("/com/evan/p2pChess/Gui/Sounds/select.wav"));
                
                //Set selected border
                for (Component comp : parent.getComponents()) {
                    if (comp instanceof JComponent) {
                        ((JComponent)comp).setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
                    }
                }
        
                colorPanel.setBorder(BorderFactory.createLineBorder(SELECTED_COLOR, 3));
                
                //Update primary and alt color variables
                primaryColor = lightColor;
                alternativeColor = darkColor;
                p2pChess.updateBoardColors();
            }
        });
        
        parent.add(colorPanel);
    }

    private JPanel backButtonPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);

        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setPreferredSize(new Dimension(120, 40));
        backButton.setBackground(Settings.WHITE_COLOR);
        backButton.setForeground(Color.BLACK);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createRaisedBevelBorder());

        panel.add(backButton);

        panel.add(Box.createVerticalStrut(10));

        return panel;
    }

    private void setupListeners() {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundManager.play(getClass().getResource("/com/evan/p2pChess/Gui/Sounds/select.wav"));
                cardLayout.show(mainPanel, "Start Screen");
            }
        });

        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                SoundManager.play(getClass().getResource("/com/evan/p2pChess/Gui/Sounds/hover.wav"));
                backButton.setBackground(Settings.SELECTED_COLOR);
                settingsPanel.repaint();
            }

            @Override 
            public void mouseExited(MouseEvent e) {
                backButton.setBackground(Settings.WHITE_COLOR);
            }
        });

        soundCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setIsSoundEnabled(!isSoundEnabled);
            }
        });
    }

}
