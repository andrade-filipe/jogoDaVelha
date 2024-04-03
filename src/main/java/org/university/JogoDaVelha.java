package org.university;

import java.util.ArrayList;

public class JogoDaVelha {

    public static final char X_PLAYER = 'x';
    public static final char O_PLAYER = 'o';
    public static final char[] PLAYERS = new char[] {X_PLAYER, O_PLAYER};
    public static final ArrayList<int[]> WINNING_POSITIONS = new ArrayList<>();
    private int[] board;
    private ArrayList moves;
    private ArrayList xPositions;
    private ArrayList oPositions;
    public JogoDaVelha(){
        this.board = new int[9];
        this.moves = new ArrayList();
        this.xPositions = new ArrayList();
        this.oPositions = new ArrayList();
        populateWinningPositions();
    }

    public void doMove(char player, int position){
        this.board[position] = player;
    }

    public ArrayList movesPlayed(){
        for (int i = 0; i < this.board.length; i++) {
            if(this.board[i] == 0){
                this.moves.add(i);
            }
        }
        return this.moves;
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
        if(this.moves.size() == 9){
            return true;
        }
        if(this.checkWinner() != 0){
            return true;
        }
        return false;
    }

    public char checkWinner(){
        this.refreshMarkedPositions();
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

    public void printBoard(){
        System.out.println(" " + this.board[0] + " | " + this.board[1] + " | " + this.board[2] + " ");
        System.out.println("---+---+---");
        System.out.println(" " + this.board[3] + " | " + this.board[4] + " | " + this.board[5] + " ");
        System.out.println("---+---+---");
        System.out.println(" " + this.board[6] + " | " + this.board[7] + " | " + this.board[8] + " ");
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
