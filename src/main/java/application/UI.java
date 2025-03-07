package application;

import chess.ChessMath;
import chess.ChessPosition;
import chess.ChessPiece;
import chess.Color;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UI {

    public static ChessPosition readChessPosition(Scanner sc){
        try {
            String s = sc.nextLine();
            char column = s.charAt(0);
            int row = Integer.parseInt(s.substring(1));
            return new ChessPosition(column, row);
        }
        catch (RuntimeException e){
            throw new InputMismatchException("Eror reading ChessPosition. ");
        }

    }
    public static void printmatch(ChessMath chessMath, List<ChessPiece> captured){
        printBoard(chessMath.getpieces());
        System.out.println();
        printCapturedPieces(captured);
        System.out.println(" ");
        System.out.println(" ");
        System.out.println("turn: " + chessMath.getTurn());
        System.out.println("waiting player: " + chessMath.getCurrentPlayer());
    }

    public static void printBoard(ChessPiece[][] pieces) {
        for (int i = 0; i < pieces.length; i ++){
            System.out.print((8- i) + " ");
            for (int j = 0; j < pieces.length; j ++){
                printPiece(pieces[i][j]);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }
    public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
        for (int i = 0; i < pieces.length; i ++){
            System.out.print((8- i) + " ");
            for (int j = 0; j < pieces.length; j ++){
                printPiece(pieces[i][j]);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }


    public static void printPiece(ChessPiece piece) {

        if (piece == null) {
            System.out.print("-");
        } else {
            System.out.print(piece);
        }
        System.out.print(" ");
    }

    // https://stackoverflow.com/questions/2979383/java-clear-the-console
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void printCapturedPieces(List<ChessPiece> captured){

        List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList());
        List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList());
        System.out.println("captured pieces: ");
        System.out.print("white: ");
        System.out.print(Arrays.toString(white.toArray()));

        System.out.println(" ");
        System.out.print("black: ");
        System.out.print(Arrays.toString(black.toArray()));
        System.out.print(" ");



    }

}
