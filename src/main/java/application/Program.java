package application;

import chess.ChessException;
import chess.ChessPosition;
import chess.ChessMath;
import chess.ChessPiece;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Program {

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);
        ChessMath chasmath = new ChessMath();

        while (true) {
            try {
                UI.clearScreen();
                UI.printBoard(chasmath.getpieces());
                System.out.println();
                System.out.print("Source :");
                ChessPosition source = UI.readChessPosition(sc);

                boolean[][] possibleMoves = chasmath.possibleMoves(source);
                UI.clearScreen();
                UI.printBoard(chasmath.getpieces(), possibleMoves);


                System.out.println();
                System.out.println("Target: ");
                ChessPosition target = UI.readChessPosition(sc);

                ChessPiece capturedPiece = chasmath.performChessMove(source, target);
            }
            catch (ChessException | InputMismatchException e){
            System.out.println(e.getMessage());
            sc.nextLine();
        }
        }


    }
}
