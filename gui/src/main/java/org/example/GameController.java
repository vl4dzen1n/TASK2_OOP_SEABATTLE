package org.example;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import models.*;

/**
 * Контроллер графического интерфейса для игры "Морской бой".
 * Управляет фазой расстановки кораблей, игровым процессом и отображением полей.
 */
public class GameController {

    private final BorderPane root = new BorderPane();
    private final Game game = new Game("Игрок 1", "Игрок 2");
    private BoardView boardView;
    private boolean vertical = true;
    private final int[] shipSizes = {4,3,3,2,2,2,1,1,1,1};
    private int shipIndex = 0;

    /**
     * Создаёт контроллер, запускает игру и отображает фазу расстановки кораблей.
     */
    public GameController() {
        game.startGame();
        showSetupScreen();
    }

    /**
     * Возвращает корневой layout интерфейса для отображения в сцене.
     *
     * @return BorderPane — корневой элемент GUI
     */
    public Parent getRoot() {
        return root;
    }

    /**
     * Отображает экран расстановки кораблей для текущего игрока.
     */
    private void showSetupScreen() {
        shipIndex = 0;
        Player player = game.getCurrentPlayer();

        Label title = new Label("Расстановка кораблей: " + player.getName());
        Button rotate = new Button("Повернуть (V/H)");
        rotate.setOnAction(e -> vertical = !vertical);

        boardView = new BoardView(player.getBoard(), false);
        boardView.setOnCellClick(this::placeShip);

        VBox top = new VBox(10, title, rotate);
        top.setAlignment(Pos.CENTER);

        root.setTop(top);
        root.setCenter(boardView);
    }

    /**
     * Размещает корабль на поле по координатам игрока.
     *
     * @param x координата строки
     * @param y координата столбца
     */
    private void placeShip(int x, int y) {
        if (shipIndex >= shipSizes.length) return;

        Ship ship = new Ship(shipSizes[shipIndex]);
        Board board = game.getCurrentPlayer().getBoard();

        if (board.placeShip(ship, x, y, vertical)) {
            shipIndex++;
            boardView.refresh(true);

            if (shipIndex == shipSizes.length) {
                nextSetupPhase();
            }
        }
    }

    /**
     * Переходит к следующей фазе игры:
     * расстановка второго игрока или начало игрового процесса.
     */
    private void nextSetupPhase() {
        if (game.getState() == Game.GameState.SETUP_PLAYER1) {
            game.setState(Game.GameState.SETUP_PLAYER2);
            game.switchPlayer();
            showSetupScreen();
        } else {
            game.setState(Game.GameState.PLAYING);
            showGameScreen();
        }
    }

    /**
     * Отображает экран игрового процесса.
     */
    private void showGameScreen() {
        updateGameView();
    }

    /**
     * Обновляет вид игрового поля текущего игрока и противника.
     */
    private void updateGameView() {
        Player current = game.getCurrentPlayer();
        Player opponent = game.getOpponent();

        Label title = new Label("Ход игрока: " + current.getName());

        boardView = new BoardView(opponent.getBoard(), true);
        boardView.setOnCellClick((x, y) -> shoot(x, y));

        root.setTop(title);
        root.setCenter(boardView);
    }

    /**
     * Обрабатывает выстрел по клетке.
     *
     * @param x координата строки
     * @param y координата столбца
     */
    private void shoot(int x, int y) {
        Player opponent = game.getOpponent();
        Board board = opponent.getBoard();

        Board.ShotResult result = board.receiveShot(x, y);

        if (result == Board.ShotResult.MISS) {
            game.switchPlayer();
        } else if (result == Board.ShotResult.SUNK) {
            if (game.checkWinCondition()) {
                showWinScreen();
                return;
            }
        }

        updateGameView();
    }

    /**
     * Отображает окно с информацией о победителе.
     */
    private void showWinScreen() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Игра окончена");
        alert.setHeaderText(null);
        alert.setContentText("Победил " + game.getWinner().getName());
        alert.showAndWait();
    }
}
