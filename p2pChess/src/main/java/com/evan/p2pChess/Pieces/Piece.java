package com.evan.p2pChess.Pieces;

import com.evan.p2pChess.Color;
import com.evan.p2pChess.Player;

/**
 * Piece Class
 * 
 * This is an abstract class for each piece of the game. Every piece inherits this class, and uses its features.
 * 
 * @author Evan Delanty
 */
public abstract class Piece implements Movement {
    protected Integer[][] piecePosition;
    protected String pieceName;
    protected Integer pieceValue;
    protected Color pieceColor;
    protected Player pieceOwner;

    public Piece(Integer[][] position, String name, Integer value, Color color, Player owner) {
        this.piecePosition = position;
        this.pieceName = name;
        this.pieceValue = value;
        this.pieceColor = color;
        this.pieceOwner = owner;
    }

    //Getters
    public String getPieceName() {
        return pieceName;
    }

    public Integer getPieceRow() {
        return piecePosition[0][0];
    }

    public Integer getPieceCol() {
        return piecePosition[0][1];
    }

    public Integer getPieceValue() {
        return pieceValue;
    }

    public Color getPieceColor() {
        return pieceColor;
    }

    public Player getPieceOwner() {
        return pieceOwner;
    }

    //Setters
    public void setPieceRow(Integer newX) {
        piecePosition[0][0] = newX;
    }

    public void setPieceCol(Integer newY) {
        piecePosition[0][1] = newY;
    }

    /**
     * getPieceSymbol()
     * 
     * xxx
     * 
     * @return
     */
    public String getPieceSymbol() {
        String symbol = "";

        switch (this.getPieceName()) {
            case "Pawn": symbol = this.getPieceColor() == Color.WHITE ? "♙" : "♟"; break;
            case "Rook": symbol = this.getPieceColor() == Color.WHITE ? "♖" : "♜"; break;
            case "Knight": symbol = this.getPieceColor() == Color.WHITE ? "♘" : "♞"; break;
            case "Bishop": symbol = this.getPieceColor() == Color.WHITE ? "♗" : "♝"; break;
            case "Queen": symbol = this.getPieceColor() == Color.WHITE ? "♕" : "♛"; break;
            case "King": symbol = this.getPieceColor() == Color.WHITE ? "♔" : "♚"; break;
        }

        return symbol;
    }

}
