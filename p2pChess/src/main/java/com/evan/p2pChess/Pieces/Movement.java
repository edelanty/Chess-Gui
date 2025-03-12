package com.evan.p2pChess.Pieces;

import com.evan.p2pChess.Board;

public interface Movement {

    /**
     * isValidMove()
     * 
     * Returns true or false depending on if the given move is valid or not.
     * 
     * @param newX Move's x position
     * @param newY Move's y position
     * @param board Board state
     */
    boolean isValidMove(Integer newRow, Integer newCol, Board board);

    /**
     * move()
     * 
     * xxx
     * 
     * @param newX Move's x position
     * @param newY Move's y position
     * @param board Board state
     */
    void move(Integer newRow, Integer newCol, Board board);
}
