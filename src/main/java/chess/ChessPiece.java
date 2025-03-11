package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

import java.awt.*;

public abstract class ChessPiece extends Piece {

    private Color color;
    private int moveCount;

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void increaseMoveCount(){
        moveCount++;
    }
    public void decreaseMoveCount(){
        moveCount--;
    }

    public ChessPosition getChessPosition(){
        return ChessPosition.fromPosition(position);
    }

    protected boolean isThereOpponentPiece(Position position) {
        Piece p = getBoard().piece(position);
        if (p instanceof ChessPiece) {
            ChessPiece chessPiece = (ChessPiece) p;
            return chessPiece.getColor() != this.getColor();
        }

        return false;
    }




}
