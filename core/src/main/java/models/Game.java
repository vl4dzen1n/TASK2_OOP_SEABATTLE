package models;

/**
 * Класс игры.
 * Хранит игроков, текущий ход и состояние игры.
 * Управляет фазами расстановки кораблей и игровым процессом.
 */
public class Game {

    private final Player player1;
    private final Player player2;
    private Player currentPlayer;
    private GameState state;

    /**
     * Состояния игры.
     */
    public enum GameState {
        SETUP_PLAYER1,
        SETUP_PLAYER2,
        PLAYING,
        FINISHED
    }

    /**
     * Создаёт новую игру с двумя игроками.
     *
     * @param player1Name имя первого игрока
     * @param player2Name имя второго игрока
     */
    public Game(String player1Name, String player2Name) {
        this.player1 = new Player(player1Name);
        this.player2 = new Player(player2Name);
        this.currentPlayer = player1;
        this.state = GameState.SETUP_PLAYER1;
    }

    /**
     * Запускает игру и переводит её в начальное состояние.
     */
    public void startGame() {
        state = GameState.SETUP_PLAYER1;
        currentPlayer = player1;
    }

    /**
     * Переключает текущего игрока.
     */
    public void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    /**
     * Проверяет условие победы.
     *
     * @return true, если игра окончена и есть победитель
     */
    public boolean checkWinCondition() {
        Player opponent = getOpponent();
        if (opponent.getBoard().isAllShipsSunk()) {
            state = GameState.FINISHED;
            return true;
        }
        return false;
    }

    /**
     * Возвращает победителя игры.
     *
     * @return победивший игрок или null, если игра ещё не окончена
     */
    public Player getWinner() {
        if (state != GameState.FINISHED) {
            return null;
        }
        return player1.getBoard().isAllShipsSunk() ? player2 : player1;
    }

    /**
     * Возвращает противника текущего игрока.
     *
     * @return противник
     */
    public Player getOpponent() {
        return (currentPlayer == player1) ? player2 : player1;
    }

    /**
     * Устанавливает состояние игры.
     *
     * @param state новое состояние игры
     */
    public void setState(GameState state) {
        this.state = state;
    }

    /**
     * Возвращает первого игрока.
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     * Возвращает второго игрока.
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     * Возвращает текущего игрока.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Возвращает текущее состояние игры.
     */
    public GameState getState() {
        return state;
    }
}
