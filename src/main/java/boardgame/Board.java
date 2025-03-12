package boardgame;

public class Board {

    private int rows;
    private int columns;
    private Piece[][] pieces;

    public Board(int rows, int columns) {
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
            System.out.println("🚨 ERRO! O método thereIsAPiece diz que já existe uma peça em " + position);
            throw new BoardException("there is alredy a píece on position " + position);
        }
        pieces[position.getRow()][position.getColumn()] = piece;
        piece.position = position;
    }

    public Piece removePiece(Position position){
        if(!positionExists(position)){
            throw new BoardException("Position not on the board");
        }
        if (piece(position) == null){
            return null;
        }
        Piece aux = piece(position);
        aux.position = null;
        pieces[position.getRow()][position.getColumn()] = null;
        return aux;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            sb.append((8 - i) + " "); // Número da linha no tabuleiro de xadrez
            for (int j = 0; j < columns; j++) {
                sb.append(pieces[i][j] == null ? "- " : pieces[i][j] + " ");
            }
            sb.append("\n");
        }
        sb.append("  a b c d e f g h\n");
        return sb.toString();
    }

}
