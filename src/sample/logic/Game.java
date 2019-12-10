package sample.logic;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import sample.view.MainWindow;

public class Game {
    public static Field field;

    public Game() {
        field = new Field();
    }

    public void keyPressed(KeyEvent keyEvent, Game game) {

        if (keyEvent.getCode().getName().equals("D") || keyEvent.getCode().equals(KeyCode.RIGHT)) {
            if (field.zeroX != 0) {
                moveRight();
                checkWin();
            }
        }
        if (keyEvent.getCode().getName().equals("S") || keyEvent.getCode().equals(KeyCode.DOWN)) {
            if (field.zeroY != 0) {
                moveDown();
                checkWin();
            }
        }
        if (keyEvent.getCode().getName().equals("A") || keyEvent.getCode().equals(KeyCode.LEFT)) {
            if (field.zeroX != 3) {
                moveLeft();
                checkWin();
            }
        }
        if (keyEvent.getCode().getName().equals("W") || keyEvent.getCode().equals(KeyCode.UP)) {
            if (field.zeroY != 3) {
                moveUp();
                checkWin();
            }
        }
        if (keyEvent.getCode().equals(KeyCode.SPACE)) {
            if (MainWindow.processIntervalBot) {
                MainWindow.processIntervalBot = false;
                MainWindow.isTextNull = true;
            }
        }
        if (keyEvent.getCode().getName().equals("Q")) {
            Solver solver = new Solver(game);
            solver.next();
        }
    }

     void moveRight() {
        field.field[field.zeroX][field.zeroY] = field.field[field.zeroX - 1][field.zeroY];
        field.field[field.zeroX - 1][field.zeroY] = 0;
        field.zeroX--;
    }

     void moveDown() {
        field.field[field.zeroX][field.zeroY] = field.field[field.zeroX][field.zeroY - 1];
        field.field[field.zeroX][field.zeroY - 1] = 0;
        field.zeroY--;
    }

     void moveLeft() {
        field.field[field.zeroX][field.zeroY] = field.field[field.zeroX + 1][field.zeroY];
        field.field[field.zeroX + 1][field.zeroY] = 0;
        field.zeroX++;
    }

     void moveUp() {
        field.field[field.zeroX][field.zeroY] = field.field[field.zeroX][field.zeroY + 1];
        field.field[field.zeroX][field.zeroY + 1] = 0;
        field.zeroY++;
    }

    private static int[][] winfield = {{1, 5, 9, 13}, {2, 6, 10, 14}, {3, 7, 11, 15}, {4, 8, 12, 0}};

    void checkWin() {
        int counter = 0;
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                if (winfield[i][j] == field.field[i][j]) counter++;
            }
        }
        MainWindow.win = counter == 16;
    }

}