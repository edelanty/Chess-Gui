package com.evan.p2pChess;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.evan.p2pChess.Pieces.Bishop;
import com.evan.p2pChess.Pieces.King;
import com.evan.p2pChess.Pieces.Knight;
import com.evan.p2pChess.Pieces.Queen;
import com.evan.p2pChess.Pieces.Rook;

public class CastleTest {
    @Test
    public void whiteKingSideCastlingTest() {
        Player white = new Player("white", 0, Color.WHITE);
        King e1King = new King(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_E}}, Color.WHITE, white);
        Rook h1Rook = new Rook(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_H}}, Color.WHITE, white);
        Board board = new Board(white, new Player("black", 0, Color.BLACK));
        //Putting pieces on the board
        board.setPieceAt(e1King.getPieceRow(), e1King.getPieceCol(), e1King);
        board.setPieceAt(h1Rook.getPieceRow(), h1Rook.getPieceCol(), h1Rook);
        e1King.move(Board.ROW_1, Board.COL_G, board);
        assertEquals(e1King, board.getPieceAt(Board.WHITE_BACK_ROW, Board.COL_G));
        assertEquals(h1Rook, board.getPieceAt(Board.WHITE_BACK_ROW, Board.COL_F));
        assertEquals(null, board.getPieceAt(Board.WHITE_BACK_ROW, Board.COL_E));
        assertEquals(null, board.getPieceAt(Board.WHITE_BACK_ROW, Board.COL_H));
        assertEquals(Board.WHITE_BACK_ROW, e1King.getPieceRow());
        assertEquals(Board.COL_G, e1King.getPieceCol());
        assertEquals(Board.WHITE_BACK_ROW, h1Rook.getPieceRow());
        assertEquals(Board.COL_F, h1Rook.getPieceCol());
    }

    @Test
    public void whiteKingSideCastlingWithClearedPathTest() {
        Player white = new Player("white", 0, Color.WHITE);
        King e1King = new King(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_E}}, Color.WHITE, white);
        Rook h1Rook = new Rook(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_H}}, Color.WHITE, white);
        Knight g1Knight = new Knight(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_G}}, Color.WHITE, white);
        Board board = new Board(white, new Player("black", 0, Color.BLACK));
        board.setPieceAt(e1King.getPieceRow(), e1King.getPieceCol(), e1King);
        board.setPieceAt(h1Rook.getPieceRow(), h1Rook.getPieceCol(), h1Rook);
        board.setPieceAt(g1Knight.getPieceRow(), g1Knight.getPieceCol(), g1Knight);
        g1Knight.move(Board.ROW_3, Board.COL_F, board);
        e1King.move(Board.ROW_1, Board.COL_G, board);
        assertEquals(e1King, board.getPieceAt(Board.WHITE_BACK_ROW, Board.COL_G));
        assertEquals(h1Rook, board.getPieceAt(Board.WHITE_BACK_ROW, Board.COL_F));
        assertEquals(null, board.getPieceAt(Board.WHITE_BACK_ROW, Board.COL_E));
        assertEquals(null, board.getPieceAt(Board.WHITE_BACK_ROW, Board.COL_H));
    }

    @Test
    public void whiteQueenSideCastlingTest() {
        Player white = new Player("white", 0, Color.WHITE);
        King e1King = new King(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_E}}, Color.WHITE, white);
        Rook a1Rook = new Rook(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_A}}, Color.WHITE, white);
        Board board = new Board(white, new Player("black", 0, Color.BLACK));
        //Putting pieces on the board
        board.setPieceAt(e1King.getPieceRow(), e1King.getPieceCol(), e1King);
        board.setPieceAt(a1Rook.getPieceRow(), a1Rook.getPieceCol(), a1Rook);
        e1King.move(Board.ROW_1, Board.COL_C, board);
        assertEquals(e1King, board.getPieceAt(Board.WHITE_BACK_ROW, Board.COL_C));
        assertEquals(a1Rook, board.getPieceAt(Board.WHITE_BACK_ROW, Board.COL_D));
        assertEquals(null, board.getPieceAt(Board.WHITE_BACK_ROW, Board.COL_E));
        assertEquals(null, board.getPieceAt(Board.WHITE_BACK_ROW, Board.COL_A));
        assertEquals(Board.WHITE_BACK_ROW, e1King.getPieceRow());
        assertEquals(Board.COL_C, e1King.getPieceCol());
        assertEquals(Board.WHITE_BACK_ROW, a1Rook.getPieceRow());
        assertEquals(Board.COL_D, a1Rook.getPieceCol());
    }

    @Test
    public void whiteQueenSideCastlingWithClearedPathTest() {
        Player white = new Player("white", 0, Color.WHITE);
        King e1King = new King(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_E}}, Color.WHITE, white);
        Rook a1Rook = new Rook(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_A}}, Color.WHITE, white);
        Bishop c1Bishop = new Bishop(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_C}}, Color.WHITE, white);
        Queen d1Queen = new Queen(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_D}}, Color.WHITE, white);
        Board board = new Board(white, new Player("black", 0, Color.BLACK));
        //Putting pieces on the board
        board.setPieceAt(e1King.getPieceRow(), e1King.getPieceCol(), e1King);
        board.setPieceAt(a1Rook.getPieceRow(), a1Rook.getPieceCol(), a1Rook);
        board.setPieceAt(c1Bishop.getPieceRow(), c1Bishop.getPieceCol(), c1Bishop);
        board.setPieceAt(d1Queen.getPieceRow(), d1Queen.getPieceCol(), d1Queen);
        //Move bishop and queen out of the way to clear path
        c1Bishop.move(Board.ROW_3, Board.COL_A, board);
        d1Queen.move(Board.ROW_3, Board.COL_D, board);
        //Now castling should work
        e1King.move(Board.ROW_1, Board.COL_C, board);
        assertEquals(e1King, board.getPieceAt(Board.WHITE_BACK_ROW, Board.COL_C));
        assertEquals(e1King, board.getPieceAt(Board.WHITE_BACK_ROW, Board.COL_C));
        assertEquals(a1Rook, board.getPieceAt(Board.WHITE_BACK_ROW, Board.COL_D));
        assertEquals(null, board.getPieceAt(Board.WHITE_BACK_ROW, Board.COL_E));
        assertEquals(null, board.getPieceAt(Board.WHITE_BACK_ROW, Board.COL_A));
    }

    @Test
    public void whiteKingSideCastlingFailedPathBlockedTest() {
        Player white = new Player("white", 0, Color.WHITE);
        King e1King = new King(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_E}}, Color.WHITE, white);
        Rook h1Rook = new Rook(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_H}}, Color.WHITE, white);
        Knight f1Knight = new Knight(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_F}}, Color.WHITE, white);
        Board board = new Board(white, new Player("black", 0, Color.BLACK));
        Integer expectedKingRow = Board.WHITE_BACK_ROW, expectedKingCol = Board.COL_E;
        //Putting pieces on the board
        board.setPieceAt(e1King.getPieceRow(), e1King.getPieceCol(), e1King);
        board.setPieceAt(h1Rook.getPieceRow(), h1Rook.getPieceCol(), h1Rook);
        board.setPieceAt(f1Knight.getPieceRow(), f1Knight.getPieceCol(), f1Knight);
        //Castling should fail because path is blocked by knight
        e1King.move(Board.ROW_1, Board.COL_G, board);
        assertEquals(e1King, board.getPieceAt(expectedKingRow, expectedKingCol));
        assertEquals(h1Rook, board.getPieceAt(Board.WHITE_BACK_ROW, Board.COL_H));
        assertEquals(f1Knight, board.getPieceAt(Board.WHITE_BACK_ROW, Board.COL_F));
        assertEquals(expectedKingRow, e1King.getPieceRow());
        assertEquals(expectedKingCol, e1King.getPieceCol());
    }

    @Test
    public void whiteKingSideCastlingFailedKingMovedTest() {
        Player white = new Player("white", 0, Color.WHITE);
        King e1King = new King(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_E}}, Color.WHITE, white);
        Rook h1Rook = new Rook(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_H}}, Color.WHITE, white);
        Board board = new Board(white, new Player("black", 0, Color.BLACK));
        //Putting pieces on the board
        board.setPieceAt(e1King.getPieceRow(), e1King.getPieceCol(), e1King);
        board.setPieceAt(h1Rook.getPieceRow(), h1Rook.getPieceCol(), h1Rook);
        //Move king and move it back to simulate that king has moved
        e1King.move(Board.ROW_2, Board.COL_E, board);
        e1King.move(Board.WHITE_BACK_ROW, Board.COL_E, board);
        //Castling should fail because king has moved
        e1King.move(Board.ROW_1, Board.COL_G, board);
        assertEquals(e1King, board.getPieceAt(Board.WHITE_BACK_ROW, Board.COL_E));
        assertEquals(h1Rook, board.getPieceAt(Board.WHITE_BACK_ROW, Board.COL_H));
    }

    @Test
    public void blackKingSideCastlingTest() {
        Player black = new Player("black", 0, Color.BLACK);
        King e8King = new King(new Integer[][]{{Board.BLACK_BACK_ROW, Board.COL_E}}, Color.BLACK, black);
        Rook h8Rook = new Rook(new Integer[][]{{Board.BLACK_BACK_ROW, Board.COL_H}}, Color.BLACK, black);
        Board board = new Board(new Player("white", 0, Color.WHITE), black);
        //Putting pieces on the board
        board.setPieceAt(e8King.getPieceRow(), e8King.getPieceCol(), e8King);
        board.setPieceAt(h8Rook.getPieceRow(), h8Rook.getPieceCol(), h8Rook);
        //King should move two squares to the right and rook should move to f8
        e8King.move(Board.ROW_8, Board.COL_G, board);
        assertEquals(e8King, board.getPieceAt(Board.BLACK_BACK_ROW, Board.COL_G));
        assertEquals(h8Rook, board.getPieceAt(Board.BLACK_BACK_ROW, Board.COL_F));
        assertEquals(null, board.getPieceAt(Board.BLACK_BACK_ROW, Board.COL_E));
        assertEquals(null, board.getPieceAt(Board.BLACK_BACK_ROW, Board.COL_H));
        assertEquals(Board.BLACK_BACK_ROW, e8King.getPieceRow());
        assertEquals(Board.COL_G, e8King.getPieceCol());
        assertEquals(Board.BLACK_BACK_ROW, h8Rook.getPieceRow());
        assertEquals(Board.COL_F, h8Rook.getPieceCol());
    }

    @Test
    public void blackKingSideCastlingWithClearedPathTest() {
        Player black = new Player("black", 0, Color.BLACK);
        King e8King = new King(new Integer[][]{{Board.BLACK_BACK_ROW, Board.COL_E}}, Color.BLACK, black);
        Rook h8Rook = new Rook(new Integer[][]{{Board.BLACK_BACK_ROW, Board.COL_H}}, Color.BLACK, black);
        Knight g8Knight = new Knight(new Integer[][]{{Board.BLACK_BACK_ROW, Board.COL_G}}, Color.BLACK, black);
        Board board = new Board(new Player("white", 0, Color.WHITE), black);
        //Putting pieces on the board
        board.setPieceAt(e8King.getPieceRow(), e8King.getPieceCol(), e8King);
        board.setPieceAt(h8Rook.getPieceRow(), h8Rook.getPieceCol(), h8Rook);
        board.setPieceAt(g8Knight.getPieceRow(), g8Knight.getPieceCol(), g8Knight);
        //Move knight out of the way to clear path
        g8Knight.move(Board.ROW_6, Board.COL_F, board);
        //Now castling should work
        e8King.move(Board.ROW_8, Board.COL_G, board);
        assertEquals(e8King, board.getPieceAt(Board.BLACK_BACK_ROW, Board.COL_G));
        assertEquals(h8Rook, board.getPieceAt(Board.BLACK_BACK_ROW, Board.COL_F));
        assertEquals(null, board.getPieceAt(Board.BLACK_BACK_ROW, Board.COL_E));
        assertEquals(null, board.getPieceAt(Board.BLACK_BACK_ROW, Board.COL_H));
    }

    @Test
    public void blackQueenSideCastlingTest() {
        Player black = new Player("black", 0, Color.BLACK);
        King e8King = new King(new Integer[][]{{Board.BLACK_BACK_ROW, Board.COL_E}}, Color.BLACK, black);
        Rook a8Rook = new Rook(new Integer[][]{{Board.BLACK_BACK_ROW, Board.COL_A}}, Color.BLACK, black);
        Board board = new Board(new Player("white", 0, Color.WHITE), black);
        //Putting pieces on the board
        board.setPieceAt(e8King.getPieceRow(), e8King.getPieceCol(), e8King);
        board.setPieceAt(a8Rook.getPieceRow(), a8Rook.getPieceCol(), a8Rook);
        //King should move two squares to the left and rook should move to d8
        e8King.move(Board.ROW_8, Board.COL_C, board);
        assertEquals(e8King, board.getPieceAt(Board.BLACK_BACK_ROW, Board.COL_C));
        assertEquals(a8Rook, board.getPieceAt(Board.BLACK_BACK_ROW, Board.COL_D));
        assertEquals(null, board.getPieceAt(Board.BLACK_BACK_ROW, Board.COL_E));
        assertEquals(null, board.getPieceAt(Board.BLACK_BACK_ROW, Board.COL_A));
        assertEquals(Board.BLACK_BACK_ROW, e8King.getPieceRow());
        assertEquals(Board.COL_C, e8King.getPieceCol());
        assertEquals(Board.BLACK_BACK_ROW, a8Rook.getPieceRow());
        assertEquals(Board.COL_D, a8Rook.getPieceCol());
    }

    @Test
    public void blackQueenSideCastlingWithClearedPathTest() {
        Player black = new Player("black", 0, Color.BLACK);
        King e8King = new King(new Integer[][]{{Board.BLACK_BACK_ROW, Board.COL_E}}, Color.BLACK, black);
        Rook a8Rook = new Rook(new Integer[][]{{Board.BLACK_BACK_ROW, Board.COL_A}}, Color.BLACK, black);
        Bishop c8Bishop = new Bishop(new Integer[][]{{Board.BLACK_BACK_ROW, Board.COL_C}}, Color.BLACK, black);
        Queen d8Queen = new Queen(new Integer[][]{{Board.BLACK_BACK_ROW, Board.COL_D}}, Color.BLACK, black);
        Board board = new Board(new Player("white", 0, Color.WHITE), black);
        //Putting pieces on the board
        board.setPieceAt(e8King.getPieceRow(), e8King.getPieceCol(), e8King);
        board.setPieceAt(a8Rook.getPieceRow(), a8Rook.getPieceCol(), a8Rook);
        board.setPieceAt(c8Bishop.getPieceRow(), c8Bishop.getPieceCol(), c8Bishop);
        board.setPieceAt(d8Queen.getPieceRow(), d8Queen.getPieceCol(), d8Queen);
        //Move bishop and queen out of the way to clear path
        c8Bishop.move(Board.ROW_6, Board.COL_A, board);
        d8Queen.move(Board.ROW_6, Board.COL_D, board);
        //Now castling should work
        e8King.move(Board.ROW_8, Board.COL_C, board);
        assertEquals(e8King, board.getPieceAt(Board.BLACK_BACK_ROW, Board.COL_C));
        assertEquals(a8Rook, board.getPieceAt(Board.BLACK_BACK_ROW, Board.COL_D));
        assertEquals(null, board.getPieceAt(Board.BLACK_BACK_ROW, Board.COL_E));
        assertEquals(null, board.getPieceAt(Board.BLACK_BACK_ROW, Board.COL_A));
    }

    @Test
    public void blackQueenSideCastlingFailedPathBlockedTest() {
        Player black = new Player("black", 0, Color.BLACK);
        King e8King = new King(new Integer[][]{{Board.BLACK_BACK_ROW, Board.COL_E}}, Color.BLACK, black);
        Rook a8Rook = new Rook(new Integer[][]{{Board.BLACK_BACK_ROW, Board.COL_A}}, Color.BLACK, black);
        Knight b8Knight = new Knight(new Integer[][]{{Board.BLACK_BACK_ROW, Board.COL_B}}, Color.BLACK, black);
        Board board = new Board(new Player("white", 0, Color.WHITE), black);
        Integer expectedKingRow = Board.BLACK_BACK_ROW, expectedKingCol = Board.COL_E;
        //Putting pieces on the board
        board.setPieceAt(e8King.getPieceRow(), e8King.getPieceCol(), e8King);
        board.setPieceAt(a8Rook.getPieceRow(), a8Rook.getPieceCol(), a8Rook);
        board.setPieceAt(b8Knight.getPieceRow(), b8Knight.getPieceCol(), b8Knight);
        //Castling should fail because path is blocked by knight
        board.printBoardToTerminal();
        e8King.move(Board.ROW_8, Board.COL_C, board);
        board.printBoardToTerminal();
        assertEquals(e8King, board.getPieceAt(expectedKingRow, expectedKingCol));
        assertEquals(a8Rook, board.getPieceAt(Board.BLACK_BACK_ROW, Board.COL_A));
        assertEquals(b8Knight, board.getPieceAt(Board.BLACK_BACK_ROW, Board.COL_B));
        assertEquals(expectedKingRow, e8King.getPieceRow());
        assertEquals(expectedKingCol, e8King.getPieceCol());
    }

    @Test
    public void blackQueenSideCastlingFailedRookMovedTest() {
        Player black = new Player("black", 0, Color.BLACK);
        King e8King = new King(new Integer[][]{{Board.BLACK_BACK_ROW, Board.COL_E}}, Color.BLACK, black);
        Rook a8Rook = new Rook(new Integer[][]{{Board.BLACK_BACK_ROW, Board.COL_A}}, Color.BLACK, black);
        Board board = new Board(new Player("white", 0, Color.WHITE), black);
        //Putting pieces on the board
        board.setPieceAt(e8King.getPieceRow(), e8King.getPieceCol(), e8King);
        board.setPieceAt(a8Rook.getPieceRow(), a8Rook.getPieceCol(), a8Rook);
        //Move rook and move it back to simulate that rook has moved
        a8Rook.move(Board.ROW_7, Board.COL_A, board);
        a8Rook.move(Board.BLACK_BACK_ROW, Board.COL_A, board);
        //Castling should fail because rook has moved
        e8King.move(Board.ROW_8, Board.COL_C, board);
        assertEquals(e8King, board.getPieceAt(Board.BLACK_BACK_ROW, Board.COL_E));
        assertEquals(a8Rook, board.getPieceAt(Board.BLACK_BACK_ROW, Board.COL_A));
    }

    @Test
    public void blackKingSideCastlingFailedCheckTest() {
        Player black = new Player("black", 0, Color.BLACK);
        Player white = new Player("white", 0, Color.WHITE);
        King e8King = new King(new Integer[][]{{Board.BLACK_BACK_ROW, Board.COL_E}}, Color.BLACK, black);
        Rook h8Rook = new Rook(new Integer[][]{{Board.BLACK_BACK_ROW, Board.COL_H}}, Color.BLACK, black);
        //White rook is checking the black king
        Rook e4Rook = new Rook(new Integer[][]{{Board.ROW_4, Board.COL_E}}, Color.WHITE, white);
        Board board = new Board(white, black);
        //Putting pieces on the board
        board.setPieceAt(e8King.getPieceRow(), e8King.getPieceCol(), e8King);
        board.setPieceAt(h8Rook.getPieceRow(), h8Rook.getPieceCol(), h8Rook);
        board.setPieceAt(e4Rook.getPieceRow(), e4Rook.getPieceCol(), e4Rook);
        //Castling should fail because king is in check
        e8King.move(Board.ROW_8, Board.COL_G, board);
        assertEquals(e8King, board.getPieceAt(Board.BLACK_BACK_ROW, Board.COL_E));
        assertEquals(h8Rook, board.getPieceAt(Board.BLACK_BACK_ROW, Board.COL_H));
    }

    @Test
    public void blackKingSideCastlingFailedCastleThroughCheckTest() {
        Player black = new Player("black", 0, Color.BLACK);
        Player white = new Player("white", 0, Color.WHITE);
        King e8King = new King(new Integer[][]{{Board.BLACK_BACK_ROW, Board.COL_E}}, Color.BLACK, black);
        Rook h8Rook = new Rook(new Integer[][]{{Board.BLACK_BACK_ROW, Board.COL_H}}, Color.BLACK, black);
        //White rook is attacking f8 square - can't castle through check
        Rook f4Rook = new Rook(new Integer[][]{{Board.ROW_4, Board.COL_F}}, Color.WHITE, white);
        Board board = new Board(white, black);
        //Putting pieces on the board
        board.setPieceAt(e8King.getPieceRow(), e8King.getPieceCol(), e8King);
        board.setPieceAt(h8Rook.getPieceRow(), h8Rook.getPieceCol(), h8Rook);
        board.setPieceAt(f4Rook.getPieceRow(), f4Rook.getPieceCol(), f4Rook);
        //Castling should fail because king would pass through a square that is under attack
        e8King.move(Board.ROW_8, Board.COL_C, board);
        assertEquals(e8King, board.getPieceAt(Board.BLACK_BACK_ROW, Board.COL_E));
        assertEquals(h8Rook, board.getPieceAt(Board.BLACK_BACK_ROW, Board.COL_H));
    }

}
