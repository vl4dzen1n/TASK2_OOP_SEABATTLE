package org.example;

import javafx.scene.control.Button;
import models.Cell;

/**
 * Кнопка, представляющая одну клетку игрового поля.
 * Содержит координаты и метод обновления состояния.
 */
public class CellButton extends Button {

    private final int x, y;

    /**
     * Создаёт кнопку для клетки с координатами x и y.
     *
     * @param x координата строки
     * @param y координата столбца
     */
    public CellButton(int x, int y) {
        this.x = x;
        this.y = y;
        setMinSize(45, 45);
    }

    /**
     * Обновляет визуальное состояние кнопки в зависимости от состояния клетки.
     *
     * @param cell     клетка игрового поля
     * @param showShip показывать корабль или нет
     */
    public void update(Cell cell, boolean showShip) {
        setText("");

        if (cell.getState() == Cell.CellState.MISS) {
            setStyle("-fx-background-color: lightgray;");
        } else if (cell.getState() == Cell.CellState.HIT) {
            setStyle("-fx-background-color: red;");
            if (cell.getShip() != null && cell.getShip().isSunk()) {
                setText("X");
            }
        } else if (showShip && cell.hasShip()) {
            setStyle("-fx-background-color: lightblue;");
        } else {
            setStyle("-fx-background-color: white;");
        }
    }
}
