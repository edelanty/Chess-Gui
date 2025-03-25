package com.evan.p2pChess.Pieces;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.MouseEvent;

import com.evan.p2pChess.Board;
import com.evan.p2pChess.Color;
import com.evan.p2pChess.Player;
import com.evan.p2pChess.SoundManager;
import com.evan.p2pChess.Gui.P2PChess;
import com.evan.p2pChess.Gui.Settings;

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
    private JDialog promotionDialog;
    protected final Map<String, ImageIcon> pieceImageCache = new HashMap<>();

    public Piece(Integer[][] position, String name, Integer value, Color color, Player owner) {
        this.piecePosition = position;
        this.pieceName = name;
        this.pieceValue = value;
        this.pieceColor = color;
        this.pieceOwner = owner;
        this.promotionDialog = new JDialog();
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
            int newRow = move.x;
            int newCol = move.y;

            gui.moveHighlightTile(this.getPieceColor(), newRow, newCol);
        }
    }

    /**
     * promotePiece()
     * 
     * @param promotionPawn
     * @param board
     * @return
     */
    protected Piece promotePiece(Pawn promotionPawn, Board board) {
        //Create a custom dialog for piece selection
        promotionDialog = new JDialog();
        promotionDialog.setModal(true);
        promotionDialog.setUndecorated(true);
        promotionDialog.getRootPane().setBorder(BorderFactory.createLineBorder(java.awt.Color.BLACK, 2));
        
        //Create a panel with a 2x2 grid
        JPanel promotionPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        promotionPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Define promotion options
        List<Class<? extends Piece>> promotionOptions = Arrays.asList(Queen.class, Rook.class, Bishop.class, Knight.class);
        
        // Button selection result
        Piece[] selectedPiece = new Piece[1];
        
        // Create buttons for each piece type
        for (Class<? extends Piece> pieceClass : promotionOptions) {
            JButton pieceButton = createPromotionButton(
                pieceClass, 
                promotionPawn.getPieceColor(), 
                board, 
                promotionDialog, 
                selectedPiece,
                promotionPawn
            );
            promotionPanel.add(pieceButton);
        }
        
        // Setup dialog
        promotionDialog.add(promotionPanel);
        promotionDialog.pack();
        promotionDialog.setLocationRelativeTo(null); // Center on screen
        promotionDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        
        // Show dialog and wait for selection
        promotionDialog.setVisible(true);
        
        return selectedPiece[0];
    }

    private JButton createPromotionButton(Class<? extends Piece> pieceClass, Color color, Board board, JDialog parentDialog, Piece[] selectedPieceHolder, Pawn originalPawn) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(100, 100));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setBackground(Settings.WHITE_COLOR);
        
        try {
            //Create a temporary piece to get its icon
            Piece tempPiece = pieceClass.getConstructor(Integer[][].class, Color.class, Player.class).newInstance(new Integer[][]{{originalPawn.getPieceRow(), originalPawn.getPieceCol()}}, color, originalPawn.getPieceOwner());
            ImageIcon icon = tempPiece.getPieceImageIcon();
            button.setIcon(icon);
        } catch (Exception e) {
            button.setText(getPieceSymbol());
            e.printStackTrace();
        }
        
        button.addActionListener(e -> {
            try {
                //Create the new piece at the current pawn's location
                Piece promotedPiece = pieceClass.getConstructor(Integer[][].class, Color.class, Player.class).newInstance(new Integer[][]{{originalPawn.getPieceRow(), originalPawn.getPieceCol()}}, color, originalPawn.getPieceOwner());
                selectedPieceHolder[0] = promotedPiece;
                parentDialog.dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error creating piece: " + ex.getMessage(), "Promotion Error", JOptionPane.ERROR_MESSAGE
                );
            }
        });

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                SoundManager.play(getClass().getResource("/com/evan/p2pChess/Gui/Sounds/hover.wav"));
                button.setBackground(Settings.SELECTED_COLOR);
                promotionDialog.repaint();
            }

            @Override 
            public void mouseExited(MouseEvent e) {
                button.setBackground(Settings.WHITE_COLOR);
            }
        });
        
        return button;
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
