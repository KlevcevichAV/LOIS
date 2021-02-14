/////////////////////////////////////////////////////////////////////////////////////////
// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант С: Проверить, является ли формула СКНФ
// Выполнена студентом грруппы 821701 БГУИР Клевцевич Александр Владимирович
// Класс предназначен для создания таблицы истинности

package test;

public class TruthTable {
    private final int rows;
    private int[][] table;

    public TruthTable(int n) {
        rows = (int) Math.pow(2, n);
        table = new int[rows][n + 1];
        while (!createTable(n)) ;
    }

    private boolean createTable(int n) {
        boolean checkTrue = false;
        boolean checkFalse = false;
        for (int i = 0; i < rows; i++) {
            for (int j = n - 1; j >= 0; j--) {
                table[i][j] = (i / (int) Math.pow(2, j)) % 2;
            }
            table[i][n] = (int) (Math.random() * 2);
            if (table[i][n] == 0) {
                checkFalse = true;
            }
            if (table[i][n] == 0) {
                checkTrue = true;
            }
        }
        return checkFalse && checkTrue;
    }

    public int[][] getTable() {
        return table;
    }

    public int getRows() {
        return rows;
    }
}