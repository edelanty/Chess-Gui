package com.evan.p2pChess;

import java.awt.Point;
import java.util.List;

import com.evan.p2pChess.Pieces.*;

public class CheckDetector {
    private Board board;

    public CheckDetector(Board board) {
        this.board = board;
    }

    public boolean isKingInCheck(Color color) {
        for (int row = 0; row < Board.BOARD_SIZE; row++) {
            for (int col = 0; col < Board.BOARD_SIZE; col++) {
                Piece piece = board.getPieceAt(row, col);
                if (piece instanceof King && piece.getPieceColor() == color) {
                    return ((King)piece).isKingInCheck(row, col, board);
                }
            }
        }

        return false;
    }

    public boolean isCheckmate(Color color) {
        return isKingInCheck(color) && !hasAnyLegalMove(color);
    }
    
    public boolean isStalemate(Color color) {
        return !isKingInCheck(color) && !hasAnyLegalMove(color);
    }    

    private boolean hasAnyLegalMove(Color color) {
        for (int row = 0; row < Board.BOARD_SIZE; row++) {
            for (int col = 0; col < Board.BOARD_SIZE; col++) {
                Piece piece = board.getPieceAt(row, col);
                if (piece != null && piece.getPieceColor() == color) {
                    List<Point> legalMoves = piece.getLegalMoves(board);
                    for (Point move : legalMoves) {
                        int toRow = (int)move.getX();
                        int toCol = (int)move.getY();
    
                        //If moving the piece here doesn't put the king in check, it's a legal move
                        if (!isKingInCheckAfterMove(piece, row, col, toRow, toCol, color)) {
                            return true; //At least one legal move exists
                        }
                    }
                }
            }
        }

        return false;
    }

    private boolean isKingInCheckAfterMove(Piece piece, int fromRow, int fromCol, int toRow, int toCol, Color color) {
        //Save original state
        Piece originalDestinationPiece = board.getPieceAt(toRow, toCol);
        //Update board state
        board.setPieceAt(fromRow, fromCol, null);
        board.setPieceAt(toRow, toCol, piece);
        //Temporarily update piece's internal position
        int originalRow = piece.getPieceRow();
        int originalCol = piece.getPieceCol();

        piece.setPieceRow(toRow);
        piece.setPieceCol(toCol);

        //Find the king piece and check the possible move
        boolean inCheck = isKingInCheck(color);

        //Restore original state
        board.setPieceAt(toRow, toCol, originalDestinationPiece);
        board.setPieceAt(fromRow, fromCol, piece);
        //Restore piece's original position
        piece.setPieceRow(originalRow);
        piece.setPieceCol(originalCol);
        
        return inCheck;
    }
    
}
