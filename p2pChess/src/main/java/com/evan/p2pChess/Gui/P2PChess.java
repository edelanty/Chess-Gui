package com.evan.p2pChess.Gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.evan.p2pChess.Board;
import com.evan.p2pChess.CheckDetector;
import com.evan.p2pChess.FenGenerator;
import com.evan.p2pChess.Game;
import com.evan.p2pChess.Gamemode;
import com.evan.p2pChess.Player;
import com.evan.p2pChess.Uci;
import com.evan.p2pChess.Pieces.Piece;

public class P2PChess {
    private JPanel chessBoardPanel;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private Settings settings;
    private Game game;
    private boolean hasFirstMove;
    private JButton[][] tileButtons;
    private JButton settingsButton;
    private JButton resignButton;
    private JButton exitButton;

    private Board board;
    private Player whitePlayer;
    private Player blackPlayer;

    private int selectedRow;
    private int selectedCol;
    private Piece rightClickHighlightedPiece;
    private int rightClickHighlightedRow;
    private int rightClickHighlightedCol;

    private boolean whiteTurn;
    private int moveNumber;

    private JTable moveHistoryTable;
    private DefaultTableModel moveHistoryModel;
    private JPanel sidePanel;
    private JPanel blackCapturedPanel;
    private JPanel whiteCapturedPanel;
    private JPanel moveListPanel;

    private JLabel whiteCapturedPieceArea;
    private JLabel blackCapturedPieceArea;
    private JTextField whiteScore;
    private JTextField blackScore;
    private JLabel blackTimerLabel;
    private JLabel whiteTimerLabel;

    private Map<String, Integer> whiteCapturedPieces;
    private Map<String, Integer> blackCapturedPieces;

    private Gamemode gamemode;
    private Uci uci;
    private FenGenerator fenGen;
    private String fenString;
    private com.evan.p2pChess.Color playerColor;
    private CheckDetector checkDetector;

    private static final int PIECE_SIZE = 50;
    private static final int MOVE_NUMBER_COLUMN_SIZE = 30;
    private static final int WHITE_MOVE_COLUMN_SIZE = 60;
    private static final int BLACK_MOVE_COLUMN_SIZE = 60;

    public P2PChess(CardLayout cardLayout, JPanel mainPanel, Settings settings, Game game, Gamemode gamemode, Uci uci) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.chessBoardPanel = new JPanel(new BorderLayout());
        this.whitePlayer = new Player("white", 0, com.evan.p2pChess.Color.WHITE);
        this.blackPlayer = new Player("black", 0, com.evan.p2pChess.Color.BLACK);
        this.whiteCapturedPieces = new HashMap<>();
        this.blackCapturedPieces = new HashMap<>();
        this.board = new Board(whitePlayer, blackPlayer);
        this.tileButtons = new JButton[Board.BOARD_SIZE][Board.BOARD_SIZE];
        this.selectedRow = -1;
        this.selectedCol = -1;
        this.sidePanel = new JPanel();
        this.blackCapturedPanel = new JPanel();
        this.whiteCapturedPanel = new JPanel();
        this.moveListPanel = new JPanel();
        this.settingsButton = new JButton();
        this.resignButton = new JButton();
        this.exitButton = new JButton();
        this.rightClickHighlightedPiece = null;
        this.rightClickHighlightedRow = -1;
        this.rightClickHighlightedCol = -1;
        this.whiteTurn = true;
        this.moveNumber = 1;
        this.settings = settings;
        this.game = game;
        this.hasFirstMove = false;
        this.blackTimerLabel = new JLabel();
        this.whiteTimerLabel = new JLabel();
        this.gamemode = gamemode;
        this.fenString = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"; //Default position
        this.uci = uci;
        this.fenGen = new FenGenerator();
        this.playerColor = null;
        this.checkDetector = new CheckDetector(board);
    }
    
    //Getters
    public JPanel getChessBoardPanel() {
        return chessBoardPanel;
    }

    public boolean getHasFirstMove() {
        return hasFirstMove;
    }

    //Setters
    public void setWhiteTimerLabel(String timeSetting) {
        whiteTimerLabel.setText("White: " + timeSetting + ":00");
    }

    public void setBlackTimerLabel(String timeSetting) {
        blackTimerLabel.setText("Black: " + timeSetting + ":00");
    }

    public void setPlayerColor(com.evan.p2pChess.Color playerColor) {
        this.playerColor = playerColor;
    }

    public void setTurnOpposite() {
        whiteTurn = !whiteTurn;
    }

    /**
     * runGui()
     * 
     * Called from the Gui class to instantiate this panel.
     * 
     */
    public void runGui() {
        setupGui();
    }

    /**
     * setupGui()
     * 
     * Adds all the required elements to the main panel used for this class in each area of the border layout.
     * 
     */
    private void setupGui() {
        board.resetBoard();
        chessBoardPanel.add(createChessBoardPanel(), BorderLayout.CENTER);
        chessBoardPanel.add(createMoveHistoryPanel(), BorderLayout.EAST);
        chessBoardPanel.add(createBlackCapturedPiecePanel(), BorderLayout.NORTH);
        chessBoardPanel.add(createWhiteCapturedPiecePanel(), BorderLayout.SOUTH);
        chessBoardPanel.add(createSidePanel(), BorderLayout.WEST);
    }

    /**
     * createChessBoardPanel()
     * 
     * Creates a new JButton for each tile in the board size and updates the display for the tile, creates an action listener, and finally assigns the proper color.
     * 
     * @return
     */
    private JPanel createChessBoardPanel() {
        JPanel gridPanel = new JPanel(new GridLayout(Board.BOARD_SIZE, Board.BOARD_SIZE));

        for (int row = 0; row < Board.BOARD_SIZE; row++) {
            for (int col = 0; col < Board.BOARD_SIZE; col++) {
                JButton tileButton = new JButton();
                tileButtons[row][col] = tileButton;

                updateTileDisplay(row, col);
                createTileActionListener(tileButton, row, col);
                createRightClickTileActionListener(tileButton, row, col);
                assignTileColor(tileButton, row, col);

                tileButton.setFocusable(false);

                gridPanel.add(tileButton);
            }
        }

        return gridPanel;
    }

    /**
     * createMoveHistoryPanel()
     * 
     * Creates the right-side panel with the combined move history for both players.
     * Displays moves in format: "1. ♙e2  ♞c3"
     * 
     * @return moveListPanel to be used on the chessBoardPanel
     */
    private JPanel createMoveHistoryPanel() {
        //Create a panel with custom background color
        moveListPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(settings.getPrimaryColor());
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        
        moveListPanel.setOpaque(true);
        moveListPanel.setBackground(settings.getPrimaryColor());

        //Create table model with three columns: Move#, White, Black
        moveHistoryModel = new DefaultTableModel(
            new Object[][] {}, 
            new String[] {"#", "White", "Black"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        //Create table with the model and custom background painting
        moveHistoryTable = new JTable(moveHistoryModel) {
            @Override
            public boolean isCellSelected(int row, int column) {
                return false;
            }
            
            @Override
            protected void paintComponent(Graphics g) {
                //Paint the background with the desired color before painting the table
                g.setColor(settings.getPrimaryColor());
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        
        //Create a custom cell renderer
        TableCellRenderer customRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                //Apply alternating row colors
                Color bgColor = row % 2 == 0 ? settings.getAlternativeColor() : settings.getPrimaryColor();
                label.setBackground(bgColor);
                label.setHorizontalAlignment(JLabel.CENTER);
                
                return label;
            }
        };
        
        //Set table appearance
        moveHistoryTable.setOpaque(true);
        moveHistoryTable.setFont(new Font("Serif", Font.BOLD, 20));
        moveHistoryTable.setForeground(Color.WHITE);
        moveHistoryTable.setRowHeight(35);
        moveHistoryTable.setTableHeader(null);
        moveHistoryTable.setShowGrid(false);
        moveHistoryTable.setFocusable(false);
        moveHistoryTable.setSelectionBackground(settings.getPrimaryColor());
        moveHistoryTable.setSelectionForeground(Color.WHITE.darker());
        moveHistoryTable.setCellSelectionEnabled(false);
        moveHistoryTable.setRowSelectionAllowed(false);
        moveHistoryTable.setColumnSelectionAllowed(false);
        moveHistoryTable.setDragEnabled(false);
        moveHistoryTable.setIntercellSpacing(new Dimension(0, 0));
        
        //Apply renderer to all columns
        for (int i = 0; i < moveHistoryTable.getColumnCount(); i++) {
            moveHistoryTable.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
        }

        //Adjusting column size
        TableColumnModel columnModel = moveHistoryTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(MOVE_NUMBER_COLUMN_SIZE); 
        columnModel.getColumn(1).setPreferredWidth(WHITE_MOVE_COLUMN_SIZE);
        columnModel.getColumn(2).setPreferredWidth(BLACK_MOVE_COLUMN_SIZE);

        //Create a custom scroll pane with matching background
        JScrollPane scrollPane = new JScrollPane(moveHistoryTable) {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(settings.getPrimaryColor());
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        
        scrollPane.setOpaque(true);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setBackground(settings.getAlternativeColor());
        scrollPane.getViewport().setBackground(settings.getAlternativeColor());
        scrollPane.getViewport().setOpaque(true);

        TitledBorder titledBorder = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(settings.getAlternativeColor(), 2),
            "Move History",
            TitledBorder.CENTER,
            TitledBorder.TOP,
            new Font("Serif", Font.PLAIN, 14),
            Color.BLACK
        );

        moveListPanel.setBorder(titledBorder);

        moveListPanel.add(scrollPane, BorderLayout.CENTER);
        moveListPanel.setPreferredSize(new Dimension(175, 300)); 
        
        return moveListPanel;
    }

    /**
     * createBlackCapturedPiecePanel()
     * 
     * Creates the captured piece panel and returns it to be put into the chessBoardPanel
     * 
     * @return the captured piece JPanel
     */
    private JPanel createBlackCapturedPiecePanel() {
        blackCapturedPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        blackCapturedPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(settings.getAlternativeColor(), 2),
            "Black Captured Pieces",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Serif", Font.PLAIN, 14),
            Color.BLACK
        ));
        blackCapturedPieceArea = new JLabel();
        blackCapturedPieceArea.setFont(new Font("Serif", Font.PLAIN, 25));
        blackCapturedPieceArea.setText(" ");

        blackScore = new JTextField();
        blackScore.setFont(new Font("Serif", Font.PLAIN, 25));
        blackScore.setFocusable(false);
        blackScore.setBorder(null);
        blackScore.setBackground(settings.getPrimaryColor());
        blackScore.setEditable(false);
        blackScore.setText("Score: " + blackPlayer.getPlayerPoints().toString());

        blackCapturedPanel.add(blackScore);
        blackCapturedPanel.add(blackCapturedPieceArea);

        blackCapturedPanel.setBackground(settings.getPrimaryColor());

        return blackCapturedPanel;
    }
    
    /**
     * createWhiteCapturedPiecePanel()
     * 
     * Creates the captured piece panel and returns it to be put into the chessBoardPanel
     * 
     * @return the captured piece JPanel
     */
    private JPanel createWhiteCapturedPiecePanel() {
        whiteCapturedPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        whiteCapturedPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(settings.getAlternativeColor(), 2),
            "White Captured Pieces",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Serif", Font.PLAIN, 14),
            Color.BLACK
        ));
        whiteCapturedPieceArea = new JLabel();
        whiteCapturedPieceArea.setFont(new Font("Serif", Font.PLAIN, 25));
        whiteCapturedPieceArea.setText(" ");

        whiteScore = new JTextField();
        whiteScore.setFont(new Font("Serif", Font.PLAIN, 25));
        whiteScore.setFocusable(false);
        whiteScore.setBorder(null);
        whiteScore.setBackground(settings.getPrimaryColor());
        whiteScore.setEditable(false);
        whiteScore.setText("Score: " + whitePlayer.getPlayerPoints().toString());

        whiteCapturedPanel.add(whiteScore);
        whiteCapturedPanel.add(whiteCapturedPieceArea);

        whiteCapturedPanel.setBackground(settings.getPrimaryColor());

        return whiteCapturedPanel;
    }

    /**
     * createSidePanel()
     * 
     * Creates all the needed gui elements for the left-side panel of the chess board gui.
     * 
     * @return sidePanel which contains all the elements needed.
     */
    private JPanel createSidePanel() {
        //Create the side panel with a white background to match the rest of UI
        sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setSize(new Dimension(500, 500));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(15, 5, 5, 5));
        sidePanel.setPreferredSize(new Dimension(150, 0));
        sidePanel.setBackground(settings.getPrimaryColor());

        //Create the buttons using the same styling approach as the main application
        settingsButton = createSidePanelButton("Settings");
        resignButton = createSidePanelButton("Resign");
        exitButton = createSidePanelButton("Exit");

        //Add icons to buttons with appropriate sizing
        settingsButton.setFont(new Font("Serif", Font.BOLD, 24));
        resignButton.setFont(new Font("Serif", Font.BOLD, 24));
        exitButton.setFont(new Font("Serif", Font.BOLD, 24));

        //Set distinct button colors for better visibility
        settingsButton.setBackground(settings.getAlternativeColor());
        resignButton.setBackground(settings.getAlternativeColor());
        exitButton.setBackground(settings.getAlternativeColor());

        settingsButton.setForeground(Color.WHITE);
        resignButton.setForeground(Color.WHITE);
        exitButton.setForeground(Color.WHITE);

        //Create timer labels
        styleTimerLabels();

        //Create action listeners
        createExitButtonActionListener(exitButton);
        createSettingsButtonActionListener(settingsButton);
        createResignButtonActionListener(resignButton);

        //Position timers and buttons
        sidePanel.add(blackTimerLabel);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidePanel.add(exitButton);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidePanel.add(resignButton);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidePanel.add(settingsButton);
        sidePanel.add(Box.createVerticalGlue());
        sidePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidePanel.add(whiteTimerLabel);

        return sidePanel;
    }

    /**
     * styleTimerLabels()
     * 
     * Creates the initial gui elements for the black and white timers.
     * 
     */
    private void styleTimerLabels() {
        Font timerFont = new Font("Arial", Font.PLAIN, 20);

        //Black Timer Label
        blackTimerLabel = new JLabel("Black: " + settings.getTimeSelection().toString() + ":00", JLabel.CENTER);
        blackTimerLabel.setFont(timerFont);
        blackTimerLabel.setForeground(Color.WHITE);
        blackTimerLabel.setBackground(Settings.GRAY_DARK_BOX_COLOR);
        blackTimerLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        blackTimerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        blackTimerLabel.setOpaque(true);

        //White Timer Label
        whiteTimerLabel = new JLabel("White: " + settings.getTimeSelection().toString() + ":00", JLabel.CENTER);
        whiteTimerLabel.setFont(timerFont);
        whiteTimerLabel.setForeground(Color.WHITE);
        whiteTimerLabel.setBackground(Settings.GRAY_DARK_BOX_COLOR);
        whiteTimerLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        whiteTimerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        whiteTimerLabel.setOpaque(true);
    }

    /**
     * createSidePanelButton()
     * 
     * Helper method to create consistently styled buttons for the side panel.
     * 
     * @param text
     * @return
     */
    private JButton createSidePanelButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        button.setPreferredSize(new Dimension(60, 60));
        button.setMaximumSize(new Dimension(500, 500));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        return button;
    }

    /**
     * handleTileClick()
     * 
     * Based on the turn and selected piece handles the moving and clicking of tiles on the GUI.
     * 
     * @param row
     * @param col
     */
    private void handleTileClick(int row, int col) {
        Piece clickedPiece = board.getPieceAt(row, col);

        //Resetting any previous right click code
        if (rightClickHighlightedPiece != null) {
            resetHighlightedTiles();
            rightClickHighlightedPiece = null;
            rightClickHighlightedRow = -1;
            rightClickHighlightedCol = -1;
        }

        if (selectedRow == -1 && clickedPiece != null && isCorrectTurn(clickedPiece)) { //Player hasn't clicked anything yet and has just selected a piece
            //Keep track of previous selected tile information to display on the GUI 
            selectedRow = row;
            selectedCol = col;

            //Visual effect for selecting a piece
            tileButtons[row][col].setBorder(BorderFactory.createLineBorder(java.awt.Color.RED, 3));
            tileButtons[row][col].setBackground(Settings.RED_COLOR);
            chessBoardPanel.repaint();
        } else if (selectedRow != -1) { //This means we've clicked a piece and are about to try and move it
            Piece selectedPiece = board.getPieceAt(selectedRow, selectedCol);
            Piece destinationPiece = board.getPieceAt(row, col);

            handleSuccessfulMoves(selectedPiece, destinationPiece, row, col);

            //Reset for next click
            resetHighlightedTiles();
            selectedRow = -1;
            selectedCol = -1;

            if (gamemode == Gamemode.HUMAN_VS_AI && isAiTurn()) { //If it's the AI's turn after a successful human move
                playAIMove();
            }
        }
    }

    /**
     * handleSuccessfulMoves()
     * 
     * Checks for a capture, plays a sound, invokes move, updates display for potential captures or history, and switches turns.
     * 
     * @param selectedPiece Piece moving from
     * @param destinationPiece Piece moving to
     * @param row Row moving to
     * @param col Col moving to
     */
    private void handleSuccessfulMoves(Piece selectedPiece, Piece destinationPiece, int row, int col) {
        if (selectedPiece != null && selectedPiece.isValidMove(row, col, board) && !checkDetector.isKingInCheckAfterMove(selectedPiece, selectedRow, selectedCol, row, col, selectedPiece.getPieceColor())) { //Executed after successful move
            //If there's no capture play the normal piece move sound otherwise don't do anything
            if (!checkForCapture(destinationPiece, selectedPiece)) {
                board.playPieceMovingSound();
            }
            //Move the piece
            selectedPiece.move(row, col, board);
            //Update the GUI
            refreshBoardDisplay();
            updateMoveListDisplay(selectedPiece, selectedRow, selectedCol, row, col);
            //Start the game clock if needed
            startGameClockIfFirstMove();
            //Check for ending, if ended don't switch back turns
            if (!checkForEndGame(selectedPiece)) {
                whiteTurn = !whiteTurn;
                game.switchTurns(whiteTurn);
            }
            //Generate new FEN string for the AI if playing against
            if (gamemode == Gamemode.HUMAN_VS_AI) {
                fenString = fenGen.generateFEN(board, whiteTurn, moveNumber);
            }
        } else { //If not a valid move shake the piece around
            invalidMoveFeedback(selectedRow, selectedCol);
        }
    }

    private boolean checkForEndGame(Piece currentPiece) {
        boolean isEndGame = false;
        com.evan.p2pChess.Color currentColor = currentPiece.getPieceColor();
        com.evan.p2pChess.Color opponentColor = (currentColor == com.evan.p2pChess.Color.WHITE) ? com.evan.p2pChess.Color.BLACK : com.evan.p2pChess.Color.WHITE;
        
        if (checkDetector.isKingInCheck(opponentColor)) {
            //Notify the player that the opponent is in check
            board.playPieceCheckSound();
            
            //Then check if it's checkmate
            if (checkDetector.isCheckmate(opponentColor)) {
                //Game over - current player wins
                chessBoardPanel.repaint();
                isEndGame = true;
                handleCheckmate();
            }
        } else {
            //Check for stalemate
            if (checkDetector.isStalemate(opponentColor)) {
                //Game over - draw
                isEndGame = true;
                handleStalemate();
            }
        }

        return isEndGame;
    }

    private void handleCheckmate() {
        board.playPieceCheckmateSound();
        game.gameOver();
        showEndGameDialog("Checkmate");
    }

    private void handleStalemate() {
        game.gameOver();
        showEndGameDialog("Stalemate");
    }

    public void handleTimeRanOut() {
        showEndGameDialog("Time");
    }

    /**
     * showEndGameDialog()
     * 
     * Popups a dialogue when ending a game.
     * 
     * @param messageText Gives a message depending on this param
     */
    private void showEndGameDialog(String messageText) {
        String winner = (whiteTurn) ? whitePlayer.getPlayerName() : blackPlayer.getPlayerName();
        EndGameDialog dialog = new EndGameDialog(null, messageText, winner, mainPanel, cardLayout, this, board);
        dialog.setVisible(true);
    }

    /**
     * isAiTurn()
     * 
     * Returns true or false depending on if it's the AI's move or not.
     * 
     * @return
     */
    private boolean isAiTurn() {
        boolean isAiTurn = false;

        if (playerColor == com.evan.p2pChess.Color.WHITE && whiteTurn) {
            isAiTurn = false;
        } else if (playerColor == com.evan.p2pChess.Color.BLACK && !whiteTurn) {
            isAiTurn = false;
        } else {
            isAiTurn = true;
        }

        return isAiTurn;
    }

    public void playAIMove() {
        //Create and start a new thread for AI move calculation
        new Thread(() -> {
            try {
                //Step 1: Get best move from engine (this is the time-consuming part)
                final String bestMove = uci.getBestMove(fenString);
                
                // Once we have the move, we need to update the UI on the EDT
                SwingUtilities.invokeLater(() -> {
                    try {
                        if (bestMove == null || bestMove.length() < 4) {
                            // Handle case where no valid move was returned
                            JOptionPane.showMessageDialog(null, 
                                "Engine couldn't find a valid move.", 
                                "AI Error", 
                                JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        
                        //Parse move
                        int fromCol = bestMove.charAt(0) - 'a';
                        int fromRow = 8 - Character.getNumericValue(bestMove.charAt(1));
                        int toCol = bestMove.charAt(2) - 'a';
                        int toRow = 8 - Character.getNumericValue(bestMove.charAt(3));
                        
                        //Validate the move coordinates
                        if (fromRow < 0 || fromRow >= 8 || fromCol < 0 || fromCol >= 8 ||
                            toRow < 0 || toRow >= 8 || toCol < 0 || toCol >= 8) {
                            JOptionPane.showMessageDialog(null, 
                                "Engine returned invalid move coordinates: " + bestMove, 
                                "AI Error", 
                                JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        
                        Piece piece = board.getPieceAt(fromRow, fromCol);
                        
                        if (piece == null) {
                            JOptionPane.showMessageDialog(null, 
                                "No piece found at position: " + bestMove.substring(0, 2), 
                                "AI Error", 
                                JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        
                        Piece destinationPiece = board.getPieceAt(toRow, toCol);
                        
                        //Save previous position for move history display
                        int prevRow = piece.getPieceRow();
                        int prevCol = piece.getPieceCol();
                        
                        //Move the piece
                        piece.move(toRow, toCol, board);
                        
                        //Update the GUI
                        refreshBoardDisplay();
                        updateMoveListDisplay(piece, prevRow, prevCol, toRow, toCol);
                        //Start the game clock if needed
                        startGameClockIfFirstMove();

                        //Play sound
                        if (!checkForCapture(destinationPiece, piece)) {
                            board.playPieceMovingSound();
                        }

                        //Check for end
                        checkForEndGame(piece);

                        //Switch turn logic
                        whiteTurn = !whiteTurn;
                        game.switchTurns(whiteTurn);
                    } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, 
                            "Error executing AI move: " + e.getMessage(), 
                            "AI Error", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                
                //Show error on EDT
                SwingUtilities.invokeLater(() -> {
                    //Show error dialog to user
                    JOptionPane.showMessageDialog(null, 
                        "Error calculating AI move: " + e.getMessage(), 
                        "AI Error", 
                        JOptionPane.ERROR_MESSAGE);
                });
            }
        }).start();
    }

    /*
     * startGameClockIfFirstMove()
     * 
     * Starts the game glock if the first has been played.
     * 
     */
    private void startGameClockIfFirstMove() {
        if (!hasFirstMove) {
            //Init the actual timers
            whiteTimerLabel.setText("White: " + settings.getTimeSelection().toString() + ":00");
            blackTimerLabel.setText("Black: " + settings.getTimeSelection().toString() + ":00");
            game.initializeTimers(blackTimerLabel, whiteTimerLabel, settings.getTimeSelection(), this);
            game.startGameClock(whiteTurn);
            hasFirstMove = !hasFirstMove;
        }
    }

    /**
     * handleRightTileClick()
     * 
     * Shows possible moves for any right clicked piece.
     * 
     * @param row
     * @param col
     */
    private void handleRightTileClick(int row, int col) {
        Piece clickedPiece = board.getPieceAt(row, col);

        //If we right-click the same piece that was already highlighted, reset highlights
        if (rightClickHighlightedRow == row && rightClickHighlightedCol == col && rightClickHighlightedPiece == clickedPiece) {
            resetHighlightedTiles();
            rightClickHighlightedPiece = null;
            rightClickHighlightedRow = -1;
            rightClickHighlightedCol = -1;
            
            return;
        }
        
        //Reset previous highlights if any
        resetHighlightedTiles();
        
        //If there's a piece at this location, show its moves
        if (clickedPiece != null) {
            //Store the piece we're highlighting
            rightClickHighlightedPiece = clickedPiece;
            rightClickHighlightedRow = row;
            rightClickHighlightedCol = col;
            
            //Show possible moves
            clickedPiece.drawPossiblePieceMoves(this, board);
        }

    }

    /**
     * isCorrectTurn()
     * 
     * Returns true if the current turn reflects the color of the piece clicked.
     * 
     * @param piece
     * @return
     */
    private boolean isCorrectTurn(Piece piece) {
        //Block interactions during AI's turn
        if (gamemode == Gamemode.HUMAN_VS_AI) {
            //Block interactions before first AI move when player is black
            if (playerColor == com.evan.p2pChess.Color.BLACK && !hasFirstMove) {
                return false;
            }
            
            //Block interactions during AI's turn
            if (isAiTurn()) {
                return false;
            }
        }
        
        com.evan.p2pChess.Color pieceColor = piece.getPieceColor();
        
        //For human vs human mode
        if (gamemode != Gamemode.HUMAN_VS_AI) {
            return (pieceColor == com.evan.p2pChess.Color.WHITE && whiteTurn) || 
                   (pieceColor == com.evan.p2pChess.Color.BLACK && !whiteTurn);
        }
        
        //For human vs AI mode
        //In this mode, player can only move their own color
        return pieceColor == playerColor && 
               ((playerColor == com.evan.p2pChess.Color.WHITE && whiteTurn) || 
                (playerColor == com.evan.p2pChess.Color.BLACK && !whiteTurn));
    }

    /**
     * checkForCapture()
     * 
     * Check if a capture will happen and update the capture display (before we "move" our other piece)
     * 
     * @param destinationPiece
     * @param selectedPiece
     */
    private boolean checkForCapture(Piece destinationPiece, Piece selectedPiece) {
        boolean isACapture = false;

        if (destinationPiece != null && destinationPiece.getPieceColor() != selectedPiece.getPieceColor()) {
            updateCapturedPieceDisplay(destinationPiece);
            isACapture = true;
        }

        return isACapture;
    }

    /**
     * refreshBoardDisplay()
     * 
     * Iterates through the board and updates the tiles display to either show a piece or a blank space.
     * 
     */
    private void refreshBoardDisplay() {
        for (int row = 0; row < Board.BOARD_SIZE; row++) {
            for (int col = 0; col < Board.BOARD_SIZE; col++) {
                updateTileDisplay(row, col);
            }
        }
    }

    /**
     * updateTileDisplay()
     * 
     * Grabs the piece sitting on the board tiles and the respective JButton of that tile.
     * Takes those elements and assigns either an empty string or the piece symbol of the respective tile.
     * 
     * @param row
     * @param col
     */
    private void updateTileDisplay(int row, int col) {
        Piece piece = board.getPieceAt(row, col);
        JButton button = tileButtons[row][col];
        button.setRolloverEnabled(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());

        //If the board has a piece on it, set that tile to display the proper piece
        if (piece != null) {
            button.setIcon(piece.getPieceImageIcon());
            button.setFont(new Font("Serif", Font.PLAIN, PIECE_SIZE));
        } else {
            button.setIcon(null);
            button.setText("");
        }
    }

    /**
     * updateMoveListDisplay()
     * 
     * Adds to the players movelist and displays the move history on the GUI.
     * 
     * @param piece
     * @param row
     * @param col
     */
    private void updateMoveListDisplay(Piece piece, int prevRow, int prevCol, int row, int col) {
        //Formatting the string to enter the queue
        String from = convertToAlgebraic(prevRow, prevCol);
        String to = convertToAlgebraic(row, col);
        String moveNotationPlayer = from + to; //e2e4
        String moveNotationGui = piece.getPieceSymbol() + to; //♙e4

        //Inserts into the player queue
        if (piece.getPieceColor() == com.evan.p2pChess.Color.WHITE) {
            whitePlayer.addPlayerMove(moveNotationPlayer);
            //Add or update row in the table
            updateMoveHistoryTable(moveNotationGui, null);
        } else {
            blackPlayer.addPlayerMove(moveNotationPlayer);
            //Update the black move in the current row
            updateMoveHistoryTable(null, moveNotationGui);
            //Increment move number after black's move
            moveNumber++;
        }

        //Update scores as well
        whiteScore.setText(whitePlayer.getPlayerPoints().toString());
        blackScore.setText(blackPlayer.getPlayerPoints().toString());
    }

    /**
     * Updates the move history table
     * 
     * @param whiteMove White's move notation (or null if updating black's move)
     * @param blackMove Black's move notation (or null if adding white's move)
     */
    private void updateMoveHistoryTable(String whiteMove, String blackMove) {
        //For white's move, add a new row
        if (whiteMove != null) {
            //Check if we need to add a new row
            if (moveHistoryModel.getRowCount() < moveNumber || moveHistoryModel.getValueAt(moveNumber - 1, 1) != null) {
                //Add a new row
                moveHistoryModel.addRow(new Object[]{moveNumber, whiteMove, ""});
            } else {
                //Update existing row
                moveHistoryModel.setValueAt(moveNumber, moveNumber - 1, 0);
                moveHistoryModel.setValueAt(whiteMove, moveNumber - 1, 1);
            }
        }
        
        //For black's move, update the current row
        if (blackMove != null && moveHistoryModel.getRowCount() >= moveNumber) {
            moveHistoryModel.setValueAt(blackMove, moveNumber - 1, 2);
        }
        
        //Scroll to the bottom row
        moveHistoryTable.scrollRectToVisible(
            moveHistoryTable.getCellRect(moveHistoryTable.getRowCount() - 1, 0, true)
        );
    }

    /**
     * updateCapturedPieceDisplay()
     * 
     * Helper function for the captured piece area for both the black and white pieces.
     * Adds the pieces from each players captures into the hasmap to be formatted later and displayed.
     * 
     * @param piece
     */
    private void updateCapturedPieceDisplay(Piece piece) {
        //Insert into the player captured pieces queue and update the GUI
        String symbol = piece.getPieceSymbol();

        if (piece.getPieceColor() == com.evan.p2pChess.Color.WHITE) {
            blackCapturedPieces.put(symbol, blackCapturedPieces.getOrDefault(symbol, 0) + 1);
            blackCapturedPieceArea.setText(formatCapturedPiecesDisplay(blackCapturedPieces));
            blackPlayer.addPlayerCapturedPiece(symbol);
        } else {
            whiteCapturedPieces.put(symbol, whiteCapturedPieces.getOrDefault(symbol, 0) + 1);
            whiteCapturedPieceArea.setText(formatCapturedPiecesDisplay(whiteCapturedPieces));
            whitePlayer.addPlayerCapturedPiece(symbol);
        }
    }

    /**
     * formatCapturedPiecesDisplay()
     * 
     * Formats the piece entries utilizing a hashmap to efficiently create the count of a certain captured piece.
     * 
     * @param capturedMap
     * @return
     */
    private String formatCapturedPiecesDisplay(Map<String, Integer> capturedMap) {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, Integer> entry : capturedMap.entrySet()) {
            sb.append(entry.getKey());
            if (entry.getValue() > 1) sb.append("x").append(entry.getValue());
            sb.append(" ");
        }

        return sb.toString().trim();
    }

    /**
     * invalidMoveFeedback()
     * 
     * Shakes the tile in which the move was invalid for.
     * 
     * @param row
     * @param col
     */
    private void invalidMoveFeedback(int row, int col) {
        JButton tileButton = tileButtons[row][col];
        Point originalLocation = tileButton.getLocation();

        Timer timer = new Timer(10, null);
        final int[] count = {0};

        timer.addActionListener(e -> {
            int dx = (count[0] % 2 == 0) ? 5 : -5;
            tileButton.setLocation(originalLocation.x + dx, originalLocation.y);
            count[0]++;

            if (count[0] >= 6) {
                timer.stop();
                tileButton.setLocation(originalLocation);
            }
        });

        timer.start();
    }

    /**
     * moveHighlightTile()
     * 
     * Sets a tile to the highlight color.
     * 
     * @param row
     * @param col
     */
    public void moveHighlightTile(com.evan.p2pChess.Color currentPieceColor, int row, int col) {
        JButton tileButton = tileButtons[row][col];
        Piece piece = board.getPieceAt(row, col);

        if (piece != null && currentPieceColor != piece.getPieceColor()) {
            tileButton.setBackground(Settings.DARKER_HIGHLIGHT_YELLOW);
        } else { //Normal highlight
            tileButton.setBackground(Settings.HIGHLIGHT_YELLOW);
        }

        chessBoardPanel.repaint();
    }

    /**
     * resetHighlightedTiles()
     * 
     * Iterates through the entire board and resets the original colors of the tiles to get rid of the potential move highlights.
     * 
     */
    private void resetHighlightedTiles() {
        JButton tempButton = new JButton();

        for (int row = 0; row < Board.BOARD_SIZE; row++) {
            for (int col = 0; col < Board.BOARD_SIZE; col++) {
                if ((row + col) % 2 == 0) {
                    tileButtons[row][col].setBackground(settings.getPrimaryColor());
                } else {
                    tileButtons[row][col].setBackground(settings.getAlternativeColor());
                }

                tileButtons[row][col].setBorder(tempButton.getBorder()); //Resets to the proper border after moving
                tileButtons[row][col].setBorder(BorderFactory.createRaisedBevelBorder());
                chessBoardPanel.repaint();
            }
        }
    }
    
    /**
     * updateBoardColors()
     * 
     * Availible for the settings class to allow the user to change their primary and alt colors.
     * 
     */
    public void updateBoardColors() {
        resetHighlightedTiles();
        resetPanelAndButtonColors();
    }

    /**
     * resetPanelAndButtonColors()
     * 
     * Resets colors for side panels and buttons.
     * 
     */
    private void resetPanelAndButtonColors() {
        Color primary = settings.getPrimaryColor(), alternative = settings.getAlternativeColor();
        settingsButton.setBackground(alternative);
        resignButton.setBackground(alternative);
        exitButton.setBackground(alternative);
        sidePanel.setBackground(primary);
        blackScore.setBackground(primary);
        whiteScore.setBackground(primary);
        blackCapturedPanel.setBackground(primary);
        whiteCapturedPanel.setBackground(primary);
        //Update captured panel borders
        whiteCapturedPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(alternative, 2),
            "White Captured Pieces",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Serif", Font.PLAIN, 14),
            Color.BLACK
        ));
        blackCapturedPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(alternative, 2),
            "Black Captured Pieces",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Serif", Font.PLAIN, 14),
            Color.BLACK
        ));
        TitledBorder titledBorder = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(settings.getAlternativeColor(), 2),
            "Move History",
            TitledBorder.CENTER,
            TitledBorder.TOP,
            new Font("Serif", Font.PLAIN, 14),
            Color.BLACK
        );
        moveListPanel.setBorder(titledBorder);
        //Update move history table and related components
        moveHistoryTable.setBackground(alternative);
        moveHistoryTable.getParent().setBackground(alternative);
        ((JScrollPane)moveHistoryTable.getParent().getParent()).setBackground(alternative);
        ((JScrollPane)moveHistoryTable.getParent().getParent()).getViewport().setBackground(alternative);
        moveHistoryTable.repaint();
        blackCapturedPanel.repaint();
        whiteCapturedPanel.repaint();
        sidePanel.repaint();
    }

    /**
     * assignTileColor()
     * 
     * Assigning each color based on which tile we are currently on.
     * 
     * @param button
     * @param row
     * @param col
     */
    private void assignTileColor(JButton button, int row, int col) {
        if ((row + col) % 2 == 0) {
            button.setBackground(settings.getPrimaryColor());
        } else {
            button.setBackground(settings.getAlternativeColor());
        }
    }

    /**
     * convertToAlgebraic()
     * 
     * Formats string for move history.
     * 
     * @param row
     * @param col
     * @return
     */
    private String convertToAlgebraic(int row, int col) {
        char file = (char) ('a' + col);
        int rank = 8 - row;
        return file + String.valueOf(rank);
    }

    /**
     * createTileActionListener()
     * 
     * Creates the action listener for any tile which simply calls handleClick when anyone of them is pressed.
     * 
     * @param button
     * @param row
     * @param col
     */
    private void createTileActionListener(JButton button, int row, int col) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleTileClick(row, col);
            }
        });
    }

     /**
     * createRightClickTileActionListener()
     * 
     * Creates the action listener for any tile which simply calls handleRightClick when anyone of them is pressed with right click.
     * 
     * @param button
     * @param row
     * @param col
     */
    private void createRightClickTileActionListener(JButton button, int row, int col) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    handleRightTileClick(row, col);
                }
            }
        });
    }

    /**
     * createExitButtonActionListener()
     * 
     * Creates an action listener for the exit button on the chess board gui, takes the user back to the start screen.
     * 
     * @param button
     */
    private void createExitButtonActionListener(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Start Screen");
            }
        });
    }
    
    /**
     * createSettingsButtonActionListener()
     * 
     * Creates an action listener for the settings button, allows a user to shift the main panel to the settings page.
     * 
     * @param button
     */
    private void createSettingsButtonActionListener(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Settings");
            }
        });
    }

    /**
     * createResignButtonActionListener()
     * 
     * Creates an action listener for the resign button.
     * 
     * @param button
     */
    private void createResignButtonActionListener(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!hasFirstMove) { //Don't do anything if timers haven't begun
                    return;
                }

                if (gamemode == Gamemode.HUMAN_VS_AI && isAiTurn()) { //Can't resign for the AI
                    return;
                }

                handleResignation();
            }
        });
    }

    private void handleResignation() {
        game.gameOver();
        showEndGameDialog("Resignation");
    }

    /**
     * newGame()
     * 
     * Resets the internal controller objects and the GUI.
     * 
     */
    public void newGame() {
        //Reset the board controller
        board.resetBoard();
        //Reset the players
        whitePlayer.resetPlayer();
        blackPlayer.resetPlayer();
        //Reset captured pieces
        whiteCapturedPieces.clear();
        blackCapturedPieces.clear();
        //Reset FEN string
        fenString = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        //White moves first and there's no move yet
        whiteTurn = true;
        hasFirstMove = false;
        moveNumber = 1;
        //Reset the move list table
        DefaultTableModel model = (DefaultTableModel) moveHistoryTable.getModel();
        model.setRowCount(0); //Clear all rows
        //Reset the timers based on the time selection
        whiteTimerLabel.setText("White: " + settings.getTimeSelection().toString() + ":00");
        blackTimerLabel.setText("Black: " + settings.getTimeSelection().toString() + ":00");
        //Reset GUI elements
        refreshBoardDisplay();
        chessBoardPanel.revalidate();
        chessBoardPanel.repaint();
        whiteCapturedPanel.revalidate();
        whiteCapturedPanel.repaint();
        blackCapturedPanel.revalidate();
        blackCapturedPanel.repaint();
        moveListPanel.revalidate();
        moveListPanel.repaint();
    }

}
