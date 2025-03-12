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
    private Player whitePlayer;
    private Player blackPlayer;

    public Board(Player whitePlayer, Player blackPlayer) {
        this.board = new Tile[BOARD_SIZE][BOARD_SIZE];
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

    //Handle scoring
    public void capturePiece(Piece captured) {
        if (captured.getPieceColor() == Color.WHITE) { //If the captured piece was white add to blackPlayers score
            blackPlayer.addPoints(captured.getPieceValue());
        } else {
            whitePlayer.addPoints(captured.getPieceValue());
        }
    }

}
