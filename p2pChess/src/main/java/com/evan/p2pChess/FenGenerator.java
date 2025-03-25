package com.evan.p2pChess;

import java.awt.Point;

import com.evan.p2pChess.Pieces.*;

public class FenGenerator {
    /**
     * Generates a FEN string representation of the current board state
     * FEN format: [piece placement] [active color] [castling availability] [en passant target] [halfmove clock] [fullmove number]
     * 
     * @param board The chess board
     * @param whiteTurn Boolean indicating if it's white's turn
     * @param moveNumber The current full move number
     * @return The FEN string representation
     */
    public String generateFEN(Board board, boolean whiteTurn, int moveNumber) {
        StringBuilder fen = new StringBuilder();
        
        // 1. Piece placement
        appendPiecePlacement(board, fen);
        
        // 2. Active color
        fen.append(" ").append(whiteTurn ? "w" : "b");
        
        // 3. Castling availability
        appendCastlingAvailability(board, fen);
        
        // 4. En passant target square
        appendEnPassantTarget(board, fen);
        
        // 5. Halfmove clock (assuming we're not tracking this, so using 0)
        fen.append(" 0");
        
        // 6. Fullmove number
        fen.append(" ").append(moveNumber);
        
        return fen.toString();
    }
    
    private void appendPiecePlacement(Board board, StringBuilder fen) {
        for (int row = 0; row < 8; row++) {
            int emptyCount = 0;
            
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPieceAt(row, col);
                
                if (piece == null) {
                    emptyCount++;
                } else {
                    // If there were empty squares before this piece, add the count
                    if (emptyCount > 0) {
                        fen.append(emptyCount);
                        emptyCount = 0;
                    }
                    
                    // Add the piece character
                    String fenSymbol = piece.getFenSymbol();
                    fen.append(fenSymbol);
                }
            }
            
            // If there are empty squares at the end of the row
            if (emptyCount > 0) {
                fen.append(emptyCount);
            }
            
            // Add row separator (except for the last row)
            if (row < 7) {
                fen.append("/");
            }
        }
    }
    
    private void appendCastlingAvailability(Board board, StringBuilder fen) {
        boolean whiteKingside = false;
        boolean whiteQueenside = false;
        boolean blackKingside = false;
        boolean blackQueenside = false;
        
        // Check all pieces for kings
        for (int row = 0; row < Board.BOARD_SIZE; row++) {
            for (int col = 0; col < Board.BOARD_SIZE; col++) {
                Piece piece = board.getPieceAt(row, col);
                
                if (piece instanceof King) {
                    King king = (King) piece;
                    if (king.getPieceColor() == Color.WHITE) {
                        whiteKingside = king.canFenCastle(true, board);
                        whiteQueenside = king.canFenCastle(false, board);
                    } else {
                        blackKingside = king.canFenCastle(true, board);
                        blackQueenside = king.canFenCastle(false, board);
                    }
                }
            }
        }
        
        fen.append(" ");
        
        // Append castling availability
        if (whiteKingside) fen.append("K");
        if (whiteQueenside) fen.append("Q");
        if (blackKingside) fen.append("k");
        if (blackQueenside) fen.append("q");
        
        // If no castling is available
        if (!(whiteKingside || whiteQueenside || blackKingside || blackQueenside)) {
            fen.append("-");
        }
    }
    
    /**
     * appendEnPassantTarget()
     * 
     * Checks for a possible enPassantSquare and puts it in the FEN string.
     * 
     * @param board
     * @param fen
     */
    private void appendEnPassantTarget(Board board, StringBuilder fen) {
        Point enPassantSquare = board.getEnPassantSquare();
        
        //Check if en passant square is actually set
        if (enPassantSquare != null && (enPassantSquare.x >= 0 && enPassantSquare.x < Board.BOARD_SIZE) && (enPassantSquare.y >= 0 && enPassantSquare.y < Board.BOARD_SIZE)) {
            char file = (char) ('a' + enPassantSquare.y);
            int rank = 8 - enPassantSquare.x;
            fen.append(" ").append(file).append(rank);
        } else {
            fen.append(" -");
        }
    }

}
