package com.evan.p2pChess.Pieces;

import com.evan.p2pChess.Board;
import com.evan.p2pChess.Color;
import com.evan.p2pChess.Player;
import com.evan.p2pChess.Gui.P2PChess;

public class Bishop extends Piece {

    public Bishop(Integer[][] position, Color color, Player owner) {
        super(position, "Bishop", 3, color, owner);
    }

    public void drawPossiblePieceMoves(P2PChess gui, Board board) {
        Integer curRow = this.getPieceRow();
        Integer curCol = this.getPieceCol();
        
        //Define the diagonal directions a bishop can move
        int[][] diagonalDirections = {
            {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };
        
        //Check all possible positions in each direction
        for (int[] dir : diagonalDirections) {
            int rowDir = dir[0];
            int colDir = dir[1];
            
            //A bishop can move any number of squares diagonally (until edge or blocked)
            for (int distance = 1; distance < Board.BOARD_SIZE; distance++) {
                int newRow = curRow + (rowDir * distance);
                int newCol = curCol + (colDir * distance);
                
                if (newRow >= 0 && newRow < Board.BOARD_SIZE && newCol >= 0 && newCol < Board.BOARD_SIZE) {
                    if (isValidMove(newRow, newCol, board)) {
                        gui.moveHighlightTile(pieceColor, newRow, newCol);
                    }
                    
                    //If there's a piece at this position, we can't move further in this direction
                    if (board.getPieceAt(newRow, newCol) != null) {
                        break;
                    }
                } else {                    
                    break; //Position is off the board, stop checking in this direction
                }
            }
        }
    }

    @Override
    public boolean isValidMove(Integer newRow, Integer newCol, Board board) {
        Piece target = board.getPieceAt(newRow, newCol);
        Integer curRow = this.getPieceRow();
        Integer curCol = this.getPieceCol();

        if (Math.abs(newRow - curRow) != Math.abs(newCol - curCol)) { //Checking whether or not the move is diagonal
            return false;
        }

        if (!isDiagonalPathClear(curRow, curCol, newRow, newCol, board)) { //Checking if the path is clear
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
