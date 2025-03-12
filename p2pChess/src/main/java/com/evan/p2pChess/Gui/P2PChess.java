package com.evan.p2pChess.Gui;

import com.evan.p2pChess.Board;
import com.evan.p2pChess.Pieces.*;
import com.evan.p2pChess.Player;
import com.evan.p2pChess.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    private Color PRIMARY_COLOR;
    private Color ALTERNATIVE_COLOR;

    private static final int PIECE_SIZE = 50;

    public P2PChess(CardLayout cardLayout, JPanel mainPanel) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.chessBoardPanel = new JPanel(new BorderLayout());
        this.whitePlayer = new Player("white", 0, com.evan.p2pChess.Color.WHITE);
        this.blackPlayer = new Player("black", 0, com.evan.p2pChess.Color.BLACK);
        this.board = new Board(whitePlayer, blackPlayer);
        this.tileButtons = new JButton[Board.BOARD_SIZE][Board.BOARD_SIZE];
        this.selectedRow = -1;
        this.selectedCol = -1;
        this.whiteTurn = true;
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

                gridPanel.add(tileButton);
            }
        }

        return gridPanel;
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
            selectedRow = row;
            selectedCol = col;
            tileButtons[row][col].setBorder(BorderFactory.createLineBorder(java.awt.Color.RED, 3));
        } else if (selectedRow != -1) { //This means we've clicked a piece and are about to try and move it
            Piece selectedPiece = board.getPieceAt(selectedRow, selectedCol);
            if (selectedPiece != null && selectedPiece.isValidMove(row, col, board)) {
                selectedPiece.move(row, col, board);
                refreshBoardDisplay();
                whiteTurn = !whiteTurn;
            } else { //Something went wrong
                //TODO user feedback
            }

            //Reset for next click
            tileButtons[selectedRow][selectedCol].setBorder(null);
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
