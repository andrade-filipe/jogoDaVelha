package org.university;

public class Main {
    public static void main(String[] args) {
        JogoDaVelha jogo = new JogoDaVelha();

//        jogo.doMove(JogoDaVelha.X_PLAYER, 8);
//        jogo.doMove(JogoDaVelha.O_PLAYER, 0);
//        jogo.refreshPossibleMoves();
//        jogo.refreshMarkedPositions();

        jogo.playComputerVsComputer();
    }
}