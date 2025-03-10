package com.evan.p2pChess.Pieces;

import com.evan.p2pChess.Board;
import com.evan.p2pChess.Color;
import com.evan.p2pChess.Player;

public class Bishop extends Piece implements Movement {

    public Bishop(Integer[][] position, Color color, Player owner) {
        super(position, "Bishop", 3, color, owner);
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
