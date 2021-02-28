/////////////////////////////////////////////////////////////////////////////////////////
// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант С: Проверить, является ли формула СКНФ
// Выполнена студентом грруппы 821701 БГУИР Клевцевич Александр Владимирович
// Класс предназначен для создания таблицы истинности

package parser;

public class TruthTable {
    private final int countElements;
    private final int countRows;
    private final int[][] table;

    public TruthTable(int n) {
        countElements = n;
        countRows = (int) Math.pow(2, n);
        table = new int[countRows][n + 1];
        createTable(n);
    }

    private void createTable(int n) {
        for (int i = 0; i < countRows; i++) {
            for (int j = n - 1; j >= 0; j--) {
                table[i][j] = (i / (int) Math.pow(2, j)) % 2;
            }
        }
    }

    public int[][] getTable() {
        return table;
    }

    public int[] getRow(int i) {
        return table[i];
    }

    public int getCountRows() {
        return countRows;
    }

    public void setValueRow(int i, boolean value) {
        table[i][countElements] = value ? 1 : 0;
    }

    public void output() {
        for (int i = 0; i < countRows; i++) {
            for (int j = 0; j <= countElements; j++) {
                System.out.print(table[i][j] + " ");
            }
            System.out.println();
        }
    }
}