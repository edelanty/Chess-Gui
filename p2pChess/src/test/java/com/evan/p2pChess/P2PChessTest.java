package com.evan.p2pChess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.evan.p2pChess.Pieces.*;

/**
 * Unit test for simple App.
 */
public class P2PChessTest {
    
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    //Player value tests

    @Test
    public void capturedPieceQueueUpdateTest() {
        //TODO
    }

    //Pawn movement tests

    @Test
    public void whitePawnE2toE4MoveTest() {
        Player white = new Player("white", 0, Color.WHITE);
        Pawn e2Pawn = new Pawn(new Integer[][]{{Board.WHITE_PAWN_ROW, Board.COL_E}}, Color.WHITE, white);
        Board board = new Board(white, new Player("black", 0, Color.BLACK));
        Integer expectedRow = Board.ROW_4, expectedCol = Board.COL_E;
        //Setting up the pawn on the board and moving it
        board.setPieceAt(e2Pawn.getPieceRow(), e2Pawn.getPieceCol(), e2Pawn);
        e2Pawn.move(Board.ROW_4, Board.COL_E, board);
        assertEquals(e2Pawn, board.getPieceAt(expectedRow, expectedCol)); //Found e2 pawn at the spot it moved
        assertEquals(null, board.getPieceAt(Board.WHITE_PAWN_ROW, Board.COL_E)); //Check for null where the piece used to be
        assertEquals(expectedRow, board.getPieceAt(expectedRow, expectedCol).getPieceRow()); //Found the expected row
        assertEquals(expectedCol, board.getPieceAt(expectedRow, expectedCol).getPieceCol()); //Found the expected col
    }

    @Test
    public void whitePawnCapturesBlackPawnTest() {
        Player white = new Player("white", 0, Color.WHITE);
        Player black = new Player("black", 0, Color.BLACK);
        Pawn e2Pawn = new Pawn(new Integer[][]{{Board.WHITE_PAWN_ROW, Board.COL_E}}, Color.WHITE, white);
        Pawn f3Pawn = new Pawn(new Integer[][]{{Board.ROW_3, Board.COL_F}}, Color.BLACK, black);
        Board board = new Board(white, black);
        Integer expectedRow = Board.ROW_3, expectedCol = Board.COL_F, expectedPoints = 1;
        //Setting up the pawn on the board and moving it
        board.setPieceAt(e2Pawn.getPieceRow(), e2Pawn.getPieceCol(), e2Pawn);
        board.setPieceAt(f3Pawn.getPieceRow(), f3Pawn.getPieceCol(), f3Pawn);
        e2Pawn.move(Board.ROW_3, Board.COL_F, board);
        assertEquals(expectedPoints, white.getPlayerPoints()); //Player points should be 1 after capturing a pawn
        assertEquals(null, board.getPieceAt(Board.WHITE_PAWN_ROW, Board.COL_E)); //Check for null where the piece used to be
        assertEquals(expectedRow, board.getPieceAt(expectedRow, expectedCol).getPieceRow()); //Found the expected row
        assertEquals(expectedCol, board.getPieceAt(expectedRow, expectedCol).getPieceCol()); //Found the expected col
    }

    @Test
    public void blackPawnE7toE6MoveTest() {
        Player black = new Player("black", 0, Color.BLACK);
        Pawn e7Pawn = new Pawn(new Integer[][]{{Board.BLACK_BACK_ROW, Board.COL_E}}, Color.BLACK, black);
        Board board = new Board(new Player("white", 0, Color.WHITE), black);
        Integer expectedRow = Board.ROW_6, expectedCol = Board.COL_E;
        //Setting up the pawn on the board and moving it
        board.setPieceAt(e7Pawn.getPieceRow(), e7Pawn.getPieceCol(), e7Pawn);
        e7Pawn.move(Board.ROW_6, Board.COL_E, board);
        assertEquals(e7Pawn, board.getPieceAt(expectedRow, expectedCol)); //Found e7 pawn at the spot it moved
        assertEquals(null, board.getPieceAt(Board.WHITE_PAWN_ROW, Board.COL_E)); //Check for null where the piece used to be
        assertEquals(expectedRow, board.getPieceAt(expectedRow, expectedCol).getPieceRow()); //Found the expected row
        assertEquals(expectedCol, board.getPieceAt(expectedRow, expectedCol).getPieceCol()); //Found the expected col
    }

    @Test
    public void blackPawnCapturesWhiteRookTest() {
        Player white = new Player("white", 0, Color.WHITE);
        Player black = new Player("black", 0, Color.BLACK);
        Pawn e7Pawn = new Pawn(new Integer[][]{{Board.BLACK_PAWN_ROW, Board.COL_E}}, Color.BLACK, black);
        Rook d6Rook = new Rook(new Integer[][]{{Board.ROW_6, Board.COL_D}}, Color.WHITE, white);
        Board board = new Board(white, black);
        Integer expectedRow = Board.ROW_6, expectedCol = Board.COL_D, expectedPoints = 5;
        //Setting up the pawn on the board and moving it
        board.setPieceAt(e7Pawn.getPieceRow(), e7Pawn.getPieceCol(), e7Pawn);
        board.setPieceAt(d6Rook.getPieceRow(), d6Rook.getPieceCol(), d6Rook);
        e7Pawn.move(Board.ROW_6, Board.COL_D, board);
        assertEquals(expectedPoints, black.getPlayerPoints()); //Player points should be 1 after capturing a pawn
        assertEquals(null, board.getPieceAt(Board.BLACK_BACK_ROW, Board.COL_E)); //Check for null where the piece used to be
        assertEquals(expectedRow, board.getPieceAt(expectedRow, expectedCol).getPieceRow()); //Found the expected row
        assertEquals(expectedCol, board.getPieceAt(expectedRow, expectedCol).getPieceCol()); //Found the expected col
    }

    @Test
    public void whitePawnBlockedMoving2SpacesTest() {
        Player white = new Player("white", 0, Color.WHITE);
        Pawn e2Pawn = new Pawn(new Integer[][]{{Board.WHITE_PAWN_ROW, Board.COL_E}}, Color.WHITE, white);
        Board board = new Board(white, new Player("black", 0, Color.BLACK));
        Integer expectedRow = Board.ROW_4, expectedCol = Board.COL_E;
        //Setting up the pawn on the board and moving it
        board.setPieceAt(e2Pawn.getPieceRow(), e2Pawn.getPieceCol(), e2Pawn);
        e2Pawn.move(Board.ROW_4, Board.COL_E, board);
        assertEquals(e2Pawn, board.getPieceAt(expectedRow, expectedCol)); //Found e2 pawn at the spot it moved
        assertEquals(null, board.getPieceAt(Board.WHITE_PAWN_ROW, Board.COL_E)); //Check for null where the piece used to be
        assertEquals(expectedRow, board.getPieceAt(expectedRow, expectedCol).getPieceRow()); //Found the expected row
        assertEquals(expectedCol, board.getPieceAt(expectedRow, expectedCol).getPieceCol()); //Found the expected col
    }

    //TODO en passant test

    // Bishop movement tests
    
    @Test
    public void whiteBishopF1toC4MoveTest() {
        Player white = new Player("white", 0, Color.WHITE);
        Bishop f1Bishop = new Bishop(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_F}}, Color.WHITE, white);
        Board board = new Board(new Player("white", 0, Color.WHITE), new Player("black", 0, Color.BLACK));
        Integer expectedRow = Board.ROW_4, expectedCol = Board.COL_C;
        //Putting bishop on the row
        board.setPieceAt(f1Bishop.getPieceRow(), f1Bishop.getPieceCol(), f1Bishop);
        f1Bishop.move(Board.ROW_4, Board.COL_C, board);
        assertEquals(f1Bishop, board.getPieceAt(expectedRow, expectedCol)); //Found f6 bishop at the spot it moved
        assertEquals(null, board.getPieceAt(Board.WHITE_BACK_ROW, Board.COL_F)); //Check for null where the piece used to be
        assertEquals(expectedRow, board.getPieceAt(expectedRow, expectedCol).getPieceRow()); //Found the expected row
        assertEquals(expectedCol, board.getPieceAt(expectedRow, expectedCol).getPieceCol()); //Found the expected col
    }

    @Test
    public void whiteBishopF1toC4BlockedMoveTest() {
        Player white = new Player("white", 0, Color.WHITE);
        Pawn e2Pawn = new Pawn(new Integer[][]{{Board.WHITE_PAWN_ROW, Board.COL_E}}, Color.WHITE, white);
        Bishop f1Bishop = new Bishop(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_F}}, Color.WHITE, white);
        Board board = new Board(new Player("white", 0, Color.WHITE), new Player("black", 0, Color.BLACK));
        Integer expectedRow = Board.WHITE_BACK_ROW, expectedCol = Board.COL_F;
        //Putting pawn and bishop on the board
        board.setPieceAt(e2Pawn.getPieceRow(), e2Pawn.getPieceCol(), e2Pawn);
        board.setPieceAt(f1Bishop.getPieceRow(), f1Bishop.getPieceCol(), f1Bishop);
        f1Bishop.move(Board.ROW_4, Board.COL_C, board);
        assertEquals(f1Bishop, board.getPieceAt(expectedRow, expectedCol)); //Found f6 bishop in same spot (failed because of block)
        assertEquals(expectedRow, board.getPieceAt(expectedRow, expectedCol).getPieceRow()); //Found the expected row
        assertEquals(expectedCol, board.getPieceAt(expectedRow, expectedCol).getPieceCol()); //Found the expected col
    }

    @Test
    public void blackBishopC8toA6MoveTest() {
        Player black = new Player("black", 0, Color.BLACK);
        Bishop c8Bishop = new Bishop(new Integer[][]{{Board.BLACK_BACK_ROW, Board.COL_C}}, Color.BLACK, black);
        Board board = new Board(new Player("white", 0, Color.WHITE), black);
        Integer expectedRow = Board.ROW_6, expectedCol = Board.COL_A;
        //Putting bishop on the board
        board.setPieceAt(c8Bishop.getPieceRow(), c8Bishop.getPieceCol(), c8Bishop);
        c8Bishop.move(Board.ROW_6, Board.COL_A, board);
        assertEquals(c8Bishop, board.getPieceAt(expectedRow, expectedCol));
        assertEquals(expectedRow, board.getPieceAt(expectedRow, expectedCol).getPieceRow()); //Found the expected row
        assertEquals(expectedCol, board.getPieceAt(expectedRow, expectedCol).getPieceCol()); //Found the expected col
    }

    //Rook movement tests

    @Test
    public void whiteRookA1toA4MoveTest() {
        Player white = new Player("white", 0, Color.WHITE);
        Rook a1Rook = new Rook(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_A}}, Color.WHITE, white);
        Board board = new Board(white, new Player("black", 0, Color.BLACK));
        Integer expectedRow = Board.ROW_4, expectedCol = Board.COL_A;
        //Putting rook on the board
        board.setPieceAt(a1Rook.getPieceRow(), a1Rook.getPieceCol(), a1Rook);
        a1Rook.move(expectedRow, expectedCol, board);
        assertEquals(a1Rook, board.getPieceAt(expectedRow, expectedCol));
        assertEquals(null, board.getPieceAt(Board.WHITE_BACK_ROW, Board.COL_A));
        assertEquals(expectedRow, board.getPieceAt(expectedRow, expectedCol).getPieceRow());
        assertEquals(expectedCol, board.getPieceAt(expectedRow, expectedCol).getPieceCol());
    }

    @Test
    public void whiteRookA1toA4BlockedMoveTest() {
        Player white = new Player("white", 0, Color.WHITE);
        Pawn a2Pawn = new Pawn(new Integer[][]{{Board.WHITE_PAWN_ROW, Board.COL_A}}, Color.WHITE, white);
        Rook a1Rook = new Rook(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_A}}, Color.WHITE, white);
        Board board = new Board(white, new Player("black", 0, Color.BLACK));
        Integer expectedRow = Board.WHITE_BACK_ROW, expectedCol = Board.COL_A;
        //Putting rook and pawn on the board
        board.setPieceAt(a2Pawn.getPieceRow(), a2Pawn.getPieceCol(), a2Pawn);
        board.setPieceAt(a1Rook.getPieceRow(), a1Rook.getPieceCol(), a1Rook);
        a1Rook.move(Board.ROW_4, Board.COL_A, board);
        assertEquals(a1Rook, board.getPieceAt(expectedRow, expectedCol));
        assertEquals(expectedRow, board.getPieceAt(expectedRow, expectedCol).getPieceRow());
        assertEquals(expectedCol, board.getPieceAt(expectedRow, expectedCol).getPieceCol());
    }

    @Test
    public void blackRookH8toH5MoveTest() {
        Player black = new Player("black", 0, Color.BLACK);
        Rook h8Rook = new Rook(new Integer[][]{{Board.BLACK_BACK_ROW, Board.COL_H}}, Color.BLACK, black);
        Board board = new Board(new Player("white", 0, Color.WHITE), black);
        Integer expectedRow = Board.ROW_5, expectedCol = Board.COL_H;
        //Putting rook on the board
        board.setPieceAt(h8Rook.getPieceRow(), h8Rook.getPieceCol(), h8Rook);
        h8Rook.move(expectedRow, expectedCol, board);
        assertEquals(h8Rook, board.getPieceAt(expectedRow, expectedCol));
        assertEquals(null, board.getPieceAt(Board.BLACK_BACK_ROW, Board.COL_H));
        assertEquals(expectedRow, board.getPieceAt(expectedRow, expectedCol).getPieceRow());
        assertEquals(expectedCol, board.getPieceAt(expectedRow, expectedCol).getPieceCol());
    }

    @Test
    public void whiteQueenD1toD4MoveTest() {
        Player white = new Player("white", 0, Color.WHITE);
        Queen d1Queen = new Queen(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_D}}, Color.WHITE, white);
        Board board = new Board(white, new Player("black", 0, Color.BLACK));
        Integer expectedRow = Board.ROW_4, expectedCol = Board.COL_D;
        //Putting queen on the board
        board.setPieceAt(d1Queen.getPieceRow(), d1Queen.getPieceCol(), d1Queen);
        d1Queen.move(expectedRow, expectedCol, board);
        assertEquals(d1Queen, board.getPieceAt(expectedRow, expectedCol));
        assertEquals(null, board.getPieceAt(Board.WHITE_BACK_ROW, Board.COL_D));
        assertEquals(expectedRow, board.getPieceAt(expectedRow, expectedCol).getPieceRow());
        assertEquals(expectedCol, board.getPieceAt(expectedRow, expectedCol).getPieceCol());
    }

    @Test
    public void whiteQueenD1toD4BlockedMoveTest() {
        Player white = new Player("white", 0, Color.WHITE);
        Pawn d2Pawn = new Pawn(new Integer[][]{{Board.WHITE_PAWN_ROW, Board.COL_D}}, Color.WHITE, white);
        Queen d1Queen = new Queen(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_D}}, Color.WHITE, white);
        Board board = new Board(white, new Player("black", 0, Color.BLACK));
        Integer expectedRow = Board.WHITE_BACK_ROW, expectedCol = Board.COL_D;
        //Putting queen and pawn on the board
        board.setPieceAt(d2Pawn.getPieceRow(), d2Pawn.getPieceCol(), d2Pawn);
        board.setPieceAt(d1Queen.getPieceRow(), d1Queen.getPieceCol(), d1Queen);
        d1Queen.move(Board.ROW_4, Board.COL_D, board);
        assertEquals(d1Queen, board.getPieceAt(expectedRow, expectedCol));
        assertEquals(expectedRow, board.getPieceAt(expectedRow, expectedCol).getPieceRow());
        assertEquals(expectedCol, board.getPieceAt(expectedRow, expectedCol).getPieceCol());
    }

    @Test
    public void blackQueenD8toD5MoveTest() {
        Player black = new Player("black", 0, Color.BLACK);
        Queen d8Queen = new Queen(new Integer[][]{{Board.BLACK_BACK_ROW, Board.COL_D}}, Color.BLACK, black);
        Board board = new Board(new Player("white", 0, Color.WHITE), black);
        Integer expectedRow = Board.ROW_5, expectedCol = Board.COL_D;
        //Putting queen on the board
        board.setPieceAt(d8Queen.getPieceRow(), d8Queen.getPieceCol(), d8Queen);
        d8Queen.move(expectedRow, expectedCol, board);
        assertEquals(d8Queen, board.getPieceAt(expectedRow, expectedCol));
        assertEquals(null, board.getPieceAt(Board.BLACK_BACK_ROW, Board.COL_D));
        assertEquals(expectedRow, board.getPieceAt(expectedRow, expectedCol).getPieceRow());
        assertEquals(expectedCol, board.getPieceAt(expectedRow, expectedCol).getPieceCol());
    }

    @Test
    public void whiteQueenD1toG4DiagonalMoveTest() {
        Player white = new Player("white", 0, Color.WHITE);
        Queen d1Queen = new Queen(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_D}}, Color.WHITE, white);
        Board board = new Board(white, new Player("black", 0, Color.BLACK));
        Integer expectedRow = Board.ROW_4, expectedCol = Board.COL_G;
        //Putting queen on the board
        board.setPieceAt(d1Queen.getPieceRow(), d1Queen.getPieceCol(), d1Queen);
        d1Queen.move(expectedRow, expectedCol, board);
        assertEquals(d1Queen, board.getPieceAt(expectedRow, expectedCol));
        assertEquals(null, board.getPieceAt(Board.WHITE_BACK_ROW, Board.COL_D));
        assertEquals(expectedRow, board.getPieceAt(expectedRow, expectedCol).getPieceRow());
        assertEquals(expectedCol, board.getPieceAt(expectedRow, expectedCol).getPieceCol());
    }

    @Test
    public void blackQueenD8toA5DiagonalMoveTest() {
        Player black = new Player("black", 0, Color.BLACK);
        Queen d8Queen = new Queen(new Integer[][]{{Board.BLACK_BACK_ROW, Board.COL_D}}, Color.BLACK, black);
        Board board = new Board(new Player("white", 0, Color.WHITE), black);
        Integer expectedRow = Board.ROW_5, expectedCol = Board.COL_A;
        //Putting queen on the board
        board.setPieceAt(d8Queen.getPieceRow(), d8Queen.getPieceCol(), d8Queen);
        d8Queen.move(expectedRow, expectedCol, board);
        assertEquals(d8Queen, board.getPieceAt(expectedRow, expectedCol));
        assertEquals(null, board.getPieceAt(Board.BLACK_BACK_ROW, Board.COL_D));
        assertEquals(expectedRow, board.getPieceAt(expectedRow, expectedCol).getPieceRow());
        assertEquals(expectedCol, board.getPieceAt(expectedRow, expectedCol).getPieceCol());
    }

    @Test
    public void whiteQueenD1toG4DiagonalBlockedMoveTest() {
        Player white = new Player("white", 0, Color.WHITE);
        Pawn f3Pawn = new Pawn(new Integer[][]{{Board.ROW_3, Board.COL_F}}, Color.WHITE, white);
        Queen d1Queen = new Queen(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_D}}, Color.WHITE, white);
        Board board = new Board(white, new Player("black", 0, Color.BLACK));
        Integer expectedRow = Board.WHITE_BACK_ROW, expectedCol = Board.COL_D;
        //Putting queen and pawn on the board
        board.setPieceAt(f3Pawn.getPieceRow(), f3Pawn.getPieceCol(), f3Pawn);
        board.setPieceAt(d1Queen.getPieceRow(), d1Queen.getPieceCol(), d1Queen);
        d1Queen.move(Board.ROW_4, Board.COL_G, board);
        assertEquals(d1Queen, board.getPieceAt(expectedRow, expectedCol));
        assertEquals(expectedRow, board.getPieceAt(expectedRow, expectedCol).getPieceRow());
        assertEquals(expectedCol, board.getPieceAt(expectedRow, expectedCol).getPieceCol());
    }

}
