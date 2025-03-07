package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.piecies.King;
import chess.piecies.Rook;

import java.util.ArrayList;
import java.util.List;

public class ChessMath {

    private Board board;
    private Color currentPlayer;
    private int turn;

    private List<Piece> piecesOnTheBoard = new ArrayList();
    private List<Piece> capturedPieces = new ArrayList();


    public ChessMath(){
        board = new Board(8, 8);
        turn = 1;
        currentPlayer = Color.WHITE;
        initialSetup();
    }

    public int getTurn(){
        return turn;
    }

    public Color getCurrentPlayer(){
        return currentPlayer;
    }

    public ChessPiece[][] getpieces(){
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
        for(int i = 0; i < board.getColumns(); i++){
           for (int j =0 ; j< board.getRows(); j++){
           mat[i][j] = (ChessPiece) board.piece(i,j);
           }
        }
        return mat;
    }

    public boolean[][] possibleMoves(ChessPosition sourcePosition){
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition){
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);
        nextTurn();
        return (ChessPiece) capturedPiece;
    }

    private Piece makeMove(Position source, Position target) {
        Piece p = board.removePiece(source);
        Piece capturedPiece = board.removePiece(target);

        board.placePiece(p, target);
        p.position = target;

        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }

        return capturedPiece;
    }



    private void validateSourcePosition(Position position) {
        if (!board.thereISApiece(position)) {
            throw new ChessException("There is no piece on source position");
        }
        ChessPiece piece = (ChessPiece) board.piece(position);

        if (piece.getColor() != currentPlayer) {
            throw new ChessException("The chosen piece does not belong to the current player");
        }
            if (!board.piece(position).isThereAnyPossibleMove()) {
            throw new ChessException("there is no possible moves for the chosen piece");
        }
    }

    private void validateTargetPosition(Position source, Position target){
            if (!board.piece(source).possibleMove(target)){
                throw new ChessException("The chose piece can´t move to taget position");
            }
    }

    private void nextTurn(){
        turn++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;

    }

    private void placeNewPiece(char column, int row, ChessPiece piece){
        board.placePiece(piece,new ChessPosition(column,row).toPosition());
        piecesOnTheBoard.add(piece);
    }

    private void initialSetup(){
        placeNewPiece('b', 8, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('f', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 1, new King(board, Color.WHITE));
        placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('f', 1, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('b', 1, new Rook(board, Color.WHITE));




    }

}
