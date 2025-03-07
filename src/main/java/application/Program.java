package application;

import chess.ChessException;
import chess.ChessPosition;
import chess.ChessMath;
import chess.ChessPiece;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Program {

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);
        ChessMath chasmath = new ChessMath();
        List<ChessPiece> captured = new ArrayList<>();

        while (true) {
            try {
                UI.clearScreen();
                UI.printmatch(chasmath, captured);
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

                if (capturedPiece != null){
                    captured.add(capturedPiece);
                }

            }
            catch (ChessException | InputMismatchException e){
            System.out.println(e.getMessage());
            sc.nextLine();
        }
        }


    }
}
