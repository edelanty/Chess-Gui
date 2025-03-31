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
import java.awt.Image;
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
    public static final Color GRAY_LIGHT_TEXT_COLOR = new Color(192, 192, 192);
    public static final Color GRAY_DARK_BOX_COLOR = new Color(50, 50, 50);
    public static final Color GRAY_LIGHT_BOX_COLOR = new Color(80, 80, 80);

    public static final Integer BULLET_TIME = 1;
    public static final Integer BLITZ_TIME = 3;
    public static final Integer STANDARD_TIME = 10;

    private JPanel settingsPanel;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JButton backButton;
    private JCheckBox soundCheckbox;
    private JButton bulletModeButton;
    private JButton blitzModeButton;
    private JButton standardModeButton;
    private P2PChess p2pChess;
    private P2PChess ai2pChess;
    private P2PChess onlineP2PChess;
    private Color primaryColor;
    private Color alternativeColor;
    private Integer timeSelection;
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
        this.ai2pChess = null;
        this.onlineP2PChess = null;
        this.backButton = new JButton("Back");
        this.soundCheckbox = new JCheckBox("Enable Sound");
        this.bulletModeButton = createResizedIconButton("/com/evan/p2pChess/Gui/Images/Game Mode/bullet.png", 200, 125);
        this.blitzModeButton = createResizedIconButton("/com/evan/p2pChess/Gui/Images/Game Mode/blitz.png", 200, 125);
        this.standardModeButton = createResizedIconButton("/com/evan/p2pChess/Gui/Images/Game Mode/standard.png", 200, 125);
        this.primaryColor = WHITE_COLOR;
        this.alternativeColor = BROWN_COLOR;
        this.timeSelection = 1;
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

    public Integer getTimeSelection() {
        return timeSelection;
    }

    //Setters
    public void setP2pChess(P2PChess p2pChess) {
        this.p2pChess = p2pChess;
    }

    public void setAi2PChess(P2PChess ai2pChess) {
        this.ai2pChess = ai2pChess;
    }

    public void setOnlineP2pChess(P2PChess onlineP2PChess) {
        this.onlineP2PChess = onlineP2PChess;
    }

    public void setIsSoundEnabled(boolean isSoundEnabled) {
        Settings.isSoundEnabled = isSoundEnabled;
    }

    public void setTimeSelection(Integer timeSelection) {
        this.timeSelection = timeSelection;
    }

    public void runGui() {
        setupGui();
        setupListeners();
    }

    private void setupGui() {
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
        
        //Gamemode options
        JPanel modePanel = new JPanel();
        modePanel.setOpaque(false);
        modePanel.setLayout(new BoxLayout(modePanel, BoxLayout.X_AXIS));
        modePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        modePanel.add(Box.createHorizontalStrut(15));
        modePanel.add(bulletModeButton);
        modePanel.add(Box.createHorizontalStrut(15));
        modePanel.add(blitzModeButton);
        modePanel.add(Box.createHorizontalStrut(15));
        modePanel.add(standardModeButton);
        
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

        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(soundCheckbox);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(colorOptionsLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(colorGrid);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(modePanel);
        mainPanel.add(Box.createVerticalStrut(10));

        return mainPanel;
    }

    private JButton createResizedIconButton(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(scaled));
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(null));
        button.setOpaque(false);
        button.setPreferredSize(new Dimension(width, height));
        button.setRolloverEnabled(true);
    
        return button;
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
                ai2pChess.updateBoardColors();
                onlineP2PChess.updateBoardColors();
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

    /**
     * highlightSelectedMode()
     * 
     * Sets all the buttons borders off when a different mode is selected.
     * 
     * @param selected
     */
    private void highlightSelectedMode(JButton selected) {
        bulletModeButton.setBorder(null);
        blitzModeButton.setBorder(null);
        standardModeButton.setBorder(null);
        selected.setBorder(BorderFactory.createLineBorder(SELECTED_COLOR, 3));
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

        bulletModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundManager.play(getClass().getResource("/com/evan/p2pChess/Gui/Sounds/select.wav"));

                if (!p2pChess.getHasFirstMove()) { //For the play against human option
                    setTimeSelection(BULLET_TIME);
                    p2pChess.setWhiteTimerLabel(BULLET_TIME.toString());
                    p2pChess.setBlackTimerLabel(BULLET_TIME.toString());
                    highlightSelectedMode(bulletModeButton);
                }

                if (!ai2pChess.getHasFirstMove()) { //For the play against AI option
                    setTimeSelection(BULLET_TIME);
                    ai2pChess.setWhiteTimerLabel(BULLET_TIME.toString());
                    ai2pChess.setBlackTimerLabel(BULLET_TIME.toString());
                    highlightSelectedMode(bulletModeButton);
                }

                if (!onlineP2PChess.getHasFirstMove()) { //For the play online option
                    setTimeSelection(BULLET_TIME);
                    ai2pChess.setWhiteTimerLabel(BULLET_TIME.toString());
                    ai2pChess.setBlackTimerLabel(BULLET_TIME.toString());
                    highlightSelectedMode(bulletModeButton);
                }
            }
        });

        blitzModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundManager.play(getClass().getResource("/com/evan/p2pChess/Gui/Sounds/select.wav"));

                if (!p2pChess.getHasFirstMove()) { //For the play against human option
                    setTimeSelection(BLITZ_TIME);
                    p2pChess.setWhiteTimerLabel(BLITZ_TIME.toString());
                    p2pChess.setBlackTimerLabel(BLITZ_TIME.toString());
                    highlightSelectedMode(blitzModeButton);
                }

                if (!ai2pChess.getHasFirstMove()) { //For the play against AI option
                    setTimeSelection(BLITZ_TIME);
                    ai2pChess.setWhiteTimerLabel(BLITZ_TIME.toString());
                    ai2pChess.setBlackTimerLabel(BLITZ_TIME.toString());
                    highlightSelectedMode(blitzModeButton);
                }

                if (!onlineP2PChess.getHasFirstMove()) { //For the play online option
                    setTimeSelection(BLITZ_TIME);
                    ai2pChess.setWhiteTimerLabel(BLITZ_TIME.toString());
                    ai2pChess.setBlackTimerLabel(BLITZ_TIME.toString());
                    highlightSelectedMode(blitzModeButton);
                }
            }
        });

        standardModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundManager.play(getClass().getResource("/com/evan/p2pChess/Gui/Sounds/select.wav"));

                if (!p2pChess.getHasFirstMove()) { //For the play against human option
                    setTimeSelection(STANDARD_TIME);
                    p2pChess.setWhiteTimerLabel(STANDARD_TIME.toString());
                    p2pChess.setBlackTimerLabel(STANDARD_TIME.toString());
                    highlightSelectedMode(standardModeButton);
                }

                if (!ai2pChess.getHasFirstMove()) { //For the play against AI option
                    setTimeSelection(STANDARD_TIME);
                    ai2pChess.setWhiteTimerLabel(STANDARD_TIME.toString());
                    ai2pChess.setBlackTimerLabel(STANDARD_TIME.toString());
                    highlightSelectedMode(standardModeButton);
                }

                if (!onlineP2PChess.getHasFirstMove()) { //For the play online option
                    setTimeSelection(STANDARD_TIME);
                    ai2pChess.setWhiteTimerLabel(STANDARD_TIME.toString());
                    ai2pChess.setBlackTimerLabel(STANDARD_TIME.toString());
                    highlightSelectedMode(standardModeButton);
                }
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
