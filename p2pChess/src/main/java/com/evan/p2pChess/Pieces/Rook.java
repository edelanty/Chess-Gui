package com.evan.p2pChess.Pieces;

import com.evan.p2pChess.Board;
import com.evan.p2pChess.Color;

public class Rook extends Piece implements Movement {

    public Rook(Integer[][] position, Color color) {
        super(position, "Rook", 5, color);
    }

    @Override
    public boolean isValidMove(Piece piece, Integer newX, Integer newY, Board board) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isValidMove'");
    }

    @Override
    public void move(Piece piece, Integer newX, Integer newY, Board board) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'move'");
    }
    
}
