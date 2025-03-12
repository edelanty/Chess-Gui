package com.evan.p2pChess.Pieces;

import com.evan.p2pChess.Board;
import com.evan.p2pChess.Color;
import com.evan.p2pChess.Player;

public class Knight extends Piece {

    public Knight(Integer[][] position, Color color, Player owner) {
        super(position, "Knight", 3, color, owner);
    }

    @Override
    public boolean isValidMove(Integer newRow, Integer newCol, Board board) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isValidMove'");
    }

    @Override
    public void move(Integer newRow, Integer newCol, Board board) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'move'");
    }
    
}
