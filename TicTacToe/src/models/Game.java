package models;

import exceptions.DuplicateSymbolFoundException;
import exceptions.InvalidBotCountException;
import strategy.gameWinningStrategy.ColumnWinningStrategy;
import strategy.gameWinningStrategy.DiagonalWinningStrategy;
import strategy.gameWinningStrategy.GameWinningStrategy;
import strategy.gameWinningStrategy.RowWinningStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {

    private  Player[] players;
    private  Board board;
    private GameState gameState;
    private int currentPlayerIndex;
    private Player winner;
    private List<Move> moves;
    private List<GameWinningStrategy> winningStrategies;

    public Game(int dimensionOfBoard, Player[] players, List<GameWinningStrategy> winningStrategies) {
        this.players = players;
        this.board = new Board(dimensionOfBoard);
        this.gameState = GameState.IN_PROGRESS;
        this.currentPlayerIndex = 0;
        this.moves = new ArrayList<>();
        this.winningStrategies = winningStrategies;
    }

    public List<GameWinningStrategy> getWinningStrategies() {
        return winningStrategies;
    }

    public void setWinningStrategies(List<GameWinningStrategy> winningStrategies) {
        this.winningStrategies = winningStrategies;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    public void printBoard() {
        board.print();
    }

    private boolean validateMove(Move move, Board board) {
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();

        Cell[][] Board = board.getBoard();
        Cell cell = Board[row][col];

        return row >= 0  && row < board.getSize() &&
                col >= 0 && col < board.getSize() &&
                cell.isEmpty();
    }

    private boolean checkWinner(Board board, Move move) {
        ///Check all the game winning strategies.
        for (GameWinningStrategy winningStrategy : winningStrategies) {
            if (winningStrategy.checkWinner(board, move)) return true;
        }

        return false;
    }

    public void makeMove(Game game) {
        Player currentPlayer = players[currentPlayerIndex];
        System.out.println("It is " + currentPlayer.getName() + " 's move");
        Move move = currentPlayer.makeMove(board);

        //validate the Move.
        if (!validateMove(move, board)) {
            System.out.println("Invalid move");
            return;
        }

        //Place the move on the board.
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();

        Cell[][] Board = board.getBoard();
        Cell cell = Board[row][col];
        cell.setPlayer(currentPlayer);
        cell.setCellState(CellState.FILLED);
        Move finalMove = new Move(currentPlayer, cell);
        moves.add(finalMove);

        currentPlayerIndex += 1;
        currentPlayerIndex %= players.length;

        // check the winner
        if (checkWinner(board, finalMove)) {
            gameState = GameState.ENDED;
            winner = currentPlayer;
        } else if (moves.size() == board.getSize() * board.getSize()) {
            gameState = GameState.DRAW;
        }
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {
        private  Player[] players;
        private  int dimension;

        public Player[] getPlayers() {
            return players;
        }

        public Builder setPlayers(Player[] players) {
            this.players = players;
            return this;
        }

        public int getDimension() {
            return dimension;
        }

        public Builder setDimension(int dimension) {
            this.dimension = dimension;
            return this;
        }

        private void validateUniqueSymbols() throws DuplicateSymbolFoundException {
            Map<Symbol, Integer> symbolCount = new HashMap<>();

            for (Player player : players) {
                if (!symbolCount.containsKey(player.getSymbol())) {
                    symbolCount.put(player.getSymbol(), 1);
                } else {
                    symbolCount.put(player.getSymbol(),
                            symbolCount.get(player.getSymbol()) + 1);
                }

                if (symbolCount.get(player.getSymbol()) > 1) {
                    throw new DuplicateSymbolFoundException("Players can't have duplicate symbols.");
                }
            }
        }

        private void validateBotCount() throws InvalidBotCountException {
            int botCount = 0;

            for (Player player : players) {
                if (player.getPlayerType().equals(PlayerType.BOT)) {
                    botCount++;
                }

                if (botCount > 1) {
                    throw new InvalidBotCountException("Only 1 bot is allowed per game.");
                }
            }
        }

        private void validateGame(int dimension, Player[] players) throws DuplicateSymbolFoundException, InvalidBotCountException {
            validateUniqueSymbols();
            validateBotCount();
        }

        public Game Build() throws InvalidBotCountException, DuplicateSymbolFoundException {

            // TODO: Validation
            validateGame(dimension, players);

            return new Game(
                    dimension,
                    players,
                    List.of(
                            new RowWinningStrategy(),
                            new ColumnWinningStrategy(),
                            new DiagonalWinningStrategy()
                    )
            );
        }
    }
}
