package com.evan.p2pChess;

import java.awt.Point;

import com.evan.p2pChess.Pieces.*;

/**
 * Creates the controller for the main chess board and places each specific piece on it's designated spot on the board.
 * Contains many helpful methods to reset, print, place, and remove pieces and the board.
 * 
 * @author Evan Delanty
 * @version v1.0.0
 * @since 3/19/2025
 */
public class Board {
    //General values
    public static final int BOARD_SIZE = 8;
    public static final int WHITE_PAWN_ROW = 6;
    public static final int BLACK_PAWN_ROW = 1;
    public static final int WHITE_BACK_ROW = 7;
    public static final int BLACK_BACK_ROW = 0;
    //Row values
    public static final int ROW_8 = 0;
    public static final int ROW_7 = 1;
    public static final int ROW_6 = 2;
    public static final int ROW_5 = 3;
    public static final int ROW_4 = 4;
    public static final int ROW_3 = 5;
    public static final int ROW_2 = 6;
    public static final int ROW_1 = 7;
    //Col values
    public static final int COL_A = 0;
    public static final int COL_B = 1;
    public static final int COL_C = 2;
    public static final int COL_D = 3;
    public static final int COL_E = 4;
    public static final int COL_F = 5;
    public static final int COL_G = 6;
    public static final int COL_H = 7;

    private Tile[][] board;
    private Point enPassantSquare;
    private Player whitePlayer;
    private Player blackPlayer;

    public Board(Player whitePlayer, Player blackPlayer) {
        this.board = new Tile[BOARD_SIZE][BOARD_SIZE];
        this.enPassantSquare = new Point(-1, -1);
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;

        //Initialize all the tiles in the board
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new Tile();
            }
        }
    }

    //Getters
    public Piece getPieceAt(int x, int y) {
        return board[x][y].getPiece();
    }

    public Point getEnPassantSquare() {
        return enPassantSquare;
    }

    //Setters
    public void setPieceAt(int x, int y, Piece piece) {
        board[x][y].setPiece(piece);
    }

    public void setEnPassantSquare(Point point) {
        enPassantSquare = point;
    }

    /**
     * resetBoard()
     * 
     * Iterates through the size of the board and sets a null piece to represent a blank tile.
     * Then recreates a class board through setBoardPieces().
     * 
     */
    public void resetBoard() {
        //Sets all squares of the board to be empty tiles
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j].setPiece(null);
            }
        }

        setBoardPieces();
    }

    /**
     * setBoardPieces()
     * 
     * Places each piece onto the board based on class chess positioning.
     * 
     */
    public void setBoardPieces() {
        //Inits pawns
        for (int i = 0; i < BOARD_SIZE; i++) {
            board[WHITE_PAWN_ROW][i].setPiece(new Pawn(new Integer[][]{{WHITE_PAWN_ROW, i}}, Color.WHITE, whitePlayer));
            board[BLACK_PAWN_ROW][i].setPiece(new Pawn(new Integer[][]{{BLACK_PAWN_ROW, i}}, Color.BLACK, blackPlayer));
        }

        //Inits rooks
        board[WHITE_BACK_ROW][0].setPiece(new Rook(new Integer[][]{{WHITE_BACK_ROW, COL_A}}, Color.WHITE, whitePlayer)); //Bottom left rook
        board[WHITE_BACK_ROW][7].setPiece(new Rook(new Integer[][]{{WHITE_BACK_ROW, COL_H}}, Color.WHITE, whitePlayer)); //Bottom right rook
        board[BLACK_BACK_ROW][0].setPiece(new Rook(new Integer[][]{{BLACK_BACK_ROW, COL_A}}, Color.BLACK, blackPlayer)); //Top left rook
        board[BLACK_BACK_ROW][7].setPiece(new Rook(new Integer[][]{{BLACK_BACK_ROW, COL_H}}, Color.BLACK, blackPlayer)); //Top right rook

        //Inits knights
        board[WHITE_BACK_ROW][1].setPiece(new Knight(new Integer[][]{{WHITE_BACK_ROW, COL_B}}, Color.WHITE, whitePlayer)); //Bottom left knight
        board[WHITE_BACK_ROW][6].setPiece(new Knight(new Integer[][]{{WHITE_BACK_ROW, COL_G}}, Color.WHITE, whitePlayer)); //Bottom right knight
        board[BLACK_BACK_ROW][1].setPiece(new Knight(new Integer[][]{{BLACK_BACK_ROW, COL_B}}, Color.BLACK, blackPlayer)); //Top left knight
        board[BLACK_BACK_ROW][6].setPiece(new Knight(new Integer[][]{{BLACK_BACK_ROW, COL_G}}, Color.BLACK, blackPlayer)); //Top right knight

        //Inits bishops
        board[WHITE_BACK_ROW][2].setPiece(new Bishop(new Integer[][]{{WHITE_BACK_ROW, COL_C}}, Color.WHITE, whitePlayer)); //Bottom left bishop
        board[WHITE_BACK_ROW][5].setPiece(new Bishop(new Integer[][]{{WHITE_BACK_ROW, COL_F}}, Color.WHITE, whitePlayer)); //Bottom right bishop
        board[BLACK_BACK_ROW][2].setPiece(new Bishop(new Integer[][]{{BLACK_BACK_ROW, COL_C}}, Color.BLACK, blackPlayer)); //Top left bishop
        board[BLACK_BACK_ROW][5].setPiece(new Bishop(new Integer[][]{{BLACK_BACK_ROW, COL_F}}, Color.BLACK, blackPlayer)); //Top right bishop

        //Inits queens
        board[WHITE_BACK_ROW][3].setPiece(new Queen(new Integer[][]{{WHITE_BACK_ROW, COL_D}}, Color.WHITE, whitePlayer)); //White queen
        board[BLACK_BACK_ROW][3].setPiece(new Queen(new Integer[][]{{BLACK_BACK_ROW, COL_D}}, Color.BLACK, blackPlayer)); //Black queen

        //Inits kings
        board[WHITE_BACK_ROW][4].setPiece(new King(new Integer[][]{{WHITE_BACK_ROW, COL_E}}, Color.WHITE, whitePlayer)); //White king
        board[BLACK_BACK_ROW][4].setPiece(new King(new Integer[][]{{BLACK_BACK_ROW, COL_E}}, Color.BLACK, blackPlayer)); //Black king
    }

    public void setDebugPieces() {
        King g1King = new King(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_G}}, Color.WHITE, whitePlayer);
        Pawn f2Pawn = new Pawn(new Integer[][]{{Board.ROW_2, Board.COL_F}}, Color.WHITE, whitePlayer);
        Pawn g2Pawn = new Pawn(new Integer[][]{{Board.ROW_2, Board.COL_G}}, Color.WHITE, whitePlayer);
        Pawn h2Pawn = new Pawn(new Integer[][]{{Board.ROW_2, Board.COL_H}}, Color.WHITE, whitePlayer);
        Rook a1Rook = new Rook(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_A}}, Color.BLACK, blackPlayer);
        
        // Place pieces on the board
        setPieceAt(g1King.getPieceRow(), g1King.getPieceCol(), g1King);
        setPieceAt(f2Pawn.getPieceRow(), f2Pawn.getPieceCol(), f2Pawn);
        setPieceAt(g2Pawn.getPieceRow(), g2Pawn.getPieceCol(), g2Pawn);
        setPieceAt(h2Pawn.getPieceRow(), h2Pawn.getPieceCol(), h2Pawn);
        setPieceAt(a1Rook.getPieceRow(), a1Rook.getPieceCol(), a1Rook);
    }

    /**
     * printBoardToTerminal()
     * 
     * Iterates through the board and prints out each piece and a . for an empty tile.
     * Used for debugging!
     * 
     */
    public void printBoardToTerminal() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j].getPiece() != null) {
                    System.out.print(board[i][j].getPiece().getPieceName().charAt(0) + " ");
                } else {
                    System.out.print(". ");
                }
                
                if (j == BOARD_SIZE - 1) { //Newline at the end of each row
                    System.out.print("\n");
                }
            }
        }
    }

    /**
     * capturePiece()
     * 
     * Adds points to the specified player based on the given piece value.
     * Additionally plays the capture sound.
     * 
     * @param captured Used to determine which player to add points to and the captured piece's value.
     */
    public void capturePiece(Piece captured) {
        if (captured.getPieceColor() == Color.WHITE) { //If the captured piece was white add to blackPlayers score
            blackPlayer.addPoints(captured.getPieceValue());
        } else {
            whitePlayer.addPoints(captured.getPieceValue());
        }

        playPieceCaptureSound();
    }

    /**
     * playPieceMovingSound()
     * 
     * Calls the play method from the SoundManager class to play the move sound.
     * 
     */
    public void playPieceMovingSound() {
        SoundManager.play(getClass().getResource("/com/evan/p2pChess/Gui/Sounds/move.wav"));
    }

    /**
     * playPieceCheckSound()
     * 
     * Calls the play method from the SoundManager class to play the check sound.
     * 
     */
    public void playPieceCheckSound() {
        SoundManager.play(getClass().getResource("/com/evan/p2pChess/Gui/Sounds/check.wav"));
    }

    /**
     * playPieceCheckmateSound()
     * 
     * Calls the play method from the SoundManager class to play the checkmate sound.
     * 
     */
    public void playPieceCheckmateSound() {
        SoundManager.play(getClass().getResource("/com/evan/p2pChess/Gui/Sounds/checkmate.wav"));
    }

    /**
     * playPieceCaptureSound()
     * 
     * Calls the play method from the SoundManager class to play the capture sound.
     * 
     */
    public void playPieceCaptureSound() {
        SoundManager.play(getClass().getResource("/com/evan/p2pChess/Gui/Sounds/capture.wav"));
    }

    /**
     * playGameStartSound()
     * 
     * Calls the play method from the SoundManager class to play the start sound.
     * 
     */
    public void playGameStartSound() {
        SoundManager.play(getClass().getResource("/com/evan/p2pChess/Gui/Sounds/start.wav"));
    }

}
