package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.piecies.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ChessMath {

    private Board board;
    private Color currentPlayer;
    private int turn;
    private boolean check;
    private boolean checkMate;


    private List<Piece> piecesOnTheBoard = new ArrayList();
    private List<Piece> capturedPieces = new ArrayList();


    public ChessMath(){
        board = new Board(8, 8);
        turn = 1;
        currentPlayer = Color.WHITE;
        check = false;
        initialSetup();
    }

    public int getTurn(){
        return turn;
    }

    public boolean getCheck(){
        return check;
    }

    public boolean getCheckMate(){
        return checkMate;
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

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);

        if (!board.thereISApiece(source)) {
            throw new ChessException("Erro: Nenhuma peça encontrada na posição " + sourcePosition);
        }

        Piece capturedPiece = makeMove(source, target);

        ChessPiece movingPiece = (ChessPiece) board.piece(target);
        if (movingPiece == null) {
            throw new ChessException("Erro: A peça não foi movida para a posição " + targetPosition);
        }

        if (testCheck(currentPlayer)) {
            undoMove(source, target, capturedPiece);
            throw new ChessException("Você não pode se colocar em xeque!");
        }

        check = testCheck(opponent(currentPlayer));

        if (testCheckMate(opponent(currentPlayer))) {
            checkMate = true;
            System.out.println("CHEQUE-MATE! " + currentPlayer + " venceu!");
            return (ChessPiece) capturedPiece;
        }

        nextTurn();
        return (ChessPiece) capturedPiece;
    }




    private boolean kingExists(Color color) {
        return piecesOnTheBoard.stream().anyMatch(p -> p instanceof King && ((ChessPiece) p).getColor() == color);
    }


    private Piece makeMove(Position source, Position target) {
        ChessPiece p = (ChessPiece) board.removePiece(source);
        if (p == null) {
            throw new ChessException("Tentativa de mover uma peça inexistente da posição: " + source);
        }

        p.increaseMoveCount();

        Piece capturedPiece = board.removePiece(target);

        System.out.println("Movendo peça: " + p);
        System.out.println("Peça capturada: " + capturedPiece);

        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
            System.out.println("Peça removida da lista: " + capturedPiece);
        }

        board.placePiece(p, target);
        p.setPosition(target);

        return capturedPiece;
    }


    private void undoMove(Position source, Position target, Piece capturedPiece) {
        ChessPiece p = (ChessPiece) board.removePiece(target);
        p.decreaseMoveCount();
        if (p != null) {
            board.placePiece(p, source);
            if (p instanceof King && !piecesOnTheBoard.contains(p)) {
                piecesOnTheBoard.add(p);
            }
        }

        if (capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            if (!piecesOnTheBoard.contains(capturedPiece)) {
                piecesOnTheBoard.add(capturedPiece);
            }
        }
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
        Piece targetPiece = board.piece(target);
        if (targetPiece instanceof King) {
            throw new ChessException("Você não pode capturar o rei!");
        }
    }

    private void nextTurn(){
        turn++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;

    }

    private Color opponent(Color Color){
        return (Color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private ChessPiece King(Color color){
        List<Piece> list = piecesOnTheBoard.stream()
                .filter(x -> ((ChessPiece)x).getColor() == color)
                .collect(Collectors.toList());


        for (Piece p : list){
            if (p instanceof King){
                return (ChessPiece) p;
            }
        }

        throw new IllegalStateException("O rei de " + color + " sumiu do tabuleiro!");
    }

    private boolean testCheck(Color color) {
        Position kingPosition = King(color).getChessPosition().toPosition();
        List<Piece> opponentPieces = piecesOnTheBoard.stream()
                .filter(x -> ((ChessPiece) x).getColor() == opponent(color))
                .toList();

        for (Piece p : opponentPieces) {
            boolean[][] mat = p.possibleMoves();
            if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
                return true;
            }
        }
        return false;
    }

    private boolean testCheckMate(Color color) {

        if (!testCheck(color)) {
            return false;
        }

        List<Piece> opponentPieces = piecesOnTheBoard.stream()
                .filter(x -> ((ChessPiece) x).getColor() == color)
                .collect(Collectors.toList());

        for (Piece p : opponentPieces) {
            boolean[][] mat = p.possibleMoves();
            for (int i = 0; i < board.getRows(); i++) {
                for (int j = 0; j < board.getColumns(); j++) {
                    if (mat[i][j]) {
                        Position source = ((ChessPiece) p).getChessPosition().toPosition();
                        Position target = new Position(i, j);

                        Piece capturedPiece = makeMove(source, target);

                        boolean isCheck = testCheck(color);

                        undoMove(source, target, capturedPiece);

                        if (!isCheck) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    private void placeNewPiece(char column, int row, ChessPiece piece){
        board.placePiece(piece,new ChessPosition(column,row).toPosition());
        piecesOnTheBoard.add(piece);
    }

    private void initialSetup(){
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK));

    }

}
