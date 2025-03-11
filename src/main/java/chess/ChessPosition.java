package chess;

import boardgame.Position;

public class ChessPosition {
    private char column;
    private int row;

    public ChessPosition(char column, int row) {
        if(column < 'a'|| column > 'h'|| row < 1 || row > 8){
            throw new ChessException("Erro initiating ChesPosition. valid values are from a1 to h8.");
        }
        this.column = column;
        this.row = row;
    }

    public char getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public Position toPosition() {
        int rowIndex = 8 - row;
        int columnIndex = column - 'a';
        return new Position(rowIndex, columnIndex);
    }


    protected static ChessPosition fromPosition(Position position) {
        if (position == null) {
            throw new IllegalArgumentException("Posição não pode ser nula!");
        }
        return new ChessPosition((char) ('a' + position.getColumn()), 8 - position.getRow());
    }



    @Override
    public String toString() {
        return "" + column + row;
    }
}
