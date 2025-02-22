package chess;

import boardgame.Board;
import boardgame.Position;
import chess.piecies.King;
import chess.piecies.Rook;

public class ChessMath {

    private Board board;

    public ChessMath(){
        board = new Board(8, 8);
        initialSetup();
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
    private void placeNewPiece(char column, int row, ChessPiece piece){
        board.placePiece(piece,new ChassPosition(column,row).toPosition());
    }
    private void initialSetup(){
        placeNewPiece ('b', 6 ,new Rook(board, Color.WHITE));
        placeNewPiece ('e', 8 ,new King(board, Color.WHITE));
    }

}
