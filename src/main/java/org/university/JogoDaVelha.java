package org.university;

import java.util.ArrayList;
import java.util.Scanner;

public class JogoDaVelha {

    public static final char X_PLAYER = 'x';
    public static final char O_PLAYER = 'o';
    public static final char[] PLAYERS = new char[] {X_PLAYER, O_PLAYER};
    public static final ArrayList<int[]> WINNING_POSITIONS = new ArrayList<>();
    private int[] board;
    private ArrayList possibleMoves;
    private ArrayList xPositions;
    private ArrayList oPositions;
    public JogoDaVelha(){
        this.board = new int[9];
        this.possibleMoves = new ArrayList();
        this.xPositions = new ArrayList();
        this.oPositions = new ArrayList();
        populateWinningPositions();
        refreshPossibleMoves();
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
            player = this.switchPlayer(player);
            this.printBoard();
        }
        this.printWinner();
        input.close();
    }

    public void playVsComputer(){
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
            player = this.switchPlayer(player);
            this.printBoard();
        }
        this.printWinner();
        input.close();
    }

//    #definição do algoritmo minimax para escolher o melhor movimento possivel para vencer a partida
//    def minimax(self, node, player):
//            if node.check_fim_partida():
//            if node.X_vence():
//            return -1
//    elif node.empate():
//            return 0
//    elif node.O_vence():
//            return 1
//
//    melhorValor = 0
//
//            for move in node.movimentos_disponiveis():
//            node.marca_movimento(move, player)
//    val = self.minimax(node, define_oponente(player))
//            node.marca_movimento(move, 0)
//            if player == 'O':
//            if val > melhorValor:
//    melhorValor = val
//          else:
//                  if val < melhorValor:
//    melhorValor = val
//        return melhorValor
//    ///////////////////////////////////////////////////////////////////////////////////////
//    #função piloto para aplicar o algoritmo minimax
//    def determine(tabuleiro, player):
//    a = 0
//    escolhas = []
//            if len(tabuleiro.movimentos_disponiveis()) == 9:
//            return 4
//            for move in tabuleiro.movimentos_disponiveis():
//            tabuleiro.marca_movimento(move, player)
//    val = tabuleiro.minimax(tabuleiro, define_oponente(player))
//            tabuleiro.marca_movimento(move, 0)
//            if val > a:
//    a = val
//    escolhas = [move]
//    elif val == a:
//            escolhas.append(move)
//            try:
//            return random.choice(escolhas)
//    except IndexError:
//            return random.choice(tabuleiro.movimentos_disponiveis())

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
