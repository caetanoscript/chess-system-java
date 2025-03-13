package chess.piecies;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMath;
import chess.ChessPiece;
import chess.Color;

public class  Pawn extends ChessPiece {

    private ChessMath chessMath;


    public Pawn(Board board, Color color, ChessMath chessMath) {
        super(board, color);
        this.chessMath = chessMath;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position p = new Position(0, 0);


        int direction = (getColor() == Color.WHITE) ? -1 : 1;
        int startingRow = (getColor() == Color.WHITE) ? 6 : 1;
        int enPassantRow = (getColor() == Color.WHITE) ? 3 : 4;

        // Movimento padrão para frente
        p.setValues(position.getRow() + direction, position.getColumn());
        if (getBoard().positionExists(p) && !getBoard().thereISApiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Movimento duplo para frente
        p.setValues(position.getRow() + (2 * direction), position.getColumn());
        Position p2 = new Position(position.getRow() + direction, position.getColumn());
        if (getBoard().positionExists(p) && !getBoard().thereISApiece(p) &&
                getBoard().positionExists(p2) && !getBoard().thereISApiece(p2) && getMoveCount() == 0) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        // Capturas diagonais
        checkDiagonal(mat, position.getRow() + direction, position.getColumn() - 1); // Diagonal esquerda
        checkDiagonal(mat, position.getRow() + direction, position.getColumn() + 1); // Diagonal direita

        // Lógica de en passant
        if (position.getRow() == enPassantRow) {
            checkEnPassant(mat, position.getRow(), position.getColumn() - 1); // Esquerda
            checkEnPassant(mat, position.getRow(), position.getColumn() + 1); // Direita
        }

        return mat;
    }

    private void checkDiagonal(boolean[][] mat, int row, int col) {
        Position p = new Position(row, col);
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }
    }

    private void checkEnPassant(boolean[][] mat, int row, int col) {
        Position p = new Position(row, col);
        if (getBoard().positionExists(p) && isThereOpponentPiece(p) &&
                getBoard().piece(p) == chessMath.getEnPassantVulnerable()) {
            mat[p.getRow() + (getColor() == Color.WHITE ? -1 : 1)][p.getColumn()] = true;
        }
    }
    @Override
    public String toString() {
        return "P";
    }
}
