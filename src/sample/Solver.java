package sample;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

class Solver {

    private Game game;
    Solver(Game game) {
        this.game = game;
        Fifteen.isTextNull = false;
    }

    private static int[][] winfield = {{1, 5, 9, 13}, {2, 6, 10, 14}, {3, 7, 11, 15}, {4, 8, 12, 0}};

    // эвристическая функция, которая находит наилучшее положение для следующего хода
    // fx = hx * kf + gx,
    // где fx - самое значение функции,
    // gx - количество ходов, которые привели к данной позиции,
    // hx - сумма расстояний между расположением каждой из плиток и их финишной позиции,
    // kf - коэффициент, показывающий, насколько hx в данном алгоритме важнее gx
    // (чем меньше kf - тем дольше, но эффективнее происходит поиск пути)
    private double fx;
    private int hx;
    private int gx;
    private final double kf = 30;
    private static Field fieldBeforeBot = new Field();
    private List<int[][]> path;
    private static List<int[][]> pathReversed;
    private List<int[][]> opened;
    private List<Double> heuristicOfOpened;
    private List<int[][]> closed;
    private List<Integer> intArrayOpenedSize;
    private List<Integer> intArrayClosedSize;
    private int countWhiles;
    private int openedSize;
    private int closedSize;
    int countBotMoves = 0;

    void botFindPath() {
        opened = new ArrayList<>();
        heuristicOfOpened = new ArrayList<>();
        closed = new ArrayList<>();
        path = new ArrayList<>();
        pathReversed = new ArrayList<>();
        intArrayOpenedSize = new ArrayList<>();
        intArrayClosedSize = new ArrayList<>();
        countWhiles = 0;
        openedSize = 0;
        closedSize = 0;
        opened.add(Game.field.field);

        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                fieldBeforeBot.field[i][j] = Game.field.field[i][j];
            }
        }
        while (!opened.isEmpty()) {
            if (countWhiles % 1000 == 1) System.out.println("countWhiles = " + countWhiles);

            //check win
            int counter = 0;
            for (int j = 0; j < 4; j++) {
                for (int i = 0; i < 4; i++) {
                    if (winfield[i][j] == Game.field.field[i][j]) counter++;
                }
            }
            if (counter == 16) {
                boolean isSizeNotZero = true;
                int size = intArrayClosedSize.size();
                while (isSizeNotZero) {
                    if (closedSize != 0) path.add(closed.get(size));
                    if (size == 0) isSizeNotZero = false;
                    else size = intArrayClosedSize.get(size - 1);
                }
                System.out.println("PathSize = " + path.size());
                Fifteen.win = true;
                for (int j = 1; j < path.size(); j++) {
                    pathReversed.add(path.get(path.size() - j - 1));
                }
                return;
            }

            //find min in heuristic
            double min = Integer.MAX_VALUE;
            int minInArray = 0;
            if (openedSize != 0) {
                for (int j = 0; j < openedSize - 1; j++) {
                    if (heuristicOfOpened.get(j) < min) {
                        min = heuristicOfOpened.get(j);
                        minInArray = j;
                    }
                }
            }

            //field=best
            for (int j = 0; j < 4; j++) {
                for (int i = 0; i < 4; i++) {
                    Game.field.field[i][j] = opened.get(minInArray)[i][j];
                    if (Game.field.field[i][j] == 0) {
                        Game.field.zeroY = j;
                        Game.field.zeroX = i;
                    }
                }
            }

            //clear
            opened.remove(minInArray);
            if (openedSize != 0) {
                openedSize--;
                heuristicOfOpened.remove(minInArray);
            }

            //add closed
            int[][] fieldPosition = new int[4][4]; ///////////////////////////////
            for (int j = 0; j < 4; j++) {
                for (int i = 0; i < 4; i++) {
                    fieldPosition[i][j] = Game.field.field[i][j];
                }
            }
            closed.add(fieldPosition);
            closedSize++;
            if (openedSize != 0) {
                intArrayClosedSize.add(intArrayOpenedSize.get(minInArray));
                intArrayOpenedSize.remove(minInArray);
            }

            //neighbors
            if (Game.field.zeroY != 3) {
                game.moveUp();
                checkPosition();
                game.moveDown();
            }
            if (Game.field.zeroY != 0) {
                game.moveDown();
                checkPosition();
                game.moveUp();
            }
            if (Game.field.zeroX != 3) {
                game.moveLeft();
                checkPosition();
                game.moveRight();
            }
            if (Game.field.zeroX != 0) {
                game.moveRight();
                checkPosition();
                game.moveLeft();
            }
            countWhiles++;
        }
    }

    void next() {
        countBotMoves = 0;
        botFindPath();
        back();
        makeOneBotMove();
    }

    private void checkPosition() {
        //find gx
        gx=0;
        boolean isSizeNotZero = true;
        int size = intArrayClosedSize.size();
        while (isSizeNotZero) {
            if (closedSize != 0) gx++;
            if (size == 0) isSizeNotZero = false;
            else size = intArrayClosedSize.get(size - 1);
        }

        //check is it in closed
        boolean inClosed = false;
        for (int c = 0; c < closedSize; c++) {
            int counter = 0;
            for (int j = 0; j < 4; j++) {
                for (int i = 0; i < 4; i++) {
                    if (Game.field.field[i][j] == closed.get(c)[i][j]) {
                        counter++;
                    }
                }
            }
            if (counter == 16) {
                inClosed = true;
            }
        }

        if (!inClosed) {
            //add in opened and heuristic
            int[][] arr = new int[4][4];
            for (int j = 0; j < 4; j++) {
                for (int i = 0; i < 4; i++) {
                    arr[i][j] = Game.field.field[i][j];
                }
            }
            opened.add(arr);
            findH();
            fx = hx * kf + gx;
            heuristicOfOpened.add(fx);
            openedSize++;
            intArrayOpenedSize.add(closedSize - 1);
        }
    }

    void makeOneBotMove() {
        if (pathReversed.size() > countBotMoves) Game.field.field = pathReversed.get(countBotMoves);
        countBotMoves++;
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                if (Game.field.field[i][j] == 0) {
                    Game.field.zeroX = i;
                    Game.field.zeroY = j;
                }
            }
        }
        game.checkWin();
        if (Fifteen.win) {
            for (int j = 0; j < 4; j++) {
                for (int i = 0; i < 4; i++) {
                    Game.field.field[i][j] = winfield[i][j];
                }
            }
            Game.field.zeroX = 3;
            Game.field.zeroY = 3;
        }
    }

    void back() {
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                Game.field.field[i][j] = fieldBeforeBot.field[i][j];
                if (Game.field.field[i][j] == 0) {
                    Game.field.zeroX = i;
                    Game.field.zeroY = j;
                }
            }
        }
    }

    private void findH() {
        hx = 0;
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                for (int k = 1; k < 16; k++) {
                    findHContinue(i, j, k);
                }
            }
        }
    }

    private void findHContinue(int i, int j, int value) {
        if (value == Game.field.field[i][j]) {
            int row = value % 4;
            if (value % 4 == 0) {
                row = 4;
            }
            int column = 0;
            if (value % 4 == 0) {
                column = 1;
            }
            hx += (abs(i - row + 1) + abs(j - value / 4 + column));
        }
    }

}
