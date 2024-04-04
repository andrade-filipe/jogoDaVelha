package org.university;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class JogoDaVelha {

    public static final char X_PLAYER = 'x';
    public static final char O_PLAYER = 'o';
    public static final char[] PLAYERS = new char[] {X_PLAYER, O_PLAYER};
    public static final ArrayList<int[]> WINNING_POSITIONS = new ArrayList<>();
    private int[] board;
    private ArrayList<Integer> possibleMoves;
    private ArrayList<Integer> xPositions;
    private ArrayList<Integer> oPositions;

    public static int amountOfLosses = 0;
    public static int amountOfWins = 0;
    public JogoDaVelha(){
        this.board = new int[9];
        this.possibleMoves = new ArrayList<>();
        this.xPositions = new ArrayList<>();
        this.oPositions = new ArrayList<>();
        populateWinningPositions();
        refreshPossibleMoves();
    }

    public JogoDaVelha(int[] board){
        this.board = board.clone();
        this.possibleMoves = new ArrayList<>();
        this.xPositions = new ArrayList<>();
        this.oPositions = new ArrayList<>();
        populateWinningPositions();
        refreshPossibleMoves();
        refreshMarkedPositions();
    }

    public JogoDaVelha(int[] board, char player, int move){
        this.board = board.clone();
        this.possibleMoves = new ArrayList<>();
        this.xPositions = new ArrayList<>();
        this.oPositions = new ArrayList<>();

        this.doMove(player, move);
        populateWinningPositions();
        refreshPossibleMoves();
        refreshMarkedPositions();
    }

    public void doMove(char player, int position){
        this.board[position] = player;
    }

    public void refreshPossibleMoves(){
        this.possibleMoves.clear();
        for (int i = 0; i < this.board.length; i++) {
            if(this.board[i] == 0){
                this.possibleMoves.add(i);
            }
        }
    }

    public void markPositions(char player){
        for (int i = 0; i < this.board.length; i++) {
            if(this.board[i] == player){
                if(player == X_PLAYER && !this.xPositions.contains(i)){
                    this.xPositions.add(i);
                }
                if(player == O_PLAYER && !this.oPositions.contains(i)){
                    this.oPositions.add(i);
                }
            }
        }
    }

    public void refreshMarkedPositions(){
        for(char player : PLAYERS){
            this.markPositions(player);
        }
    }


    public boolean checkIfGameOver(){
        if(this.possibleMoves.isEmpty()){
            return true;
        }
        if(this.checkWinner() != '0'){
            return true;
        }
        return false;
    }

    public char checkWinner(){
        for(int[] win : WINNING_POSITIONS){
            if(checkIfPlayerXWon(win)){
                return X_PLAYER;
            }
            if(checkIfPlayerOWon(win)){
                return O_PLAYER;
            }
        }
        return '0';
    }

    public boolean checkIfPlayerXWon(int[] win){
        return this.xPositions.contains(win[0]) &&
                this.xPositions.contains(win[1]) &&
                this.xPositions.contains(win[2]);
    }
    public boolean checkIfPlayerOWon(int[] win){
        return this.oPositions.contains(win[0]) &&
                this.oPositions.contains(win[1]) &&
                this.oPositions.contains(win[2]);
    }

    public boolean xIsTheWinner(){
        return this.checkWinner() == X_PLAYER;
    }

    public boolean oIsTheWinner(){
        return this.checkWinner() == O_PLAYER;
    }

    public boolean draw(){
        return this.checkWinner() == '0';
    }

    public char switchPlayer(char player){
        if(player == X_PLAYER){
            return O_PLAYER;
        }
        return X_PLAYER;
    }

    public void printBoard(){
        System.out.println(" " + (char) this.board[0] + " | " + (char) this.board[1] + " | " + (char) this.board[2] + " ");
        System.out.println("---+---+---");
        System.out.println(" " +  (char) this.board[3] + " | " + (char) this.board[4] + " | " + (char) this.board[5] + " ");
        System.out.println("---+---+---");
        System.out.println(" " + (char) this.board[6] + " | " + (char) this.board[7] + " | " + (char) this.board[8] + " ");
    }

    public void printWinner(){
        if(draw()){
            System.out.println("Empate!");
        }
        if(oIsTheWinner()){
            System.out.println("O venceu!");
        }
        if(xIsTheWinner()){
            System.out.println("X venceu");
        }
    }

    public void playSolo(){
        Scanner input = new Scanner(System.in);
        this.printBoard();
        char player = X_PLAYER;

        while(!this.checkIfGameOver()){
            System.out.println(player + " Escolha uma posição de 0 a 8: ");
            int move = input.nextInt();

            if(validMove(move)){
                this.doMove(player, move);
            }
            if(this.checkIfGameOver()){
                break;
            }
            this.refreshPossibleMoves();
            this.refreshMarkedPositions();
            player = this.switchPlayer(player);
            this.printBoard();
        }
        this.printWinner();
        input.close();
    }

    public void playVsComputer(){
        Scanner input = new Scanner(System.in);
        char player = X_PLAYER;

        while(!this.checkIfGameOver()){
            this.printBoard();
            System.out.println(player + " Escolha uma posição de 0 a 8: ");
            int move = input.nextInt();

            if(validMove(move)){
                this.doMove(player, move);
            }

            this.refreshPossibleMoves();
            this.refreshMarkedPositions();
            player = this.switchPlayer(player);

            if(this.checkIfGameOver()){
                break;
            }

            if(player == O_PLAYER){
                System.out.println("Computer plays...");
                int pcMove = this.calculateMoveForO(this.board, O_PLAYER);
                if(validMove(pcMove)){
                    this.doMove(player, pcMove);
                }

                this.refreshPossibleMoves();
                this.refreshMarkedPositions();
                player = this.switchPlayer(player);

                if(this.checkIfGameOver()){
                    break;
                }
            }
        }
        this.printBoard();
        this.printWinner();
        input.close();
    }

    public void playComputerVsComputer(){
        char player = X_PLAYER;

        while(!this.checkIfGameOver()){

            if(player == X_PLAYER){
                System.out.println(player + " plays...");
                int pcMove = this.calculateMoveForX(this.board, X_PLAYER);
                if(validMove(pcMove)){
                    this.doMove(player, pcMove);
                }

                this.refreshPossibleMoves();
                this.refreshMarkedPositions();
                player = this.switchPlayer(player);

                if(this.checkIfGameOver()){
                    break;
                }
                this.printBoard();
            }

            if(player == O_PLAYER){
                System.out.println(player + " plays...");
                int pcMove = this.calculateMoveForO(this.board, O_PLAYER);
                if(validMove(pcMove)){
                    this.doMove(player, pcMove);
                }

                this.refreshPossibleMoves();
                this.refreshMarkedPositions();
                player = this.switchPlayer(player);

                if(this.checkIfGameOver()){
                    break;
                }
                this.printBoard();
            }
        }
        this.printBoard();
        this.printWinner();
    }

    private int calculateMoveForO(int[] board, char player) {
        int currEvaluation = Integer.MIN_VALUE;
        int bestMove = 9; //out of bounds
        for(int move : this.possibleMoves){
            amountOfLosses = 0;
            amountOfWins = 0;
            JogoDaVelha node = new JogoDaVelha(board, player,move);
            node.oMinimax(player);

            int evaluation = amountOfWins - amountOfLosses;
            if(evaluation > currEvaluation){
                currEvaluation = evaluation;
                bestMove = move;
            }else {
                //Alpha & Beta modification, cutting the algorithm as soon as it finds a worse decision than the previous one
                break;
            }
        }
        return bestMove;
    }

    public void oMinimax(char player){
        if(this.checkIfGameOver()){
            if(this.xIsTheWinner()){
                amountOfLosses += this.possibleMoves.size();
                return;
            } else if (this.draw()) {
                return;
            } else if(this.oIsTheWinner()){
                amountOfWins += this.possibleMoves.size();
                return;
            }
        }
        player = this.switchPlayer(player);
        for(int move : this.possibleMoves){
            JogoDaVelha child = new JogoDaVelha(this.board, player, move);
            child.oMinimax(player);
        }
    }

    private int calculateMoveForX(int[] board, char player) {
        int currEvaluation = Integer.MIN_VALUE;
        int bestMove = 9; //out of bounds
        if(this.possibleMoves.size() == 9){
            return this.possibleMoves.get(new Random().nextInt(0,8));
        }
        for(int move : this.possibleMoves){
            amountOfLosses = 0;
            amountOfWins = 0;
            JogoDaVelha node = new JogoDaVelha(board, player, move);
            node.xMinimax(player);
            int evaluation = amountOfWins - amountOfLosses;
            if(evaluation > currEvaluation){
                currEvaluation = evaluation;
                bestMove = move;
            } else {
                //Alpha & Beta modification, cutting the algorithm as soon as it finds a worse decision than the previous one
                //makes the AI dummer tho
                break;
            }
        }
        return bestMove;
    }

    public void xMinimax(char player){
        if(this.checkIfGameOver()){
            if(this.xIsTheWinner()){
                amountOfWins += this.possibleMoves.size();
                return;
            } else if (this.draw()) {
                return;
            } else if(this.oIsTheWinner()){
                amountOfLosses += this.possibleMoves.size();
                return;
            }
        }
        player = this.switchPlayer(player);
        for(int move : this.possibleMoves){
            JogoDaVelha child = new JogoDaVelha(this.board, player, move);
            child.xMinimax(player);
        }
    }

    public boolean validMove(int move){
        return move <= 8 && move >= 0 && this.possibleMoves.contains(move);
    }
    public void populateWinningPositions(){
        WINNING_POSITIONS.add(new int[] {0,1,2});
        WINNING_POSITIONS.add(new int[] {3,4,5});
        WINNING_POSITIONS.add(new int[] {6,7,8});
        WINNING_POSITIONS.add(new int[] {0,3,6});
        WINNING_POSITIONS.add(new int[] {1,4,7});
        WINNING_POSITIONS.add(new int[] {2,5,8});
        WINNING_POSITIONS.add(new int[] {0,4,8});
        WINNING_POSITIONS.add(new int[] {2,4,6});
    }
}
