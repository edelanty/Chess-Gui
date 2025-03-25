package com.evan.p2pChess;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.evan.p2pChess.Pieces.Bishop;
import com.evan.p2pChess.Pieces.King;
import com.evan.p2pChess.Pieces.Knight;
import com.evan.p2pChess.Pieces.Pawn;
import com.evan.p2pChess.Pieces.Queen;
import com.evan.p2pChess.Pieces.Rook;

public class CheckmateTest {
    @Test
    public void foolsMateCheckmateTest() {
        //Set up a Fool's Mate checkmate position directly
        Player white = new Player("white", 0, Color.WHITE);
        Player black = new Player("black", 0, Color.BLACK);
        Board board = new Board(white, black);
        //White pieces
        King e1King = new King(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_E}}, Color.WHITE, white);
        Pawn f3Pawn = new Pawn(new Integer[][]{{Board.ROW_3, Board.COL_F}}, Color.WHITE, white);
        Pawn g4Pawn = new Pawn(new Integer[][]{{Board.ROW_4, Board.COL_G}}, Color.WHITE, white);
        Pawn e2Pawn = new Pawn(new Integer[][]{{Board.ROW_2, Board.COL_E}}, Color.WHITE, white);
        Pawn d2Pawn = new Pawn(new Integer[][]{{Board.ROW_2, Board.COL_D}}, Color.WHITE, white);
        Pawn h2Pawn = new Pawn(new Integer[][]{{Board.ROW_2, Board.COL_H}}, Color.WHITE, white);
        //Add nearby white pieces for realism
        Bishop f1Bishop = new Bishop(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_F}}, Color.WHITE, white);
        Knight g1Knight = new Knight(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_G}}, Color.WHITE, white);
        Rook h1Rook = new Rook(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_H}}, Color.WHITE, white);
        Queen d1Queen = new Queen(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_D}}, Color.WHITE, white);
        //Black pieces (Queen already on h4 for checkmate)
        King e8King = new King(new Integer[][]{{Board.BLACK_BACK_ROW, Board.COL_E}}, Color.BLACK, black);
        Queen h4Queen = new Queen(new Integer[][]{{Board.ROW_4, Board.COL_H}}, Color.BLACK, black);
        //Add nearby black pieces for realism
        Bishop f8Bishop = new Bishop(new Integer[][]{{Board.BLACK_BACK_ROW, Board.COL_F}}, Color.BLACK, black);
        Knight g8Knight = new Knight(new Integer[][]{{Board.BLACK_BACK_ROW, Board.COL_G}}, Color.BLACK, black);
        Rook h8Rook = new Rook(new Integer[][]{{Board.BLACK_BACK_ROW, Board.COL_H}}, Color.BLACK, black);
        Pawn e5Pawn = new Pawn(new Integer[][]{{Board.ROW_5, Board.COL_E}}, Color.BLACK, black);
        Pawn f7Pawn = new Pawn(new Integer[][]{{Board.ROW_7, Board.COL_F}}, Color.BLACK, black);
        //Place all pieces directly on the board
        board.setPieceAt(e1King.getPieceRow(), e1King.getPieceCol(), e1King);
        board.setPieceAt(f3Pawn.getPieceRow(), f3Pawn.getPieceCol(), f3Pawn);
        board.setPieceAt(g4Pawn.getPieceRow(), g4Pawn.getPieceCol(), g4Pawn);
        board.setPieceAt(f1Bishop.getPieceRow(), f1Bishop.getPieceCol(), f1Bishop);
        board.setPieceAt(g1Knight.getPieceRow(), g1Knight.getPieceCol(), g1Knight);
        board.setPieceAt(h1Rook.getPieceRow(), h1Rook.getPieceCol(), h1Rook);
        board.setPieceAt(d1Queen.getPieceRow(), d1Queen.getPieceCol(), d1Queen);
        board.setPieceAt(e8King.getPieceRow(), e8King.getPieceCol(), e8King);
        board.setPieceAt(h4Queen.getPieceRow(), h4Queen.getPieceCol(), h4Queen);
        board.setPieceAt(f8Bishop.getPieceRow(), f8Bishop.getPieceCol(), f8Bishop);
        board.setPieceAt(g8Knight.getPieceRow(), g8Knight.getPieceCol(), g8Knight);
        board.setPieceAt(h8Rook.getPieceRow(), h8Rook.getPieceCol(), h8Rook);
        board.setPieceAt(e5Pawn.getPieceRow(), e5Pawn.getPieceCol(), e5Pawn);
        board.setPieceAt(f7Pawn.getPieceRow(), f7Pawn.getPieceCol(), f7Pawn);
        board.setPieceAt(e2Pawn.getPieceRow(), e2Pawn.getPieceCol(), e2Pawn);
        board.setPieceAt(d2Pawn.getPieceRow(), d2Pawn.getPieceCol(), d2Pawn);
        board.setPieceAt(h2Pawn.getPieceRow(), h2Pawn.getPieceCol(), h2Pawn);
        board.printBoardToTerminal();
        //Checkmate detection
        CheckDetector checkDetector = new CheckDetector(board);
        boolean isInCheck = checkDetector.isKingInCheck(Color.WHITE);
        boolean isCheckmate = checkDetector.isCheckmate(Color.WHITE);
        boolean isStalemate = checkDetector.isStalemate(Color.WHITE);
        //Assert conditions
        assertTrue(isInCheck);
        assertTrue(isCheckmate);
        assertFalse(isStalemate);
    }

    @Test
    public void checkButNotCheckmate_KingCanMove() {
        //Setup a position where king is in check but can move away
        Player white = new Player("white", 0, Color.WHITE);
        Player black = new Player("black", 0, Color.BLACK);
        Board board = new Board(white, black);
        //Create minimal pieces needed
        King e1King = new King(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_E}}, Color.WHITE, white);
        King e8King = new King(new Integer[][]{{Board.BLACK_BACK_ROW, Board.COL_E}}, Color.BLACK, black);
        Rook h1Rook = new Rook(new Integer[][]{{Board.ROW_5, Board.COL_E}}, Color.BLACK, black);
        //Place pieces on the board
        board.setPieceAt(e1King.getPieceRow(), e1King.getPieceCol(), e1King);
        board.setPieceAt(e8King.getPieceRow(), e8King.getPieceCol(), e8King);
        board.setPieceAt(h1Rook.getPieceRow(), h1Rook.getPieceCol(), h1Rook);
        //Create check detector
        CheckDetector checkDetector = new CheckDetector(board);
        //Assert king is in check but not checkmate (king can move to d1 or f1)
        assertTrue(checkDetector.isKingInCheck(Color.WHITE));
        assertFalse(checkDetector.isCheckmate(Color.WHITE));
    }

    @Test
    public void checkmateBlockTest() {
        //Setup a position where king is in check but another piece can block
        Player white = new Player("white", 0, Color.WHITE);
        Player black = new Player("black", 0, Color.BLACK);
        Board board = new Board(white, black);
        //Create minimal pieces needed
        King e1King = new King(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_E}}, Color.WHITE, white);
        Rook d1Rook = new Rook(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_D}}, Color.WHITE, white);
        Queen a1Queen = new Queen(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_A}}, Color.WHITE, white);
        Bishop c1Bishop = new Bishop(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_C}}, Color.WHITE, white);
        King e8King = new King(new Integer[][]{{Board.BLACK_BACK_ROW, Board.COL_E}}, Color.BLACK, black);
        Queen e5Queen = new Queen(new Integer[][]{{Board.ROW_5, Board.COL_E}}, Color.BLACK, black);
        //Place pieces on the board
        board.setPieceAt(e1King.getPieceRow(), e1King.getPieceCol(), e1King);
        board.setPieceAt(d1Rook.getPieceRow(), d1Rook.getPieceCol(), d1Rook);
        board.setPieceAt(a1Queen.getPieceRow(), a1Queen.getPieceCol(), a1Queen);
        board.setPieceAt(c1Bishop.getPieceRow(), c1Bishop.getPieceCol(), c1Bishop);
        board.setPieceAt(e8King.getPieceRow(), e8King.getPieceCol(), e8King);
        board.setPieceAt(e5Queen.getPieceRow(), e5Queen.getPieceCol(), e5Queen);
        //Create check detector
        CheckDetector checkDetector = new CheckDetector(board);
        //Assert king is in check but not checkmate (queen can be captured or blocked)
        assertTrue(checkDetector.isKingInCheck(Color.WHITE));
        assertFalse(checkDetector.isCheckmate(Color.WHITE));
    }

    @Test
    public void backRankCheckmateTest() {
        Player white = new Player("white", 0, Color.WHITE);
        Player black = new Player("black", 0, Color.BLACK);
        Board board = new Board(white, black);
        //Create minimal pieces needed
        King g1King = new King(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_G}}, Color.WHITE, white);
        Pawn f2Pawn = new Pawn(new Integer[][]{{Board.ROW_2, Board.COL_F}}, Color.WHITE, white);
        Pawn g2Pawn = new Pawn(new Integer[][]{{Board.ROW_2, Board.COL_G}}, Color.WHITE, white);
        Pawn h2Pawn = new Pawn(new Integer[][]{{Board.ROW_2, Board.COL_H}}, Color.WHITE, white);
        Rook a1Rook = new Rook(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_A}}, Color.BLACK, black);
        //Place pieces on the board
        board.setPieceAt(g1King.getPieceRow(), g1King.getPieceCol(), g1King);
        board.setPieceAt(f2Pawn.getPieceRow(), f2Pawn.getPieceCol(), f2Pawn);
        board.setPieceAt(g2Pawn.getPieceRow(), g2Pawn.getPieceCol(), g2Pawn);
        board.setPieceAt(h2Pawn.getPieceRow(), h2Pawn.getPieceCol(), h2Pawn);
        board.setPieceAt(a1Rook.getPieceRow(), a1Rook.getPieceCol(), a1Rook);
        //Create check detector
        CheckDetector checkDetector = new CheckDetector(board);
        //Assert checkmate
        assertTrue(checkDetector.isKingInCheck(Color.WHITE));
        assertTrue(checkDetector.isCheckmate(Color.WHITE));
    }

    @Test
    public void stalemateTest() {
        Player white = new Player("white", 0, Color.WHITE);
        Player black = new Player("black", 0, Color.BLACK);
        Board board = new Board(white, black);
        King a8King = new King(new Integer[][]{{Board.ROW_8, Board.COL_A}}, Color.BLACK, black);
        Queen c7Queen = new Queen(new Integer[][]{{Board.ROW_7, Board.COL_C}}, Color.WHITE, white);
        board.setPieceAt(a8King.getPieceRow(), a8King.getPieceCol(), a8King);
        board.setPieceAt(c7Queen.getPieceRow(), c7Queen.getPieceCol(), c7Queen);
        CheckDetector checkDetector = new CheckDetector(board);
        //Assert stalemate
        assertFalse(checkDetector.isKingInCheck(Color.BLACK));
        assertTrue(checkDetector.isStalemate(Color.BLACK));
        assertFalse(checkDetector.isCheckmate(Color.BLACK));
    }

    @Test
    public void scholarsMateTest() {
        //Set up a proper Scholar's Mate scenario
        Player white = new Player("white", 0, Color.WHITE);
        Player black = new Player("black", 0, Color.BLACK);
        Board board = new Board(white, black);
        //White pieces
        King e1King = new King(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_E}}, Color.WHITE, white);
        Queen d1Queen = new Queen(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_D}}, Color.WHITE, white);
        Bishop f1Bishop = new Bishop(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_F}}, Color.WHITE, white);
        //Black pieces
        King e8King = new King(new Integer[][]{{Board.BLACK_BACK_ROW, Board.COL_E}}, Color.BLACK, black);
        Pawn e7Pawn = new Pawn(new Integer[][]{{Board.ROW_7, Board.COL_E}}, Color.BLACK, black);
        Pawn f7Pawn = new Pawn(new Integer[][]{{Board.ROW_7, Board.COL_F}}, Color.BLACK, black);
        Pawn g7Pawn = new Pawn(new Integer[][]{{Board.ROW_7, Board.COL_G}}, Color.BLACK, black);
        Pawn d7Pawn = new Pawn(new Integer[][]{{Board.ROW_7, Board.COL_D}}, Color.BLACK, black);
        Bishop f8Bishop = new Bishop(new Integer[][]{{Board.ROW_8, Board.COL_F}}, Color.BLACK, black);
        Queen d8Queen = new Queen(new Integer[][]{{Board.ROW_8, Board.COL_D}}, Color.BLACK, black);
        //Place pieces on the board
        board.setPieceAt(e1King.getPieceRow(), e1King.getPieceCol(), e1King);
        board.setPieceAt(d1Queen.getPieceRow(), d1Queen.getPieceCol(), d1Queen);
        board.setPieceAt(f1Bishop.getPieceRow(), f1Bishop.getPieceCol(), f1Bishop);
        board.setPieceAt(e8King.getPieceRow(), e8King.getPieceCol(), e8King);
        board.setPieceAt(e7Pawn.getPieceRow(), e7Pawn.getPieceCol(), e7Pawn);
        board.setPieceAt(f7Pawn.getPieceRow(), f7Pawn.getPieceCol(), f7Pawn);
        board.setPieceAt(g7Pawn.getPieceRow(), g7Pawn.getPieceCol(), g7Pawn);
        board.setPieceAt(d7Pawn.getPieceRow(), d7Pawn.getPieceCol(), d7Pawn);
        board.setPieceAt(f8Bishop.getPieceRow(), f8Bishop.getPieceCol(), f8Bishop);
        board.setPieceAt(d8Queen.getPieceRow(), d8Queen.getPieceCol(), d8Queen);
        //Final position after Scholar's Mate (instead of moving pieces)
        //Move queen directly to f7 for checkmate position
        board.setPieceAt(d1Queen.getPieceRow(), d1Queen.getPieceCol(), null);
        d1Queen = new Queen(new Integer[][]{{Board.ROW_7, Board.COL_F}}, Color.WHITE, white);
        board.setPieceAt(d1Queen.getPieceRow(), d1Queen.getPieceCol(), d1Queen);
        //Move bishop to c4
        board.setPieceAt(f1Bishop.getPieceRow(), f1Bishop.getPieceCol(), null);
        f1Bishop = new Bishop(new Integer[][]{{Board.ROW_4, Board.COL_C}}, Color.WHITE, white);
        board.setPieceAt(f1Bishop.getPieceRow(), f1Bishop.getPieceCol(), f1Bishop);
        board.printBoardToTerminal();
        //Create check detector
        CheckDetector checkDetector = new CheckDetector(board);
        //Check for checkmate
        assertTrue(checkDetector.isKingInCheck(Color.BLACK));
        assertTrue(checkDetector.isCheckmate(Color.BLACK));
    }

}
