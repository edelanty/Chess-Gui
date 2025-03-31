package com.evan.p2pChess.Online;

import java.io.IOException;

/**
 * NetworkConnection 
 * 
 * @author Evan Delanty
 * @version v1.0.0
 * @since 3/31/2025
 */
public interface NetworkConnection {

    /**
     * sendMove()
     * 
     * ...
     * 
     * @param move
     * @throws IOException
     */
    void sendMove(String move) throws IOException;

    /**
     * receiveMove()
     * 
     * ...
     * 
     * @return
     * @throws IOException
     */
    String receiveMove() throws IOException;
}
