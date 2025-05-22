import controllers.GameController;
import exceptions.DuplicateSymbolFoundException;
import exceptions.InvalidBotCountException;
import models.*;

public class TicTacToeDemo {

    public static void main(String[] args) throws InvalidBotCountException, DuplicateSymbolFoundException {

        GameController gameController = new GameController();

        // Play game
        Player player1 = new Player(1, "Player 1", Symbol.X, PlayerType.HUMAN);
        Player player2 = new Player(2, "Player 2", Symbol.O, PlayerType.HUMAN);
        Player[] players = new Player[] {player1, player2};
        Game game = gameController.startGame(3, players);

        while (gameController.getGameState(game).equals(GameState.IN_PROGRESS)) {
            gameController.printBoard(game);
            gameController.makeMove(game);
        }

        if (gameController.getGameState(game).equals(GameState.DRAW)) {
            gameController.printBoard(game);
            System.out.println("Game has DRAWN.");
        } else {
            //Someone has WON the game.
            gameController.printBoard(game);
            System.out.println(gameController.getWinner(game).getName() + " has WON the game. Congratulations.");
        }

    }
}
