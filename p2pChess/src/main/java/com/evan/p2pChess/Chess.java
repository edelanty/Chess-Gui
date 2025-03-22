package com.evan.p2pChess;

public class Chess {
    public static void main(String[] args) {
        Uci uci = new Uci();
        uci.start("stockfish");
        Game game = new Game(uci);
        game.gameStart();
        // uci.close();
    }
}
