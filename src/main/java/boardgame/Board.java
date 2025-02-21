package boardgame;

public class Board {

    private int rows;
    private int columns;
    private Piece[][] pieces;

    public Board(int columns, int rows) {
        if (rows < 1 || columns < 1){
            throw new BoardException("Error creating bord: there must be at least 1 row and 1 column");
        }
        this.columns = columns;
        this.rows = rows;
        pieces = new Piece[rows][columns];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Piece piece(int row, int colunm) {
        if (!positionExists(row,colunm)) {
            throw new BoardException("position not on the board");
        }
        return pieces[row][colunm];
    }

    public Piece piece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("position not on the board");
        }
        return pieces[position.getRow()][position.getColumn()];
    }

    public void placePiece(Piece piece, Position position) {
        if(thereISApiece((position))){
            throw new BoardException("there is alredy a píece on position " + position);
        }
        pieces[position.getRow()][position.getColumn()] = piece;
        piece.position = position;
    }

    private Boolean positionExists(int row, int column) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    public boolean positionExists(Position position) {
        return positionExists(position.getRow(), position.getColumn());
    }

    public boolean thereISApiece(Position position){
        if (!positionExists(position)) {
            throw new BoardException("position not on the board");
        }
        return piece(position) != null;
    }
}
