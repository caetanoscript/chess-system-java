package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

import java.awt.*;

public abstract class ChessPiece extends Piece {

    private Color color;

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return color;
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
