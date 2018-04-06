package com.rockingstar.engine.game;

import javafx.scene.Node;

/**
 * Created by Bert de Boer on 4/3/2018.
 */
public abstract class AbstractGame implements GameInterface {

    protected State currentState;

    protected Player player1;
    protected Player player2;
    protected Player currentPlayer;

    /**
     * State code for the current move.
     * -1   Invalid move
     * 0    No move has been made yet
     * 1    Valid move
     */
    protected int moveDetails;

    /**
     * The player's result
     * 0    Draw
     * 1    Won
     */
    protected int playerResult;

    public AbstractGame(Player player1, Player player2) {
        currentState = State.PREGAME;
        this.player1 = player1;
        this.player2 = player2;

        currentPlayer = player1;
    }

    @Override
    public abstract Node getView();

    @Override
    public void setGameState(State newGameState) {
        currentState = newGameState;
    }

    @Override
    public void startGame() {
        currentState = State.GAME_STARTED;
    }

    @Override
    public void doPlayerMove(int x, int y) throws IllegalStateException {
        if (currentState != State.GAME_STARTED)
            throw new IllegalStateException();
    }

    @Override
    public void doPlayerMove(int position) throws IllegalStateException {
        if (currentState != State.GAME_STARTED)
            throw new IllegalStateException();
    }

    @Override
    public Player getPlayerToMove() throws IllegalStateException {
        if (currentState != State.GAME_STARTED)
            throw new IllegalStateException();

        return currentPlayer;
    }

    @Override
    public String getTurnMessage(Player player) {
        return null;
    }

    @Override
    public final State getGameState() {
        return currentState;
    }

    @Override
    public int getPlayerScore(Player player) {
        return player.getScore();
    }

    @Override
    public int getPlayerResult() throws IllegalStateException {
        if (currentState != State.GAME_FINISHED)
            throw new IllegalStateException();

        return playerResult;
    }

    @Override
    public final int getMoveDetails() {
        return moveDetails;
    }

    @Override
    public String getMatchResultComment() {
        return null;
    }

    public void setPlayer1(Player player) {
        player1 = player;
    }

    public void setPlayer2(Player player) {
        player2 = player;
    }

    public void setCurrentPlayer(String name) {
        currentPlayer = player1.getUsername().equals(name) ? player1 : player2;
    }

    public void setCurrentPlayer(int id) {
        currentPlayer = id == 0 ? player1 : player2;
    }

    public void switchPlayers() {
        currentPlayer = currentPlayer == player1 ? player2 : player1;
    }
}
