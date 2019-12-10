package sample.logic;

import sample.view.MainWindow;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Field {
    public static int f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12, f13, f14, f15, f16;
    public int[][] field;
    public int zeroX;
    public int zeroY;
    private Random random = new Random();
    private Set<Integer> numbers = new HashSet<>();
    public boolean checking_is_ok = true;

    public Field() {
        if (MainWindow.gameIsUser) {
            field = new int[][]{{f1, f5, f9, f13}, {f2, f6, f10, f14}, {f3, f7, f11, f15}, {f4, f8, f12, f16}};
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (field[i][j]==0) {
                        zeroY = j;
                        zeroX = i;
                    }
                }
            }
        }
        else {
            field = new int[4][4];
            fill();
        }
        MainWindow.gameIsUser=false;
    }

    public void fill() {
        numbers.clear();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                boolean numbersContainsC = false;
                int value = 0;
                while (!numbersContainsC) {
                    value = random.nextInt(16);
                    if (!numbers.contains(value)) {
                        numbers.add(value);
                        numbersContainsC = true;
                    }
                }
                if (value == 0) {
                    zeroX = i;
                    zeroY = j;
                }
                field[i][j] = value;
            }
        }
        checkWinnable();
        if (!checking_is_ok) fill();
    }

    //проверка решаемости - по формуле, описанной на http://pyatnashki.wmsite.ru/kombinacyi
    public void checkWinnable() {
        checking_is_ok = true;
        int counter = 0;
        for (int i = 0; i < 16; i++) {
            for (int j = i + 1; j < 16; j++) {
                if (field[j % 4][j / 4] != 0)
                    if (field[i % 4][i / 4] > field[j % 4][j / 4]) {
                        counter++;
                    }
            }
        }
        if ((counter + zeroY ) % 2 == 0) checking_is_ok = false;
    }

    /*private void tostring() {
        StringBuilder s = new StringBuilder();
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                s.append(field[i][j]);
                s.append(", ");
            }
            s.append("\n");
        }
        System.out.println(s);
    }*/
}
