package com.evan.p2pChess.Pieces;

import com.evan.p2pChess.Board;
import com.evan.p2pChess.Color;
import com.evan.p2pChess.Player;

public class Pawn extends Piece {
    private boolean hasMoved;
    private Integer direction;

    public Pawn(Integer[][] position, Color color, Player owner) {
        super(position, "Pawn", 1, color, owner);
        this.hasMoved = false;
        this.direction = (color == Color.WHITE) ? -1 : 1;
    }

    //Getters
    public boolean getHasMoved() {
        return hasMoved;
    }

    //Setters
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    @Override
    public boolean isValidMove(Integer newRow, Integer newCol, Board board) {
        boolean isValidMove = false;
        Integer curRow = this.getPieceRow();
        Integer curCol = this.getPieceCol();

        if (newRow == curRow + direction && newCol == curCol && board.getPieceAt(newRow, newCol) == null) {
            isValidMove = true; //Normal 1 step movement (if new row is + direction and it's same column and it's an empty tile ahead, return true)
        } else if (!getHasMoved() && newRow == curRow + (direction * 2) && newCol == curCol && board.getPieceAt(curRow + direction, curCol) == null && board.getPieceAt(newRow, newCol) == null) {
            isValidMove = true; //First move 2 step movement (if this pawn hasn't moved yet and same as above and nothing in front, return true)
        } else if (newRow == curRow + direction && (newCol == curCol + 1 || newCol == curCol - 1)) {
            Piece target = board.getPieceAt(newRow, newCol); //Capture case (diagonal) (if the target new row is curRow + direction and it's new col is left or right)
            if (target != null && target.getPieceColor() != this.getPieceColor()) { 
                isValidMove = true; //If player IS moving diagonal, check if there's a non-null piece on the tile and if it's opp color
            }
        } else {
            isValidMove = false;
        }

        return isValidMove;
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

            //If not the first move set it equal to true
            if (!hasMoved) {
                setHasMoved(true);
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
    //TODO code en passant
}
