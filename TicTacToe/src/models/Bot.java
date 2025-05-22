package models;

public class Bot extends Player {

    private BotDifficultyLevel botDifficultyLevel;

    public Bot(int id, String name, Symbol symbol) {
        super(id, name, symbol, PlayerType.BOT);
    }

    public BotDifficultyLevel getBotDifficultyLevel() {
        return botDifficultyLevel;
    }

    public void setBotDifficultyLevel(BotDifficultyLevel botDifficultyLevel) {
        this.botDifficultyLevel = botDifficultyLevel;
    }
}
