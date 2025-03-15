package com.evan.p2pChess.Pieces;

import com.evan.p2pChess.Board;
import com.evan.p2pChess.Color;
import com.evan.p2pChess.Player;
import com.evan.p2pChess.Gui.P2PChess;

public class Queen extends Piece {

    public Queen(Integer[][] position, Color color, Player owner) {
        super(position, "Queen", 9, color, owner);
    }

    public void drawPossiblePieceMoves(P2PChess gui, Board board) {
        
    }

    @Override
    public boolean isValidMove(Integer newRow, Integer newCol, Board board) {
        Piece target = board.getPieceAt(newRow, newCol);
        Integer curRow = this.getPieceRow();
        Integer curCol = this.getPieceCol();

        if (Math.abs(newRow - curRow) == Math.abs(newCol - curCol)) { //Checking for diagonal
            if (!isDiagonalPathClear(curRow, curCol, newRow, newCol, board)) {
                return false;
            }
        } else if (curRow == newRow || curCol == newCol) { //Checking for straight
            if (!isStraightPathClear(curRow, curCol, newRow, newCol, board)) {
                return false;
            }
        } else { //Else it's not a valid queen move
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
