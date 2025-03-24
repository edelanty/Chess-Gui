package com.evan.p2pChess.Pieces;

import java.awt.Image;
import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import com.evan.p2pChess.Board;
import com.evan.p2pChess.Color;
import com.evan.p2pChess.Player;
import com.evan.p2pChess.Gui.P2PChess;

/**
 * Piece Class
 * 
 * This is an abstract class for each piece of the game. Every piece inherits this class, and uses its features.
 * 
 * @author Evan Delanty
 * @version v1.0.0
 * @since 3/19/2025
 */
public abstract class Piece implements Movement {
    protected Integer[][] piecePosition;
    protected String pieceName;
    protected Integer pieceValue;
    protected Color pieceColor;
    protected Player pieceOwner;
    protected final Map<String, ImageIcon> pieceImageCache = new HashMap<>();

    public Piece(Integer[][] position, String name, Integer value, Color color, Player owner) {
        this.piecePosition = position;
        this.pieceName = name;
        this.pieceValue = value;
        this.pieceColor = color;
        this.pieceOwner = owner;
    }

    //Getters
    public String getPieceName() {
        return pieceName;
    }

    public Integer getPieceRow() {
        return piecePosition[0][0];
    }

    public Integer getPieceCol() {
        return piecePosition[0][1];
    }

    public Integer getPieceValue() {
        return pieceValue;
    }

    public Color getPieceColor() {
        return pieceColor;
    }

    public Player getPieceOwner() {
        return pieceOwner;
    }

    //Setters
    public void setPieceRow(Integer newX) {
        piecePosition[0][0] = newX;
    }

    public void setPieceCol(Integer newY) {
        piecePosition[0][1] = newY;
    }

    /**
     * getLegalMoves()
     * 
     * Returns a list of all legal moves given a piece.
     * 
     * @param board
     * @return
     */
    public abstract List<Point> getLegalMoves(Board board);

    /**
     * drawPossiblePieceMoves()
     * 
     * Changes the color of tiles on the board where a piece could theoretically move unblocked.
     * 
     */
    public void drawPossiblePieceMoves(P2PChess gui, Board board) {
        List<Point> legalMoves = getLegalMoves(board);
        
        for (Point move : legalMoves) {
            int newCol = move.x;
            int newRow = move.y;

            gui.moveHighlightTile(this.getPieceColor(), newRow, newCol);
        }
    }

    /**
     * getPieceSymbol()
     * 
     * xxx
     * 
     * @return
     */
    public String getPieceSymbol() {
        String symbol = "";

        switch (this.getPieceName()) {
            case "Pawn": symbol = this.getPieceColor() == Color.WHITE ? "♙" : "♟"; break;
            case "Rook": symbol = this.getPieceColor() == Color.WHITE ? "♖" : "♜"; break;
            case "Knight": symbol = this.getPieceColor() == Color.WHITE ? "♘" : "♞"; break;
            case "Bishop": symbol = this.getPieceColor() == Color.WHITE ? "♗" : "♝"; break;
            case "Queen": symbol = this.getPieceColor() == Color.WHITE ? "♕" : "♛"; break;
            case "King": symbol = this.getPieceColor() == Color.WHITE ? "♔" : "♚"; break;
        }

        return symbol;
    }

    public String getFenSymbol() {
        String symbol = "";

        switch (this.getPieceName()) {
            case "Pawn": symbol = this.getPieceColor() == Color.WHITE ? "P" : "p"; break;
            case "Rook": symbol = this.getPieceColor() == Color.WHITE ? "R" : "r"; break;
            case "Knight": symbol = this.getPieceColor() == Color.WHITE ? "N" : "n"; break;
            case "Bishop": symbol = this.getPieceColor() == Color.WHITE ? "B" : "b"; break;
            case "Queen": symbol = this.getPieceColor() == Color.WHITE ? "Q" : "q"; break;
            case "King": symbol = this.getPieceColor() == Color.WHITE ? "K" : "k"; break;
        }

        return symbol;
    }

    private String getPieceImageKey() {
        String color = this.getPieceColor() == Color.WHITE ? "white" : "black";
        String name = this.getPieceName().toLowerCase();
        
        return color + "-" + name;
    }

    public ImageIcon getPieceImageIcon() {
        String key = this.getPieceImageKey();

        if (!pieceImageCache.containsKey(key)) {
            ImageIcon icon = new ImageIcon(getClass().getResource("/com/evan/p2pChess/Gui/Images/Pieces/" + key + ".png"));
            Image scaled = icon.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
            icon = new ImageIcon(scaled);
            pieceImageCache.put(key, icon);
        }

        return pieceImageCache.get(key);    
    }

    /**
     * isDiagonalPathClear()
     * 
     * Checks each tile given a new path for a piece, returns false if blocked returns true if not.
     * 
     * @param startRow
     * @param startCol
     * @param endRow
     * @param endCol
     * @param board
     * @return
     */
    protected boolean isDiagonalPathClear(Integer curRow, Integer curCol, Integer newRow, Integer newCol, Board board) {
        boolean isDiagonalPathClear = true;
        Integer rowDirection = (newRow > curRow) ? 1 : -1;
        Integer colDirection = (newCol > curCol) ? 1 : -1;

        Integer tempRow = curRow + rowDirection;
        Integer tempCol = curCol + colDirection;

        while (tempRow != newRow && tempCol != newCol) { //While we haven't reached the destination
            if (tempRow < 0 || tempRow >= Board.BOARD_SIZE || tempCol < 0 || tempCol >= Board.BOARD_SIZE) {
                return false; //Early exit if invalid direction
            }

            if (board.getPieceAt(tempRow, tempCol) != null) { //Check for blockage set false and break if so
                isDiagonalPathClear = false;

                break;
            }

            tempRow += rowDirection;
            tempCol += colDirection;
        }

        return isDiagonalPathClear;
    }

    /**
     * isStraightPathClear()
     * 
     * Checks each tile given a new position returns false if not clear, true if clear
     * 
     * @param curRow
     * @param curCol
     * @param newRow
     * @param newCol
     * @param board
     * @return
     */
    protected boolean isStraightPathClear(Integer curRow, Integer curCol, Integer newRow, Integer newCol, Board board) {
        boolean isStraightPathClear = true;
        Integer direction = 0, tempRow = 0, tempCol = 0;

        if (curRow == newRow) { //If moving horizontally across columns
            direction = (newCol > curCol) ? 1 : -1;
            tempCol = curCol + direction;

            while (tempCol != newCol) { //While we haven't reached the destination tile
                if (tempRow < 0 || tempRow >= Board.BOARD_SIZE || tempCol < 0 || tempCol >= Board.BOARD_SIZE) {
                    return false; //Early exit if invalid direction
                }
                
                if (board.getPieceAt(curRow, tempCol) != null) {
                    isStraightPathClear = false;

                    break;
                }

                tempCol += direction;
            }
        } else { //Moving vertically up a column
            direction = (newRow > curRow) ? 1 : -1;
            tempRow = curRow + direction;

            while (tempRow != newRow) {
                if (board.getPieceAt(tempRow, curCol) != null) {
                    isStraightPathClear = false;

                    break;
                }

                tempRow += direction;
            }
        }

        return isStraightPathClear;
    }

}
