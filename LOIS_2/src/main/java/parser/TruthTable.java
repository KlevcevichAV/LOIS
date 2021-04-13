/////////////////////////////////////////////////////////////////////////////////////////
// Лабораторная работа №2 по дисциплине ЛОИС
// Вариант 8: Построить СКНФ для заданной формулы
// Выполнена студентом грруппы 821701 БГУИР Клевцевич Александр Владимирович
// Класс предназначен для создания таблицы истинности

package parser;

import java.util.List;

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

    public int getValueRow(int i) {
        return table[i][countElements];
    }

    public int getCountRows() {
        return countRows;
    }

    public void setValueRow(int i, boolean value) {
        table[i][countElements] = value ? 1 : 0;
    }

    public int countDis() {
        int result = 0;
        for (int i = 0; i < countRows; i++) {
            if (table[i][countElements] == 1) {
                result++;
            }
        }
        return result;
    }

    public void output(List<String> elements) {
        for (String element : elements) {
            System.out.print(element + " ");
        }
        System.out.println();
        for (int i = 0; i < countRows; i++) {
            for (int j = 0; j <= countElements; j++) {
                System.out.print(table[i][j] + " ");
            }
            System.out.println();
        }
    }

}