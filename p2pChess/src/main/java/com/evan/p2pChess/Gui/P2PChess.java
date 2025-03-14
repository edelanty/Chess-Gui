package com.evan.p2pChess.Gui;

import com.evan.p2pChess.Board;
import com.evan.p2pChess.Pieces.*;
import com.evan.p2pChess.Player;
import com.evan.p2pChess.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class P2PChess {
    private JPanel chessBoardPanel;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JButton[][] tileButtons;
    private Board board;
    private Player whitePlayer;
    private Player blackPlayer;

    private int selectedRow;
    private int selectedCol;
    private boolean whiteTurn;
    private int moveNumber;

    private Color PRIMARY_COLOR;
    private Color ALTERNATIVE_COLOR;

    private JTextArea whiteMovesArea;
    private JTextArea blackMovesArea;
    private JLabel whiteCapturedPieceArea;
    private JLabel blackCapturedPieceArea;
    private JTextField whiteScore;
    private JTextField blackScore;

    private Map<String, Integer> whiteCapturedPieces;
    private Map<String, Integer> blackCapturedPieces;

    private static final int PIECE_SIZE = 50;

    public P2PChess(CardLayout cardLayout, JPanel mainPanel) {
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
        this.whiteTurn = true;
        this.moveNumber = 1;
        this.PRIMARY_COLOR = Settings.WHITE_COLOR;
        this.ALTERNATIVE_COLOR = Settings.BROWN_COLOR;
    }

    public JPanel getChessBoardPanel() {
        return chessBoardPanel;
    }

    public void runGUI() {
        setupGui();
    }

    private void setupGui() {
        board.resetBoard();
        chessBoardPanel.add(createChessBoardPanel(), BorderLayout.CENTER);
        chessBoardPanel.add(createMoveHistoryPanel(), BorderLayout.EAST);
        chessBoardPanel.add(createBlackCapturedPiecePanel(), BorderLayout.NORTH);
        chessBoardPanel.add(createWhiteCapturedPiecePanel(), BorderLayout.SOUTH);
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
                assignTileColor(tileButton, row, col);

                tileButton.setFocusable(false);

                gridPanel.add(tileButton);
            }
        }

        return gridPanel;
    }

    private JPanel createMoveHistoryPanel() {
        JPanel moveListPanel = new JPanel(new GridLayout(2, 1));

        whiteMovesArea = new JTextArea();
        blackMovesArea = new JTextArea();

        whiteMovesArea.setEditable(false);
        whiteMovesArea.setFocusable(false);
        blackMovesArea.setEditable(false);
        blackMovesArea.setFocusable(false);

        whiteMovesArea.setFont(new Font("Serif", Font.PLAIN, 25));
        blackMovesArea.setFont(new Font("Serif", Font.PLAIN, 25));

        JPanel whitePanel = new JPanel(new BorderLayout());
        whitePanel.setBorder(BorderFactory.createTitledBorder(whitePlayer.getPlayerName()));
        whitePanel.add(new JScrollPane(whiteMovesArea), BorderLayout.CENTER);

        JPanel blackPanel = new JPanel(new BorderLayout());
        blackPanel.setBorder(BorderFactory.createTitledBorder(blackPlayer.getPlayerName()));
        blackPanel.add(new JScrollPane(blackMovesArea), BorderLayout.CENTER);

        whiteMovesArea.setPreferredSize(new Dimension(140, 0));
        blackMovesArea.setPreferredSize(new Dimension(140, 0));

        moveListPanel.add(whitePanel);
        moveListPanel.add(blackPanel);

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
        JPanel blackCapturedPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        blackCapturedPanel.setBorder(BorderFactory.createTitledBorder("Black Captured Pieces"));
        blackCapturedPieceArea = new JLabel();
        blackCapturedPieceArea.setFont(new Font("Serif", Font.PLAIN, 25));
        blackCapturedPieceArea.setText(" ");

        blackScore = new JTextField();
        blackScore.setFont(new Font("Serif", Font.PLAIN, 25));
        blackScore.setFocusable(false);
        blackScore.setBorder(null);
        blackScore.setEditable(false);
        blackScore.setText("Score: " + blackPlayer.getPlayerPoints().toString());

        blackCapturedPanel.add(blackScore);
        blackCapturedPanel.add(blackCapturedPieceArea);

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
        JPanel whiteCapturedPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        whiteCapturedPanel.setBorder(BorderFactory.createTitledBorder("White Captured Pieces"));
        whiteCapturedPieceArea = new JLabel();
        whiteCapturedPieceArea.setFont(new Font("Serif", Font.PLAIN, 25));
        whiteCapturedPieceArea.setText(" ");

        whiteScore = new JTextField();
        whiteScore.setFont(new Font("Serif", Font.PLAIN, 25));
        whiteScore.setFocusable(false);
        whiteScore.setBorder(null);
        whiteScore.setEditable(false);
        whiteScore.setText("Score: " + blackPlayer.getPlayerPoints().toString());

        whiteCapturedPanel.add(whiteScore);
        whiteCapturedPanel.add(whiteCapturedPieceArea);

        return whiteCapturedPanel;
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

        if (selectedRow == -1 && clickedPiece != null && isCorrectTurn(clickedPiece)) { //Player hasn't clicked anything yet and has just selected a piece
            //Keep track of previous selected tile information to display on the GUI 
            selectedRow = row;
            selectedCol = col;

            //Visual effect for selecting a piece
            tileButtons[row][col].setBorder(BorderFactory.createLineBorder(java.awt.Color.RED, 3));
            tileButtons[row][col].setBackground(Settings.RED_COLOR);
        } else if (selectedRow != -1) { //This means we've clicked a piece and are about to try and move it
            Piece selectedPiece = board.getPieceAt(selectedRow, selectedCol);
            Piece destinationPiece = board.getPieceAt(row, col);

            if (selectedPiece != null && selectedPiece.isValidMove(row, col, board)) { //Executed after successful move
                checkForCapture(destinationPiece, selectedPiece);
                selectedPiece.move(row, col, board);
                refreshBoardDisplay();
                updateMoveListDisplay(selectedPiece, selectedRow, selectedCol, row, col);
                whiteTurn = !whiteTurn;
            } else {
                //TODO user feedback for invalid move
            }

            //Reset for next click
            JButton tempButton = new JButton();
            tileButtons[selectedRow][selectedCol].setBorder(tempButton.getBorder()); //Resets to the proper border after moving
            if ((selectedRow + selectedCol) % 2 == 0) {
                tileButtons[selectedRow][selectedCol].setBackground(PRIMARY_COLOR);
            } else {
                tileButtons[selectedRow][selectedCol].setBackground(ALTERNATIVE_COLOR);
            }

            selectedRow = -1;
            selectedCol = -1;
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
        boolean isCorrectTurn;

        if (piece.getPieceColor() == com.evan.p2pChess.Color.WHITE && whiteTurn) {
            isCorrectTurn = true;
        } else if (piece.getPieceColor() == com.evan.p2pChess.Color.BLACK && !whiteTurn) {
            isCorrectTurn = true;
        } else {
            isCorrectTurn = false;
        }

        return isCorrectTurn;
    }

    /**
     * checkForCapture()
     * 
     * Check if a capture will happen and update the capture display (before we "move" our other piece)
     * 
     * @param destinationPiece
     * @param selectedPiece
     */
    private void checkForCapture(Piece destinationPiece, Piece selectedPiece) {
        if (destinationPiece != null && destinationPiece.getPieceColor() != selectedPiece.getPieceColor()) {
            updateCapturedPieceDisplay(destinationPiece);
        }
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

        //If the board has a piece on it, set that tile to display the proper piece
        if (piece != null) {
            button.setText(piece.getPieceSymbol());
            button.setFont(new Font("Serif", Font.PLAIN, PIECE_SIZE));
        } else {
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
        String moveNotation = piece.getPieceSymbol() + " " + from + " → " + to;

        //Inserts into the player queue and updates the GUI
        if (piece.getPieceColor() == com.evan.p2pChess.Color.WHITE) {
            whitePlayer.addPlayerMove(moveNumber + ". " + moveNotation);
            whiteMovesArea.append(moveNumber + ". " + moveNotation + "\n");
        } else {
            blackPlayer.addPlayerMove(moveNumber + ". " + moveNotation);
            blackMovesArea.append(moveNumber + ". " + moveNotation + "\n");
            moveNumber++;
        }

        //Update scores as well
        whiteScore.setText(whitePlayer.getPlayerPoints().toString());
        blackScore.setText(blackPlayer.getPlayerPoints().toString());
    }

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
            button.setBackground(PRIMARY_COLOR);
        } else {
            button.setBackground(ALTERNATIVE_COLOR);
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
     * Creates an action listener for each clicked button in the GUI and calls handleTileClick for each one.
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

}
