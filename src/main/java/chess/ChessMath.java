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

    private void initialSetup(){
        board.placePiece(new Rook(board, Color.WHITE), new Position(2,1));
        board.placePiece(new King(board, Color.WHITE), new Position(5,1));
    }

}
