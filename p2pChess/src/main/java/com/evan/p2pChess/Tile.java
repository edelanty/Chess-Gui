package com.evan.p2pChess;

import com.evan.p2pChess.Pieces.Piece;

public class Tile {
    private Piece piece;

    public Tile() {
        this.piece = null; //null represents empty tile
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
