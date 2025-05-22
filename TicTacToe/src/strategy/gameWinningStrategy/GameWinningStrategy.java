package strategy.gameWinningStrategy;

import models.Board;
import models.Move;

public interface GameWinningStrategy {

    public boolean checkWinner(Board board, Move move);
}
