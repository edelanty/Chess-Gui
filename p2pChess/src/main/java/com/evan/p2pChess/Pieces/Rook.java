package com.evan.p2pChess.Pieces;

import com.evan.p2pChess.Board;
import com.evan.p2pChess.Color;
import com.evan.p2pChess.Player;
import com.evan.p2pChess.Gui.P2PChess;

/**
 * Contains all the functionality for the Rook piece in a classic game of chess. This class inherits from the Piece class in order
 * to keep a well-designed OOP paradigm.
 * Contains methods for validating a proper rook move (straight only, not out of bounds, etc...) and draws possible moves from its position,
 * AND contains the move method (sets the piece given the coordinates and replaces it's old position with an empty tile).
 * 
 * @author Evan Delanty
 * @version v1.0.0
 * @since 3/19/2025
 */
public class Rook extends Piece {
    private boolean hasMoved;

    public Rook(Integer[][] position, Color color, Player owner) {
        super(position, "Rook", 5, color, owner);
        this.hasMoved = false;
    }
    
    //Getters
    public boolean getHasMoved() {
        return hasMoved;
    }

    //Setters
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public void drawPossiblePieceMoves(P2PChess gui, Board board) {
        Integer curRow = this.getPieceRow();
        Integer curCol = this.getPieceCol();
        
        //Define the straight directions a rook can move
        int[][] straightDirections = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}
        };
        
        //Check all possible positions in each direction
        for (int[] dir : straightDirections) {
            int rowDir = dir[0];
            int colDir = dir[1];
            
            //A rook can move any number of squares in a straight line (until edge or blocked)
            for (int distance = 1; distance < Board.BOARD_SIZE; distance++) {
                int newRow = curRow + (rowDir * distance);
                int newCol = curCol + (colDir * distance);
                
                //Check if position is on the board
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

        if (curRow != newRow && curCol != newCol) { //Checking for if the move is actually straight
            return false;
        }

        if (!isStraightPathClear(curRow, curCol, newRow, newCol, board)) {
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
    
}
