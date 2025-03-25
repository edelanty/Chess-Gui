package com.evan.p2pChess.Pieces;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.evan.p2pChess.Board;
import com.evan.p2pChess.Color;
import com.evan.p2pChess.Player;

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

    @Override
    public List<Point> getLegalMoves(Board board) {
        List<Point> legalMoves = new ArrayList<>();
        int curRow = getPieceRow();
        int curCol = getPieceCol();
        int newRow = curRow + direction;
        Integer newCol = null;

        //Single move or double move forward
        if (newRow >= 0 && newRow < Board.BOARD_SIZE) {
            if (isValidMove(newRow, curCol, board)) {
                legalMoves.add(new Point(newRow, curCol));

                //Double move from starting position
                if (!hasMoved) {
                    newRow = curRow + (2 * direction);

                    if (newRow >= 0 && newRow < Board.BOARD_SIZE) {
                        if (isValidMove(newRow, curCol, board)) {
                            legalMoves.add(new Point(newRow, curCol));
                        }
                    }
                }
            }
        }

        //Diagonal capture moves
        int[] captureColOffsets = {-1, 1};

        for (int colOffset : captureColOffsets) {
            newRow = curRow + direction;
            newCol = curCol + colOffset;

            if (newRow >= 0 && newRow < Board.BOARD_SIZE && newCol >= 0 && newCol < Board.BOARD_SIZE) {
                Piece target = board.getPieceAt(newRow, newCol);

                if (target != null && target.getPieceColor() != pieceColor) {
                    legalMoves.add(new Point(newRow, newCol));
                }
            }

            //En passant capture
            if (newCol >= 0 && newCol < Board.BOARD_SIZE) {
                Point enPassant = board.getEnPassantSquare();
                if (enPassant != null && enPassant.equals(new Point(newRow, newCol))) {
                    Piece adjacentPawn = board.getPieceAt(curRow, newCol);
                    if (adjacentPawn instanceof Pawn && adjacentPawn.getPieceColor() != this.getPieceColor()) {
                        legalMoves.add(new Point(newRow, newCol));
                    }
                }
            }
        }

        return legalMoves;
    }

    @Override
    public boolean isValidMove(Integer newRow, Integer newCol, Board board) {
        boolean isValidMove = false;
        Integer curRow = this.getPieceRow();
        Integer curCol = this.getPieceCol();
    
        //En Passant logic
        if (newRow == curRow + direction && (newCol == curCol + 1 || newCol == curCol - 1)) {
            Point enPassant = board.getEnPassantSquare();
            if (enPassant != null && enPassant.equals(new Point(newRow, newCol))) {
                Piece adjacentPawn = board.getPieceAt(curRow, newCol);
                if (adjacentPawn instanceof Pawn && adjacentPawn.getPieceColor() != this.getPieceColor()) {
                    return true; //En passant capture
                }
            }
        }
    
        //Normal 1 step forward movement
        if (newRow == curRow + direction && newCol == curCol && board.getPieceAt(newRow, newCol) == null) {
            isValidMove = true;
        }
        //Double move
        else if (!getHasMoved() && newRow == curRow + (direction * 2) && newCol == curCol && 
                 board.getPieceAt(curRow + direction, curCol) == null && board.getPieceAt(newRow, newCol) == null) {
            isValidMove = true;
        }
        //Diagonal capture
        else if (newRow == curRow + direction && (newCol == curCol + 1 || newCol == curCol - 1)) {
            Piece target = board.getPieceAt(newRow, newCol);
            if (target != null && target.getPieceColor() != this.getPieceColor()) {
                isValidMove = true;
            }
        }
    
        return isValidMove;
    }
    
    @Override
    public void move(Integer newRow, Integer newCol, Board board) {
        if (isValidMove(newRow, newCol, board)) {
            Integer curRow = this.getPieceRow();
            Integer curCol = this.getPieceCol();
    
            //En Passant capture logic
            if (Math.abs(curCol - newCol) == 1 && board.getPieceAt(newRow, newCol) == null) {
                Point enPassant = board.getEnPassantSquare();
                if (enPassant != null && enPassant.equals(new Point(newRow, newCol))) {
                    Piece adjacentPawn = board.getPieceAt(curRow, newCol);
                    if (adjacentPawn instanceof Pawn && adjacentPawn.getPieceColor() != this.getPieceColor()) {
                        //Perform the en passant capture
                        Piece capturedPawn = board.getPieceAt(curRow, newCol);
                        board.capturePiece(capturedPawn); //Capture the adjacent pawn
                        board.setPieceAt(curRow, newCol, null); //Remove the captured pawn from the board
                    }
                }
            }
    
            Piece captured = board.getPieceAt(newRow, newCol);
    
            //Handle normal captures (non-en-passant)
            if (captured != null && captured.getPieceColor() != this.getPieceColor()) {
                board.capturePiece(captured);
            }
    
            //If a double move, set the possible enPassantSquare
            if (Math.abs(newRow - curRow) == 2) {
                board.setEnPassantSquare(new Point(curRow + direction, curCol));
            }
    
            //Update "hasMoved" after first move
            if (!hasMoved) {
                setHasMoved(true);
            }
    
            //Remove old position from board
            board.setPieceAt(curRow, curCol, null);
    
            //Update piece position
            this.setPieceRow(newRow);
            this.setPieceCol(newCol);
    
            //Place piece at new position on the board
            board.setPieceAt(newRow, newCol, this);
    
            //Check for promotion after move
            checkForPromotion(board);
        } else {
            throw new IllegalArgumentException("Invalid Piece Move");
        }
    }
    
    /**
     * checkForPromotion()
     * 
     * Checks if a given pawn can promote, gives an option between the Queen, Knight, Rook, and Bishop.
     * Once chosen places the piece on the board.
     * 
     * @param board
     */
    private void checkForPromotion(Board board) {
        Integer curRow = this.getPieceRow();
        Integer curCol = this.getPieceCol();
        if ((this.pieceColor == Color.WHITE && curRow == Board.BLACK_BACK_ROW) || (this.pieceColor == Color.BLACK && curRow == Board.WHITE_BACK_ROW)) {
            Piece promoted = promotePiece(this, board);
            board.setPieceAt(curRow, curCol, promoted);
            promoted.setPieceRow(curRow);
            promoted.setPieceCol(curCol);
        }
    }

}
