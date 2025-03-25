package com.evan.p2pChess;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.evan.p2pChess.Pieces.Piece;

public class FenNotationGeneratorTest {
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
        System.out.println("Current Working Directory: " + System.getProperty("user.dir"));
        assertEquals(expectedFEN, fenString);
    }

    @Test
    public void enPassantFenTest() {
        //TODO
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
