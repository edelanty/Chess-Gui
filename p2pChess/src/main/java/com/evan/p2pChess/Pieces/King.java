package com.evan.p2pChess.Pieces;

import com.evan.p2pChess.Board;
import com.evan.p2pChess.Color;
import com.evan.p2pChess.Player;
import com.evan.p2pChess.Gui.P2PChess;

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

    public boolean isKingInCheck(Integer newRow, Integer newCol, Board board) {
        boolean isKingChecked = false;
        Integer curRow = newRow;
        Integer curCol = newCol;

        if (newRow == curRow && newCol == curCol) { //Cannot move king onto same tile
            return false;
        }

        for (int row = 0; row < Board.BOARD_SIZE; row++) {
            for (int col = 0; col < Board.BOARD_SIZE; col++) {
                Piece attacker = board.getPieceAt(row, col);

                if (attacker != null && attacker.getPieceColor() != pieceColor) { //If the potential attacker is a piece of opposing color
                    if (attacker.isValidMove(curRow, curCol, board)) { //If the potential attacker has a valid move on where the king is
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

    private boolean canCastle(Integer newRow, Integer newCol, Board board) {
        //The direction of castling: kingside or queenside
        int direction = (newCol > getPieceCol()) ? 1 : -1;
        
        //Ensure the King is not in check
        if (isKingInCheck(getPieceRow(), getPieceCol(), board)) {
            return false;
        }
    
        //Check the squares between the King and Rook are clear
        int curCol = getPieceCol();
        int startCol = curCol + direction;
        int endCol = newCol;
        
        for (int col = startCol; col != endCol; col += direction) {
            if (board.getPieceAt(getPieceRow(), col) != null) {
                return false;
            }
        }
    
        //Identify the Rook's position
        Piece rook = null;
        if (direction == 1) { //Kingside castling
            rook = board.getPieceAt(getPieceRow(), Board.COL_H);
        } else { //Queenside castling
            rook = board.getPieceAt(getPieceRow(), Board.COL_A);
        }
    
        //Ensure the Rook is present and hasn't moved
        if (rook != null && rook instanceof Rook) {
            Rook rookPiece = (Rook) rook;
            if (!rookPiece.getHasMoved()) {
                //Finally, check if the King passes through any attacked squares
                if (isKingInCheck(getPieceRow(), startCol, board) || isKingInCheck(getPieceRow(), endCol, board)) {
                    return false;
                }
    
                return true;
            }
        }
    
        return false;
    }    

    private boolean isMoveCastle(Integer newRow, Integer newCol) {
        Integer curRow = this.getPieceRow();

        if (hasMoved || isChecked) { //Can't castle if either
            return false;
        }

        if (getPieceColor() == Color.WHITE) { //Case for white-side castle
            if (newRow == curRow && newCol == Board.COL_G) { //King side castle
                return true;
            }
        } else {
            if (newRow == curRow && newCol == Board.COL_C) { //Queen side castle
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isValidMove(Integer newRow, Integer newCol, Board board) {
        int curRow = this.getPieceRow();
        int curCol = this.getPieceCol();
        int rowDiff = Math.abs(newRow - curRow);
        int colDiff = Math.abs(newCol - curCol);

        if (isKingInCheck(newRow, newCol, board)) { //Cannot move king into a checked tile
            return false;
        }

        if ((rowDiff <= 1 && colDiff <= 1) && (rowDiff + colDiff != 0)) {
            Piece target = board.getPieceAt(newRow, newCol);

            if (target == null || target.getPieceColor() != this.getPieceColor()) { //Capturable piece
                return true;
            }
        }

        if (isMoveCastle(newRow, newCol)) {
            return canCastle(newRow, newCol, board);
        }

        return false;
    }

    @Override
    public void move(Integer newRow, Integer newCol, Board board) {
        Integer curRow = this.getPieceRow();
        Integer curCol = this.getPieceCol();
        
        if (isMoveCastle(newRow, newCol) && canCastle(newRow, newCol, board)) { //Castle behavior
            //Remove old position from board
            board.setPieceAt(curRow, curCol, null);

            //Update piece position
            this.setPieceRow(newRow);
            this.setPieceCol(newCol);

            //Update board with new piece position
            board.setPieceAt(newRow, newCol, this);

            //Additionally move the rook
            int rookRow = getPieceRow();
            int rookCol = (newCol > curCol) ? Board.COL_H : Board.COL_A;
            int rookNewCol = (newCol > curCol) ? newCol - 1 : newCol + 1;
            Piece rook = board.getPieceAt(curRow, rookCol);
            Rook rookPiece = (Rook) rook;
            
            //Set new row and col for the rook
            rookPiece.setPieceRow(curRow);
            rookPiece.setPieceCol(rookNewCol);

            //Update board with new piece position
            board.setPieceAt(curRow, rookNewCol, rookPiece);

            //Set old rook position to null and update hasMoved
            board.setPieceAt(rookRow, rookCol, null);
            rookPiece.setHasMoved(true);
        } else { //Normal movement
            if (isValidMove(newRow, newCol, board) && !isKingInCheck(newRow, newCol, board)) {
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
            } else {
                System.out.println("Invalid Move"); 
            }
        }
    }
    
}
