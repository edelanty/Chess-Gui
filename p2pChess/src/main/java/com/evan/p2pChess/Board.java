package com.evan.p2pChess;

import com.evan.p2pChess.Pieces.*;

/**
 * Board
 * 
 * Bunch on "tiles"
 * 
 * @author Evan Delanty
 */

public class Board {
    public static final int BOARD_SIZE = 8;
    public static final int WHITE_PAWN_ROW = 6;
    public static final int BLACK_PAWN_ROW = 1;
    public static final int WHITE_BACK_ROW = 7;
    public static final int BLACK_BACK_ROW = 0;

    private Tile[][] board;

    public Board() {
        this.board = new Tile[BOARD_SIZE][BOARD_SIZE];

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

    //Setters
    public void setPieceAt(int x, int y, Piece piece) {
        board[x][y].setPiece(piece);
    }

    //Sets up a standard board
    public void resetBoard() {
        //Sets all squares of the board to be empty tiles
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j].setPiece(null);
            }
        }

        setBoardPieces();
    }

    public void setBoardPieces() {
        //Inits pawns
        for (int i = 0; i < BOARD_SIZE; i++) {
            board[WHITE_PAWN_ROW][i].setPiece(new Pawn(new Integer[][]{{WHITE_PAWN_ROW, i}}, Color.WHITE));
            board[BLACK_PAWN_ROW][i].setPiece(new Pawn(new Integer[][]{{BLACK_BACK_ROW, i}}, Color.BLACK));
        }

        //Inits rooks
        board[WHITE_BACK_ROW][0].setPiece(new Rook(new Integer[][]{{WHITE_BACK_ROW, 0}}, Color.WHITE)); //Bottom left rook
        board[WHITE_BACK_ROW][7].setPiece(new Rook(new Integer[][]{{WHITE_BACK_ROW, 7}}, Color.WHITE)); //Bottom right rook
        board[BLACK_BACK_ROW][0].setPiece(new Rook(new Integer[][]{{BLACK_BACK_ROW, 0}}, Color.BLACK)); //Top left rook
        board[BLACK_BACK_ROW][7].setPiece(new Rook(new Integer[][]{{BLACK_BACK_ROW, 7}}, Color.BLACK)); //Top right rook

        //Inits knights
        board[WHITE_BACK_ROW][1].setPiece(new Knight(new Integer[][]{{WHITE_BACK_ROW, 1}}, Color.WHITE)); //Bottom left knight
        board[WHITE_BACK_ROW][6].setPiece(new Knight(new Integer[][]{{WHITE_BACK_ROW, 6}}, Color.WHITE)); //Bottom right knight
        board[BLACK_BACK_ROW][1].setPiece(new Knight(new Integer[][]{{BLACK_BACK_ROW, 1}}, Color.BLACK)); //Top left knight
        board[BLACK_BACK_ROW][6].setPiece(new Knight(new Integer[][]{{BLACK_BACK_ROW, 6}}, Color.BLACK)); //Top right knight

        //Inits bishops
        board[WHITE_BACK_ROW][2].setPiece(new Bishop(new Integer[][]{{WHITE_BACK_ROW, 2}}, Color.WHITE)); //Bottom left bishop
        board[WHITE_BACK_ROW][5].setPiece(new Bishop(new Integer[][]{{WHITE_BACK_ROW, 5}}, Color.WHITE)); //Bottom right bishop
        board[BLACK_BACK_ROW][2].setPiece(new Bishop(new Integer[][]{{BLACK_BACK_ROW, 2}}, Color.BLACK)); //Top left bishop
        board[BLACK_BACK_ROW][5].setPiece(new Bishop(new Integer[][]{{BLACK_BACK_ROW, 5}}, Color.BLACK)); //Top right bishop

        //Inits queens
        board[WHITE_BACK_ROW][3].setPiece(new Queen(new Integer[][]{{WHITE_BACK_ROW, 3}}, Color.WHITE)); //White queen
        board[BLACK_BACK_ROW][3].setPiece(new Queen(new Integer[][]{{BLACK_BACK_ROW, 3}}, Color.BLACK)); //Black queen

        //Inits kings
        board[WHITE_BACK_ROW][4].setPiece(new King(new Integer[][]{{WHITE_BACK_ROW, 4}}, Color.WHITE)); //White king
        board[BLACK_BACK_ROW][4].setPiece(new King(new Integer[][]{{BLACK_BACK_ROW, 4}}, Color.BLACK)); //Black king
    }

    //Prints ze board
    public void printBoardToTerminal() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j].getPiece() != null) {
                    System.out.print(board[i][j].getPiece().getPieceName().charAt(0) + " ");
                } else {
                    System.out.print(". ");
                }
                
                //Newline at the end of each row
                if (j == BOARD_SIZE - 1) {
                    System.out.print("\n");
                }
            }
        }
    }

}
