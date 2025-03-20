package com.evan.p2pChess.Pieces;

import com.evan.p2pChess.Board;

/**
 * The movement interfaces contains two required functions for piece moving each implemented separate from each piece.
 * 
 * @author Evan Delanty
 * @version v1.0.0
 * @since 3/19/2025
 */
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
