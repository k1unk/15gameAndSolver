package sample.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.logic.Game;
import sample.logic.Solver;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class MainWindow {
    private Image image1 = new Image("sample/images/num1.jpg");
    private Image image2 = new Image("sample/images/num2.jpg");
    private Image image3 = new Image("sample/images/num3.jpg");
    private Image image4 = new Image("sample/images/num4.jpg");
    private Image image5 = new Image("sample/images/num5.jpg");
    private Image image6 = new Image("sample/images/num6.jpg");
    private Image image7 = new Image("sample/images/num7.jpg");
    private Image image8 = new Image("sample/images/num8.jpg");
    private Image image9 = new Image("sample/images/num9.jpg");
    private Image image10 = new Image("sample/images/num10.jpg");
    private Image image11 = new Image("sample/images/num11.jpg");
    private Image image12 = new Image("sample/images/num12.jpg");
    private Image image13 = new Image("sample/images/num13.jpg");
    private Image image14 = new Image("sample/images/num14.jpg");
    private Image image15 = new Image("sample/images/num15.jpg");

    private Game game = new Game();
    public static boolean isTextNull = false;
    public static boolean gameIsUser = false;
    public static boolean win = false;
    static boolean userPositionWindowIsOpened = false;
    private static boolean helpWindowOpened = false;
    public static boolean processIntervalBot = false;

    @FXML
    Button buttonMove;
    @FXML
    private ImageView gridBack;
    @FXML
    private ImageView im00, im10, im20, im30, im01, im11, im21, im31, im02, im12, im22, im32, im03, im13, im23, im33;
    @FXML
    Text text;

    @FXML
    void initialize() {
        Image background = new Image("sample/images/num0.jpg");
        gridBack.setImage(background);
        start();
    }

    private void keyPressed(Game game) {
        buttonMove.setOnKeyPressed(ke ->
        {
            game.keyPressed(ke, game);
            repaint();
        });
    }

    public void startRandomGame() {
        start();
    }

    private void start() {
        gameIsUser = false;
        win = false;
        game = new Game();

        keyPressed(game);
        repaint();
    }

    public void selectUser() throws IOException {
        if (!userPositionWindowIsOpened) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("userPosition.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("");
            stage.setScene(new Scene(root, 370, 400));
            stage.show();

            stage.setOnCloseRequest(event -> userPositionWindowIsOpened = false);
            userPositionWindowIsOpened = true;
        }
        gameIsUser = true;
    }

    public void startUsersPosition() throws IOException {
        start();

        FileReader fileReader = new FileReader("userPosition.txt");
        Scanner scanner = new Scanner(fileReader);
        int value;
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                value = Integer.parseInt(scanner.nextLine());
                Game.field.field[i][j] = value;
                if (value == 0) {
                    Game.field.zeroX = i;
                    Game.field.zeroY = j;
                }
            }
        }

        repaint();
    }

    public void help() throws IOException {
        if (!helpWindowOpened) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("helpWindow.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Help");
            stage.setScene(new Scene(root, 600, 180));
            stage.show();

            stage.setOnCloseRequest(event -> helpWindowOpened = false);
            helpWindowOpened = true;
        }
    }

    private void botInterval(double speed) {
        Solver solver = new Solver(game);
        processIntervalBot = true;
        text.setText("Press SPACE to stop");
        solver.countBotMoves = 0;
        solver.botFindPath();
        solver.back();

        repaint();

        win = false;
        final boolean[] botNotInProcessOrWin = {false};
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(speed), e -> {
            if (isTextNull) text.setText("");

            if (!botNotInProcessOrWin[0]) {
                if (processIntervalBot) {
                    repaint();
                    solver.makeOneBotMove();
                } else {
                    botNotInProcessOrWin[0] = true;
                }
                if (win) {
                    repaint();
                    text.setText("");
                    botNotInProcessOrWin[0] = true;
                }
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    //функции, которые запускают решателя бота с заданным интервалом (0.1, 0.2, 0.4 или 1 секунда)
    public void start01() { botInterval(0.1); }
    public void start02() { botInterval(0.2); }
    public void start04() { botInterval(0.4); }
    public void start1()  { botInterval(1); }

    //функция, которая запускает решателя бота, который найдет только следующий ход
    public void next() {
        Solver solver = new Solver(game);
        solver.next();
        repaint();
    }

    //имена ImageView складываются из "im" + номер строки + номер столбца
    private void repaint() {
        im00.setImage(null);
        im01.setImage(null);
        im02.setImage(null);
        im03.setImage(null);
        im10.setImage(null);
        im11.setImage(null);
        im12.setImage(null);
        im13.setImage(null);
        im20.setImage(null);
        im21.setImage(null);
        im22.setImage(null);
        im23.setImage(null);
        im30.setImage(null);
        im31.setImage(null);
        im32.setImage(null);
        im33.setImage(null);

        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                for (int k = 1; k < 16; k++) {
                    if (Game.field.field[i][j] == k) {
                        whatImageView(i, j).setImage(whatImage(k));
                    }
                }
            }
        }
    }

    private Image whatImage(int number) {
        if (number == 1) return image1;
        if (number == 2) return image2;
        if (number == 3) return image3;
        if (number == 4) return image4;
        if (number == 5) return image5;
        if (number == 6) return image6;
        if (number == 7) return image7;
        if (number == 8) return image8;
        if (number == 9) return image9;
        if (number == 10) return image10;
        if (number == 11) return image11;
        if (number == 12) return image12;
        if (number == 13) return image13;
        if (number == 14) return image14;
        if (number == 15) return image15;
        return image1;
    }

    private ImageView whatImageView(int x, int y) {
        if (x == 0 && y == 0) return im00;
        if (x == 1 && y == 0) return im01;
        if (x == 2 && y == 0) return im02;
        if (x == 3 && y == 0) return im03;
        if (x == 0 && y == 1) return im10;
        if (x == 1 && y == 1) return im11;
        if (x == 2 && y == 1) return im12;
        if (x == 3 && y == 1) return im13;
        if (x == 0 && y == 2) return im20;
        if (x == 1 && y == 2) return im21;
        if (x == 2 && y == 2) return im22;
        if (x == 3 && y == 2) return im23;
        if (x == 0 && y == 3) return im30;
        if (x == 1 && y == 3) return im31;
        if (x == 2 && y == 3) return im32;
        if (x == 3 && y == 3) return im33;
        return im00;
    }
}
