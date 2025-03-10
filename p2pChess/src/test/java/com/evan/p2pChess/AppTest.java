package com.evan.p2pChess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.evan.p2pChess.Pieces.*;

/**
 * Unit test for simple App.
 */
public class AppTest {
    
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

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
        board.printBoardToTerminal();
        e2Pawn.move(Board.ROW_3, Board.COL_F, board);
        board.printBoardToTerminal();
        assertEquals(expectedPoints, white.getPlayerPoints()); //Player points should be 1 after capturing a pawn
        assertEquals(null, board.getPieceAt(Board.WHITE_PAWN_ROW, Board.COL_E)); //Check for null where the piece used to be
        assertEquals(expectedRow, board.getPieceAt(expectedRow, expectedCol).getPieceRow()); //Found the expected row
        assertEquals(expectedCol, board.getPieceAt(expectedRow, expectedCol).getPieceCol()); //Found the expected col
    }
}
