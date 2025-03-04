package com.evan.p2pChess.Pieces;

import com.evan.p2pChess.Board;

public interface Movement {

    /**
     * isValidMove()
     * 
     * Returns true or false depending on if the given move is valid or not.
     * 
     * @return
     */
    boolean isValidMove(Piece piece, Integer newX, Integer newY, Board board);

    /**
     * 
     * 
     * @param piece Given piece
     * @param newX Move's x position
     * @param newY Move's y position
     * @param board Board state
     */
    void move(Piece piece, Integer newX, Integer newY, Board board);
}
