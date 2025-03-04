package com.evan.p2pChess.Pieces;

import com.evan.p2pChess.Board;
import com.evan.p2pChess.Color;

public class Knight extends Piece implements Movement {

    public Knight(Integer[][] position, Color color) {
        super(position, "Knight", 3, color);
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
