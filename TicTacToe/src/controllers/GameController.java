package controllers;

import exceptions.DuplicateSymbolFoundException;
import exceptions.InvalidBotCountException;
import models.Game;
import models.GameState;
import models.Player;

public class GameController {

    public Game startGame(int dimension, Player[] players) throws InvalidBotCountException, DuplicateSymbolFoundException {
        return Game.getBuilder().setDimension(dimension).setPlayers(players).Build();
    }

    public void printBoard(Game game) {
        game.printBoard();
    }

    public GameState getGameState(Game game) {
        return game.getGameState();
    }

    public void makeMove(Game game) {
        game.makeMove(game);
    }

    public Player getWinner(Game game) {
        return game.getWinner();
    }
}
