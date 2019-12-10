package test;

import sample.logic.Field;
import sample.view.UserPositionWindow;

import static org.junit.jupiter.api.Assertions.*;
import static sample.logic.Field.*;
import static sample.logic.Field.f16;

class Test {

    //проверка на то, является ли стартовое поле решаемым
    private Field field = new Field();
    @org.junit.jupiter.api.Test
    void startRandomPositionTest() {
        field.fill();
        field.checkWinnable();
        assertTrue(field.checking_is_ok);
    }

    //Показывает, что поле, заданное пользователем является решаемым
    private UserPositionWindow userPositionWindow = new UserPositionWindow();
    @org.junit.jupiter.api.Test
    void startUsersPositionTestTrue() {
        String t1 = "1";
        String t2 = "2";
        String t3 = "3";
        String t4 = "4";
        String t5 = "5";
        String t6 = "6";
        String t7 = "8";
        String t8 = "7";
        String t9 = "9";
        String t10 = "11";
        String t11 = "10";
        String t12 = "12";
        String t13 = "13";
        String t14 = "14";
        String t15 = "15";
        String t16 = "0";
        f1 = Integer.parseInt(t1);
        f2 = Integer.parseInt(t2);
        f3 = Integer.parseInt(t3);
        f4 = Integer.parseInt(t4);
        f5 = Integer.parseInt(t5);
        f6 = Integer.parseInt(t6);
        f7 = Integer.parseInt(t7);
        f8 = Integer.parseInt(t8);
        f9 = Integer.parseInt(t9);
        f10 = Integer.parseInt(t10);
        f11 = Integer.parseInt(t11);
        f12 = Integer.parseInt(t12);
        f13 = Integer.parseInt(t13);
        f14 = Integer.parseInt(t14);
        f15 = Integer.parseInt(t15);
        f16 = Integer.parseInt(t16);
        userPositionWindow.check();

        assertTrue(userPositionWindow.checking_is_ok);
    }

    //Показывает, что поле, заданное пользователем не является решаемым
    @org.junit.jupiter.api.Test
    void startUsersPositionTestFalse() {
        String t1 = "2";
        String t2 = "1";
        String t3 = "3";
        String t4 = "4";
        String t5 = "5";
        String t6 = "6";
        String t7 = "8";
        String t8 = "7";
        String t9 = "9";
        String t10 = "11";
        String t11 = "10";
        String t12 = "12";
        String t13 = "13";
        String t14 = "14";
        String t15 = "15";
        String t16 = "0";
        f1 = Integer.parseInt(t1);
        f2 = Integer.parseInt(t2);
        f3 = Integer.parseInt(t3);
        f4 = Integer.parseInt(t4);
        f5 = Integer.parseInt(t5);
        f6 = Integer.parseInt(t6);
        f7 = Integer.parseInt(t7);
        f8 = Integer.parseInt(t8);
        f9 = Integer.parseInt(t9);
        f10 = Integer.parseInt(t10);
        f11 = Integer.parseInt(t11);
        f12 = Integer.parseInt(t12);
        f13 = Integer.parseInt(t13);
        f14 = Integer.parseInt(t14);
        f15 = Integer.parseInt(t15);
        f16 = Integer.parseInt(t16);

        userPositionWindow.check();

        assertFalse(userPositionWindow.checking_is_ok);
    }

    //Показывает, что поле, заданное пользователем имеет неверные входные значения
    @org.junit.jupiter.api.Test
    void wrongValues() {
        String t1 = "2qqqqqq";
        String t2 = "1";
        String t3 = "3";
        String t4 = "4";
        String t5 = "5";
        String t6 = "6";
        String t7 = "8";
        String t8 = "7";
        String t9 = "9";
        String t10 = "11";
        String t11 = "10";
        String t12 = "12";
        String t13 = "13";
        String t14 = "14";
        String t15 = "15";
        String t16 = "0";

        try {
            f1 = Integer.parseInt(t1);
            f2 = Integer.parseInt(t2);
            f3 = Integer.parseInt(t3);
            f4 = Integer.parseInt(t4);
            f5 = Integer.parseInt(t5);
            f6 = Integer.parseInt(t6);
            f7 = Integer.parseInt(t7);
            f8 = Integer.parseInt(t8);
            f9 = Integer.parseInt(t9);
            f10 = Integer.parseInt(t10);
            f11 = Integer.parseInt(t11);
            f12 = Integer.parseInt(t12);
            f13 = Integer.parseInt(t13);
            f14 = Integer.parseInt(t14);
            f15 = Integer.parseInt(t15);
            f16 = Integer.parseInt(t16);

            userPositionWindow.check();

            assertFalse(userPositionWindow.checking_is_ok);
        } catch (Exception e) {
            System.out.println("Значения введены неверно");
        }

    }
}
