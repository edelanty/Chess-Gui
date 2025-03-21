package com.evan.p2pChess.Pieces;

import com.evan.p2pChess.Board;
import com.evan.p2pChess.Color;
import com.evan.p2pChess.Player;
import com.evan.p2pChess.Gui.P2PChess;

/**
 * Contains all the functionality for the Pawn piece in a classic game of chess. This class inherits from the Piece class in order
 * to keep a well-designed OOP paradigm.
 * Contains methods for validating a proper pawn move (2 steps forward if unmoved, 1 step otherwise, diagonal capture, en passant) and draws possible moves from its position,
 * AND contains the move method (sets the piece given the coordinates and replaces it's old position with an empty tile).
 * 
 * @author Evan Delanty
 * @version v1.0.0
 * @since 3/19/2025
 */
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

    public void drawPossiblePieceMoves(P2PChess gui, Board board) {
        Integer curRow = this.getPieceRow();
        Integer curCol = this.getPieceCol();
        Integer newRow = curRow + direction;
        Integer newCol = null;

        //Single move or double move upwards highlighting
        if (newRow >= 0 && newRow < Board.BOARD_SIZE) {
            if (isValidMove(newRow, curCol, board)) {
                gui.moveHighlightTile(pieceColor, newRow, curCol);

                if (!hasMoved) {
                    newRow = curRow + (2 * direction);

                    if (newRow >= 0 && newRow < Board.BOARD_SIZE) {
                        if (isValidMove(newRow, curCol, board)) {
                            gui.moveHighlightTile(pieceColor, newRow, curCol);
                        }
                    }
                }
            }
        }

        //Diagonal capture highlighting
        int[] captureColOffsets = {-1, 1};

        for (int colOffset : captureColOffsets) {
            newRow = curRow + direction;
            newCol = curCol + colOffset;

            if (newRow >= 0 && newRow < Board.BOARD_SIZE && newCol >= 0 && newCol < Board.BOARD_SIZE) {
                Piece target = board.getPieceAt(newRow, newCol);

                if (target != null && target.getPieceColor() != pieceColor) { //If there's a piece and it's the opposing color
                    gui.moveHighlightTile(pieceColor, newRow, newCol);
                }
            }

            //Add en passant highlighting TODO
        }

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
