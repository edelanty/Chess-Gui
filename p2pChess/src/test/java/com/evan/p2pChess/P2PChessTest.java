package com.evan.p2pChess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

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

    @Test
    public void whiteKnightB1toC3MoveTest() {
        Player white = new Player("white", 0, Color.WHITE);
        Knight b1Knight = new Knight(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_B}}, Color.WHITE, white);
        Board board = new Board(white, new Player("black", 0, Color.BLACK));
        Integer expectedRow = Board.ROW_3, expectedCol = Board.COL_C;
        //Putting knight on the board
        board.setPieceAt(b1Knight.getPieceRow(), b1Knight.getPieceCol(), b1Knight);
        b1Knight.move(expectedRow, expectedCol, board);
        assertEquals(b1Knight, board.getPieceAt(expectedRow, expectedCol));
        assertEquals(null, board.getPieceAt(Board.WHITE_BACK_ROW, Board.COL_B));
        assertEquals(expectedRow, board.getPieceAt(expectedRow, expectedCol).getPieceRow());
        assertEquals(expectedCol, board.getPieceAt(expectedRow, expectedCol).getPieceCol());
    }

    @Test
    public void whiteKnightG1toF3MoveTest() {
        Player white = new Player("white", 0, Color.WHITE);
        Knight g1Knight = new Knight(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_G}}, Color.WHITE, white);
        Board board = new Board(white, new Player("black", 0, Color.BLACK));
        Integer expectedRow = Board.ROW_3, expectedCol = Board.COL_F;
        //Putting knight on the board
        board.setPieceAt(g1Knight.getPieceRow(), g1Knight.getPieceCol(), g1Knight);
        g1Knight.move(expectedRow, expectedCol, board);
        assertEquals(g1Knight, board.getPieceAt(expectedRow, expectedCol));
        assertEquals(null, board.getPieceAt(Board.WHITE_BACK_ROW, Board.COL_G));
        assertEquals(expectedRow, board.getPieceAt(expectedRow, expectedCol).getPieceRow());
        assertEquals(expectedCol, board.getPieceAt(expectedRow, expectedCol).getPieceCol());
    }

    @Test
    public void blackKnightB8toA6MoveTest() {
        Player black = new Player("black", 0, Color.BLACK);
        Knight b8Knight = new Knight(new Integer[][]{{Board.BLACK_BACK_ROW, Board.COL_B}}, Color.BLACK, black);
        Board board = new Board(new Player("white", 0, Color.WHITE), black);
        Integer expectedRow = Board.ROW_6, expectedCol = Board.COL_A;
        //Putting knight on the board
        board.setPieceAt(b8Knight.getPieceRow(), b8Knight.getPieceCol(), b8Knight);
        b8Knight.move(expectedRow, expectedCol, board);
        assertEquals(b8Knight, board.getPieceAt(expectedRow, expectedCol));
        assertEquals(null, board.getPieceAt(Board.BLACK_BACK_ROW, Board.COL_B));
        assertEquals(expectedRow, board.getPieceAt(expectedRow, expectedCol).getPieceRow());
        assertEquals(expectedCol, board.getPieceAt(expectedRow, expectedCol).getPieceCol());
    }

    @Test
    public void whiteKnightB1toInvalidMovesTest() {
        Player white = new Player("white", 0, Color.WHITE);
        Knight b1Knight = new Knight(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_B}}, Color.WHITE, white);
        Board board = new Board(white, new Player("black", 0, Color.BLACK));
        Integer expectedRow = Board.WHITE_BACK_ROW, expectedCol = Board.COL_B;
        //Putting knight on the board
        board.setPieceAt(b1Knight.getPieceRow(), b1Knight.getPieceCol(), b1Knight);
        //Try to move straight up (not an L-shape)
        b1Knight.move(Board.ROW_2, Board.COL_B, board);
        assertEquals(b1Knight, board.getPieceAt(expectedRow, expectedCol));
        //Try to move diagonally (not an L-shape)
        b1Knight.move(Board.ROW_2, Board.COL_C, board);
        assertEquals(b1Knight, board.getPieceAt(expectedRow, expectedCol));
        assertEquals(expectedRow, board.getPieceAt(expectedRow, expectedCol).getPieceRow());
        assertEquals(expectedCol, board.getPieceAt(expectedRow, expectedCol).getPieceCol());
    }

    @Test
    public void blackKnightJumpingOverPiecesTest() {
        Player black = new Player("black", 0, Color.BLACK);
        Player white = new Player("white", 0, Color.WHITE);
        Knight g8Knight = new Knight(new Integer[][]{{Board.BLACK_BACK_ROW, Board.COL_G}}, Color.BLACK, black);
        //Place some pieces that would block other pieces but not a knight
        Pawn f7Pawn = new Pawn(new Integer[][]{{Board.BLACK_PAWN_ROW, Board.COL_F}}, Color.BLACK, black);
        Pawn g7Pawn = new Pawn(new Integer[][]{{Board.BLACK_PAWN_ROW, Board.COL_G}}, Color.BLACK, black);
        Pawn h7Pawn = new Pawn(new Integer[][]{{Board.BLACK_PAWN_ROW, Board.COL_H}}, Color.BLACK, black);
        Board board = new Board(white, black);
        Integer expectedRow = Board.ROW_6, expectedCol = Board.COL_H;
        //Putting pieces on the board
        board.setPieceAt(g8Knight.getPieceRow(), g8Knight.getPieceCol(), g8Knight);
        board.setPieceAt(f7Pawn.getPieceRow(), f7Pawn.getPieceCol(), f7Pawn);
        board.setPieceAt(g7Pawn.getPieceRow(), g7Pawn.getPieceCol(), g7Pawn);
        board.setPieceAt(h7Pawn.getPieceRow(), h7Pawn.getPieceCol(), h7Pawn);
        //Knight should be able to jump over the pawns
        g8Knight.move(expectedRow, expectedCol, board);
        assertEquals(g8Knight, board.getPieceAt(expectedRow, expectedCol));
        assertEquals(null, board.getPieceAt(Board.BLACK_BACK_ROW, Board.COL_G));
        assertEquals(expectedRow, board.getPieceAt(expectedRow, expectedCol).getPieceRow());
        assertEquals(expectedCol, board.getPieceAt(expectedRow, expectedCol).getPieceCol());
    }

    @Test
    public void whiteKnightCaptureTest() {
        Player white = new Player("white", 0, Color.WHITE);
        Player black = new Player("black", 0, Color.BLACK);
        Knight b1Knight = new Knight(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_B}}, Color.WHITE, white);
        Pawn c3Pawn = new Pawn(new Integer[][]{{Board.ROW_3, Board.COL_C}}, Color.BLACK, black);
        Board board = new Board(white, black);
        Integer expectedRow = Board.ROW_3, expectedCol = Board.COL_C;
        //Putting pieces on the board
        board.setPieceAt(b1Knight.getPieceRow(), b1Knight.getPieceCol(), b1Knight);
        board.setPieceAt(c3Pawn.getPieceRow(), c3Pawn.getPieceCol(), c3Pawn);
        //Knight should be able to capture the opponent's pawn
        b1Knight.move(expectedRow, expectedCol, board);
        assertEquals(b1Knight, board.getPieceAt(expectedRow, expectedCol));
        assertEquals(null, board.getPieceAt(Board.WHITE_BACK_ROW, Board.COL_B));
        assertEquals(expectedRow, board.getPieceAt(expectedRow, expectedCol).getPieceRow());
        assertEquals(expectedCol, board.getPieceAt(expectedRow, expectedCol).getPieceCol());
    }

    @Test
    public void whiteKnightBlockedBySameColorTest() {
        Player white = new Player("white", 0, Color.WHITE);
        Knight b1Knight = new Knight(new Integer[][]{{Board.WHITE_BACK_ROW, Board.COL_B}}, Color.WHITE, white);
        Pawn c3Pawn = new Pawn(new Integer[][]{{Board.ROW_3, Board.COL_C}}, Color.WHITE, white);
        Board board = new Board(white, new Player("black", 0, Color.BLACK));
        Integer expectedRow = Board.WHITE_BACK_ROW, expectedCol = Board.COL_B;
        //Putting pieces on the board
        board.setPieceAt(b1Knight.getPieceRow(), b1Knight.getPieceCol(), b1Knight);
        board.setPieceAt(c3Pawn.getPieceRow(), c3Pawn.getPieceCol(), c3Pawn);
        //Knight should not be able to move to a square occupied by a piece of the same color
        b1Knight.move(Board.ROW_3, Board.COL_C, board);
        assertEquals(b1Knight, board.getPieceAt(expectedRow, expectedCol));
        assertEquals(c3Pawn, board.getPieceAt(Board.ROW_3, Board.COL_C));
        assertEquals(expectedRow, board.getPieceAt(expectedRow, expectedCol).getPieceRow());
        assertEquals(expectedCol, board.getPieceAt(expectedRow, expectedCol).getPieceCol());
    }

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

    //AI tests

    @Test
    public void stockfishBestMoveTest() throws InterruptedException, ExecutionException, TimeoutException {
        Uci uci = new Uci();
        String bestMove = "";
        String expected = "f4g3";
        String position = "8/8/4Rp2/5P2/1PP1pkP1/7P/1P1r4/7K b - - 0 40";
        uci.start("stockfish");
        uci.command("uci", Function.identity(), (s) -> s.startsWith("uciok"), 2000l);
        // We set the give position
        uci.command("position fen " + position, Function.identity(), s -> s.startsWith("readyok"), 2000l);
        bestMove = uci.command(
            "go movetime 3000",
            lines -> lines.stream().filter(s->s.startsWith("bestmove")).findFirst().get(),
            line -> line.startsWith("bestmove"),
            5000l)
            .split(" ")[1];
        assertEquals(expected, bestMove);
        uci.close();
    }

    @Test
    public void stockfishBestMoveFunctionTest() throws InterruptedException, ExecutionException, TimeoutException {
        Uci uci = new Uci();
        String bestMove = "";
        String expected = "f4g3";
        String position = "8/8/4Rp2/5P2/1PP1pkP1/7P/1P1r4/7K b - - 0 40";
        uci.start("stockfish");
        bestMove = uci.getBestMove(position);
        assertEquals(expected, bestMove);
        uci.close();
    }

    //Fen Gen tests
    @Test
    public void testStartingPosition() {
        Board board = new Board(new Player("white", 0, Color.WHITE), new Player("black", 0, Color.BLACK));
        FenGenerator fenGen = new FenGenerator();
        board.resetBoard();
        String fenString = fenGen.generateFEN(board, true, 1);
        String expectedFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        assertEquals(expectedFEN, fenString);
    }

    @Test
    public void testEmptyBoard() {
        Board board = new Board(new Player("white", 0, Color.WHITE), new Player("black", 0, Color.BLACK));
        FenGenerator fenGen = new FenGenerator();
        // Don't reset board - start with an empty board
        String fenString = fenGen.generateFEN(board, true, 1);
        String expectedFEN = "8/8/8/8/8/8/8/8 w - - 0 1";
        assertEquals(expectedFEN, fenString);
    }

    @Test
    public void testBlackTurn() {
        Board board = new Board(new Player("white", 0, Color.WHITE), new Player("black", 0, Color.BLACK));
        FenGenerator fenGen = new FenGenerator();
        board.resetBoard();
        String fenString = fenGen.generateFEN(board, false, 1);
        String expectedFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR b KQkq - 0 1";
        assertEquals(expectedFEN, fenString);
    }
//TODO
    @Test
    public void enPassantFenTest() {
        Board board = new Board(new Player("white", 0, Color.WHITE), new Player("black", 0, Color.BLACK));
        FenGenerator fenGen = new FenGenerator();
        board.resetBoard();
        
        // Simulate e4 move: move e2 pawn to e4
        Piece pawn = board.getPieceAt(Board.ROW_2, Board.COL_E);
        // Remove pawn from e2
        board.setPieceAt(Board.ROW_2, Board.COL_E, null);
        // Place pawn at e4
        pawn.setPieceRow(Board.ROW_4);
        pawn.setPieceCol(Board.COL_E);
        board.setPieceAt(Board.ROW_4, Board.COL_E, pawn);
        
        String fenString = fenGen.generateFEN(board, false, 1);
        String expectedFEN = "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1";
        // assertEquals(expectedFEN, fenString);
    }

}
