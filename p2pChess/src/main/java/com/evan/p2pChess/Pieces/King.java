package com.evan.p2pChess.Pieces;

import com.evan.p2pChess.Board;
import com.evan.p2pChess.Color;
import com.evan.p2pChess.Player;
import com.evan.p2pChess.Gui.P2PChess;

/**
 * Contains all the functionality for the King piece in a classic game of chess. This class inherits from the Piece class in order
 * to keep a well-designed OOP paradigm.
 * Contains methods for validating a proper king move (one space all around, not in check after move) and draws possible moves from its position,
 * AND contains the move method (sets the piece given the coordinates and replaces it's old position with an empty tile).
 * Contains the logic for "checking" each king as well.
 * 
 * @author Evan Delanty
 * @version v1.0.0
 * @since 3/19/2025
 */
public class King extends Piece {
    private boolean isChecked;
    private boolean hasMoved;

    public King(Integer[][] position, Color color, Player owner) {
        super(position, "King", 0, color, owner);
        this.isChecked = false;
        this.hasMoved = false;
    }

    //Getters
    public boolean getHasMoved() {
        return hasMoved;
    }

    //Setters
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    /**
     * Determines if the king has castling rights for FEN notation purposes.
     * This only checks if the king and relevant rook have maintained their eligibility
     * (have not moved), not whether castling is immediately possible.
     * 
     * @param kingside true for kingside castling, false for queenside castling
     * @param board the current game board
     * @return true if the king has castling rights with the specified rook
     */
    public boolean canFenCastle(boolean kingside, Board board) {
        //If the king has moved, no castling rights
        if (hasMoved) {
            return false;
        }
        
        //Determine which rook to check
        int rookCol = kingside ? Board.COL_H : Board.COL_A;
        int rookRow = this.getPieceRow(); // Same row as king
        
        //Get the rook
        Piece rook = board.getPieceAt(rookRow, rookCol);
        
        //Check if rook exists, is the right type, and hasn't moved
        if (rook != null && rook instanceof Rook && rook.getPieceColor() == this.getPieceColor()) {
            Rook rookPiece = (Rook) rook;
            return !rookPiece.getHasMoved();
        }
        
        return false;
    }

    public boolean isKingInCheck(Integer newRow, Integer newCol, Board board) {
        boolean isKingChecked = false;
        
        for (int row = 0; row < Board.BOARD_SIZE; row++) {
            for (int col = 0; col < Board.BOARD_SIZE; col++) {
                Piece attacker = board.getPieceAt(row, col);

                if (attacker != null && attacker.getPieceColor() != pieceColor && !(attacker instanceof King)) { //If the potential attacker is a piece of opposing color
                    if (attacker.isValidMove(newRow, newCol, board)) { //If the potential attacker has a valid move on where the king is
                        isKingChecked = true; //The king is checked

                        break;
                    }
                }
            }

            if (isKingChecked) { //Early exit
                break;
            }
        }

        return isKingChecked;
    }

    public void drawPossiblePieceMoves(P2PChess gui, Board board) {
        Integer curRow = this.getPieceRow();
        Integer curCol = this.getPieceCol();
        
        int[][] kingDirections = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1},           {0, 1},
            {1, -1},  {1, 0},  {1, 1}
        };
        
        for (int[] dir : kingDirections) {
            int newRow = curRow + dir[0];
            int newCol = curCol + dir[1];
            
            if (newRow >= 0 && newRow < Board.BOARD_SIZE && newCol >= 0 && newCol < Board.BOARD_SIZE) {
                if (isValidMove(newRow, newCol, board)) {
                    gui.moveHighlightTile(this.getPieceColor(), newRow, newCol);
                }
            }
        }
    }

    public boolean canCastle(Integer newRow, Integer newCol, Board board) {
        int direction = (newCol > getPieceCol()) ? 1 : -1; //1 for kingside, -1 for queenside

        //Ensure the King is not in check
        if (isKingInCheck(this.getPieceRow(), this.getPieceCol(), board)) {
            return false;
        }

        //Check if the squares between the King and the Rook are clear
        int startCol = getPieceCol() + direction; //First square the king will move through
        int endCol = (direction == 1) ? Board.COL_H : Board.COL_A; //Rook's position column

        for (int col = startCol; col != endCol; col += direction) {
            if (board.getPieceAt(getPieceRow(), col) != null) {
                //Found a blocking piece
                return false;
            }
        }

        //Find the Rook's position
        int rookCol = (direction == 1) ? Board.COL_H : Board.COL_A;
        Piece rook = board.getPieceAt(getPieceRow(), rookCol);

        //Ensure the Rook is present and hasn't moved
        if (rook == null || !(rook instanceof Rook) || ((Rook) rook).getHasMoved()) {
            return false;
        }

        //Check if the King passes through any attacked squares
        int[] threatCols = (direction == 1) ? new int[]{Board.COL_F, Board.COL_G} : new int[]{Board.COL_C, Board.COL_D};

        for (int col : threatCols) {
            if (isKingInCheck(getPieceRow(), col, board)) {
                return false;
            }
        }

        return true;
    }

    /**
     * isMoveCastle()
     * 
     * Returns true if the incoming king move is an attempted castle, false if not
     * 
     * @param newRow
     * @param newCol
     * @return
     */
    private boolean isMoveCastle(Integer newRow, Integer newCol) {
        Integer curRow = this.getPieceRow();

        if (hasMoved || isChecked) { //Can't castle if either
            return false;
        }

        if (newRow != curRow) { //Early exit if this is the case
            return false;
        }

        return newCol == Board.COL_G || newCol == Board.COL_C;
    }

    @Override
    public boolean isValidMove(Integer newRow, Integer newCol, Board board) {
        int curRow = this.getPieceRow();
        int curCol = this.getPieceCol();
        int rowDiff = Math.abs(newRow - curRow);
        int colDiff = Math.abs(newCol - curCol);

        if (newRow == curRow && newCol == curCol) { //Cannot move king onto same tile
            return false;
        }

        if (isKingInCheck(newRow, newCol, board)) { //Cannot move king into a checked tile
            return false;
        }

        if ((rowDiff <= 1 && colDiff <= 1) && (rowDiff + colDiff != 0)) {
            Piece target = board.getPieceAt(newRow, newCol);

            if (target == null || target.getPieceColor() != this.getPieceColor()) { //Capturable piece
                return true;
            }
        }

        if (isMoveCastle(newRow, newCol)) { //Makes castleing possible
            return canCastle(newRow, newCol, board);
        }

        return false;
    }

    @Override
    public void move(Integer newRow, Integer newCol, Board board) {
        Integer curRow = this.getPieceRow();
        Integer curCol = this.getPieceCol();
        
        if (isValidMove(newRow, newCol, board)) {
            boolean isCastling = isMoveCastle(newRow, newCol) && canCastle(newRow, newCol, board);

            if (isCastling) {
                castle(curRow, curCol, newRow, newCol, board);
            } else {
                Piece captured = board.getPieceAt(newRow, newCol);
    
                //Handle captures
                if (captured != null && captured.getPieceColor() != this.getPieceColor()) { //If the potential captured piece isn't null and the colors are opposite, "capture"
                    board.capturePiece(captured);
                }
    
                //If not the first move set it equal to true
                if (!hasMoved) {
                    setHasMoved(true);
                }
    
                //Remove old position from board
                board.setPieceAt(curRow, curCol, null);
    
                //Update piece position
                this.setPieceRow(newRow);
                this.setPieceCol(newCol);
    
                //Update board with new piece position
                board.setPieceAt(newRow, newCol, this);
            }
        } else {
            System.out.println("Invalid Move"); 
        }
    }

    private void castle(Integer row, Integer col, Integer newRow, Integer newCol, Board board) {
        //Remove old position from board
        board.setPieceAt(row, col, null);

        //Update piece position
        this.setPieceRow(newRow);
        this.setPieceCol(newCol);

        //Update board with new piece position
        board.setPieceAt(newRow, newCol, this);

        //If not the first move set it equal to true
        if (!hasMoved) {
            setHasMoved(true);
        }

        //Additionally move the rook
        int rookRow = getPieceRow();
        int rookCol = (newCol > col) ? Board.COL_H : Board.COL_A;
        int rookNewCol = (newCol > col) ? newCol - 1 : newCol + 1;
        Piece rook = board.getPieceAt(row, rookCol);
        Rook rookPiece = (Rook) rook;
        
        //Set new row and col for the rook
        rookPiece.setPieceRow(row);
        rookPiece.setPieceCol(rookNewCol);

        //Update board with new piece position
        board.setPieceAt(row, rookNewCol, rookPiece);

        //Set old rook position to null and update hasMoved
        board.setPieceAt(rookRow, rookCol, null);
        rookPiece.setHasMoved(true);
    }
    
}
