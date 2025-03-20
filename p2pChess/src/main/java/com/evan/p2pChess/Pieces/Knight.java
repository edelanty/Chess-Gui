package com.evan.p2pChess.Pieces;

import com.evan.p2pChess.Board;
import com.evan.p2pChess.Color;
import com.evan.p2pChess.Player;
import com.evan.p2pChess.Gui.P2PChess;

/**
 * Contains all the functionality for the Knight piece in a classic game of chess. This class inherits from the Piece class in order
 * to keep a well-designed OOP paradigm.
 * Contains methods for validating a proper knight move (L shaped) and draws possible moves from its position,
 * AND contains the move method (sets the piece given the coordinates and replaces it's old position with an empty tile).
 * 
 * @author Evan Delanty
 * @version v1.0.0
 * @since 3/19/2025
 */
public class Knight extends Piece {

    public Knight(Integer[][] position, Color color, Player owner) {
        super(position, "Knight", 3, color, owner);
    }
    
    public void drawPossiblePieceMoves(P2PChess gui, Board board) {
        Integer curRow = this.getPieceRow();
        Integer curCol = this.getPieceCol();

        int[][] knightMoves = {
            {-2, -1}, {-2, 1},
            {-1, -2}, {-1, 2},
            {1, -2}, {1, 2},
            {2, -1}, {2, 1}
        };
    
        for (int[] move : knightMoves) {
            int newRow = curRow + move[0];
            int newCol = curCol + move[1];

            if (newRow >= 0 && newRow < Board.BOARD_SIZE && newCol >= 0 && newCol < Board.BOARD_SIZE) { //If the indices are in the bounds of our array
                if (isValidMove(newRow, newCol, board)) { //Check for a valid move at the possible new positions
                    gui.moveHighlightTile(this.getPieceColor(), newRow, newCol); //Highlight the pieces that are valid at the position
                }
            }
        }
    }

    @Override
    public boolean isValidMove(Integer newRow, Integer newCol, Board board) {
        Piece target = null;
        Integer curRow = this.getPieceRow();
        Integer curCol = this.getPieceCol();
        Integer rowDiff = 0, colDiff = 0;
        boolean isLShape = false;

        target = board.getPieceAt(newRow, newCol);

        rowDiff = Math.abs(newRow - curRow);
        colDiff = Math.abs(newCol - curCol);

        isLShape = (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);

        if (!isLShape) { //If the movement is not an L shape early return false
            return false;
        }

        if (target == null || target.getPieceColor() != this.getPieceColor()) { //Finally checking if the destination is either empty or an opposing piece (capture)
            return true;
        }

        return false;
    }

    @Override
    public void move(Integer newRow, Integer newCol, Board board) {
        if (isValidMove(newRow, newCol, board)) {
            Integer curRow = this.getPieceRow();
            Integer curCol = this.getPieceCol();

            Piece captured = board.getPieceAt(newRow, newCol);

            //Handle captures
            if (captured != null && captured.getPieceColor() != this.getPieceColor()) { //If the potential captured piece isn't null and the colors are opposite, "capture"
                board.capturePiece(captured);
            }

            //Remove old position from board
            board.setPieceAt(curRow, curCol, null);

            //Update piece position
            this.setPieceRow(newRow);
            this.setPieceCol(newCol);

            //Update board with new piece position
            board.setPieceAt(newRow, newCol, this);
        } else {
            System.out.println("Invalid Move"); 
        }
    }
    
}
