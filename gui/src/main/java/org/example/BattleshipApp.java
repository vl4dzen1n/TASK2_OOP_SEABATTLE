package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Главный класс JavaFX приложения для игры "Морской бой".
 * Отвечает за запуск графического интерфейса и отображение главного окна игры.
 */
public class BattleshipApp extends Application {

    /**
     * Метод запуска приложения.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Инициализация и отображение окна приложения.
     *
     * @param stage основная сцена приложения
     */
    @Override
    public void start(Stage stage) {
        GameController controller = new GameController();

        Scene scene = new Scene(controller.getRoot(), 600, 650);
        stage.setTitle("Морской бой");
        stage.setScene(scene);
        stage.show();
    }
}
