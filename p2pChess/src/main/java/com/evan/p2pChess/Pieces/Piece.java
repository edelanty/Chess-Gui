package com.evan.p2pChess.Pieces;

import com.evan.p2pChess.Color;

/**
 * Piece Class
 * 
 * This is an abstract class for each piece of the game. Every piece inherits this class, and uses its features.
 * 
 * @author Evan Delanty
 */

public abstract class Piece {
    protected Integer[][] piecePosition;
    protected String pieceName;
    protected Integer pieceValue;
    protected Color pieceColor;

    public Piece(Integer[][] position, String name, Integer value, Color color) {
        this.piecePosition = position;
        this.pieceName = name;
        this.pieceValue = value;
        this.pieceColor = color;
    }

    //Getters
    public String getPieceName() {
        return pieceName;
    }

}
