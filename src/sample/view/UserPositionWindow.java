package sample.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static sample.logic.Field.f1;
import static sample.logic.Field.f2;
import static sample.logic.Field.f3;
import static sample.logic.Field.f4;
import static sample.logic.Field.f5;
import static sample.logic.Field.f6;
import static sample.logic.Field.f7;
import static sample.logic.Field.f8;
import static sample.logic.Field.f9;
import static sample.logic.Field.f10;
import static sample.logic.Field.f11;
import static sample.logic.Field.f12;
import static sample.logic.Field.f13;
import static sample.logic.Field.f14;
import static sample.logic.Field.f15;
import static sample.logic.Field.f16;

public class UserPositionWindow {
    private static boolean checkingSymbolsOK;
    public boolean checking_is_ok;

    @FXML
    private Text text;
    @FXML
    private Button okButton;
    @FXML
    public TextField t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16;

    @FXML
    void initialize() throws IOException {
        FileReader fileReader = new FileReader("userPosition.txt");
        Scanner scanner = new Scanner(fileReader);
        t1.setText(scanner.nextLine());
        t2.setText(scanner.nextLine());
        t3.setText(scanner.nextLine());
        t4.setText(scanner.nextLine());
        t5.setText(scanner.nextLine());
        t6.setText(scanner.nextLine());
        t7.setText(scanner.nextLine());
        t8.setText(scanner.nextLine());
        t9.setText(scanner.nextLine());
        t10.setText(scanner.nextLine());
        t11.setText(scanner.nextLine());
        t12.setText(scanner.nextLine());
        t13.setText(scanner.nextLine());
        t14.setText(scanner.nextLine());
        t15.setText(scanner.nextLine());
        t16.setText(scanner.nextLine());
    }

    @FXML
    private void back() throws IOException {
        try {
            f1 = Integer.parseInt(t1.getText());
            f2 = Integer.parseInt(t2.getText());
            f3 = Integer.parseInt(t3.getText());
            f4 = Integer.parseInt(t4.getText());
            f5 = Integer.parseInt(t5.getText());
            f6 = Integer.parseInt(t6.getText());
            f7 = Integer.parseInt(t7.getText());
            f8 = Integer.parseInt(t8.getText());
            f9 = Integer.parseInt(t9.getText());
            f10 = Integer.parseInt(t10.getText());
            f11 = Integer.parseInt(t11.getText());
            f12 = Integer.parseInt(t12.getText());
            f13 = Integer.parseInt(t13.getText());
            f14 = Integer.parseInt(t14.getText());
            f15 = Integer.parseInt(t15.getText());
            f16 = Integer.parseInt(t16.getText());
            check();
        } catch (NumberFormatException ignored) {
        }

        if (checkingSymbolsOK && checking_is_ok) {
            Stage window = (Stage) okButton.getScene().getWindow();
            window.close();
            MainWindow.userPositionWindowIsOpened = false;
            rewriteFile();
        } else {
            if (!checkingSymbolsOK) text.setText("Введите правильные значения");
            else text.setText("Поле неправильно перемешано");
        }
    }

    private void rewriteFile() throws IOException {
        FileWriter writer = new FileWriter("userPosition.txt", false);
        writer.write(f1 + "\n" + f2 + "\n" + f3 + "\n" + f4 + "\n" + f5 + "\n" + f6 + "\n" + f7 + "\n" + f8 + "\n" +
                f9 + "\n" + f10 + "\n" + f11 + "\n" + f12 + "\n" + f13 + "\n" + f14 + "\n" + f15 + "\n" + f16);
        writer.flush();
    }

    //проверка правильности введённого пользователем поля по формуле, описанной на http://pyatnashki.wmsite.ru/kombinacyi
    public void check() {
        Set<Integer> numbers = new HashSet<>(Arrays.asList(f1, f2, f3, f4, f5, f6, f7, f8,
                f9, f10, f11, f12, f13, f14, f15, f16));
        HashSet<Integer> numbers2 = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0));
        checkingSymbolsOK = numbers.containsAll(numbers2);

        int[][] winfield = {{f1, f5, f9, f13}, {f2, f6, f10, f14}, {f3, f7, f11, f15}, {f4, f8, f12, f16}};
        int counter = 0;
        int noY = 0;
        for (int i = 0; i < 16; i++) {
            for (int j = i + 1; j < 16; j++) {
                if (winfield[j % 4][j / 4] != 0) {
                    if (winfield[i % 4][i / 4] > winfield[j % 4][j / 4]) {
                        counter++;
                    }
                }
            }
        }
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                if (winfield[i][j] == 0) noY = j;
            }
        }
        checking_is_ok = (counter + noY) % 2 != 0;
    }

}
