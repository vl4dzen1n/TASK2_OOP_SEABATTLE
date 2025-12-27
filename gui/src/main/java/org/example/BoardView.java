package org.example;

import javafx.scene.layout.GridPane;
import models.Board;
import models.Cell;

import java.util.function.BiConsumer;

/**
 * Визуальное представление игрового поля 10x10.
 * Состоит из сетки CellButton и отображает состояние клеток.
 */
public class BoardView extends GridPane {

    private final CellButton[][] buttons = new CellButton[10][10];
    private final Board board;
    private BiConsumer<Integer, Integer> clickHandler;

    /**
     * Создаёт BoardView для заданного игрового поля.
     *
     * @param board     игровое поле, которое отображается
     * @param hideShips скрывать корабли (true для поля противника)
     */
    public BoardView(Board board, boolean hideShips) {
        this.board = board;
        setHgap(2);
        setVgap(2);

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {

                final int fx = x;
                final int fy = y;

                CellButton btn = new CellButton(fx, fy);
                buttons[fx][fy] = btn;
                add(btn, fy, fx);

                btn.setOnAction(e -> {
                    if (clickHandler != null) {
                        clickHandler.accept(fx, fy);
                    }
                });
            }
        }

        refresh(!hideShips);
    }

    /**
     * Устанавливает обработчик клика по клетке.
     *
     * @param handler BiConsumer с координатами x и y клетки
     */
    public void setOnCellClick(BiConsumer<Integer, Integer> handler) {
        this.clickHandler = handler;
    }

    /**
     * Обновляет визуальное состояние всех клеток.
     *
     * @param showShips показывать корабли или нет
     */
    public void refresh(boolean showShips) {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                Cell cell = board.getCells()[x][y];
                buttons[x][y].update(cell, showShips);
            }
        }
    }
}
