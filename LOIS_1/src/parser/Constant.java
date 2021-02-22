/////////////////////////////////////////////////////////////////////////////////////////
// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант С: Проверить, является ли формула СКНФ
// Выполнена студентом грруппы 821701 БГУИР Клевцевич Александр Владимирович
// Класс предназначен хранения алфавита и знаков

package parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constant {
    public static final List<String> SYMBOLS = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"));
    public static final List<String> SIGNS = new ArrayList<>(Arrays.asList("!", "/\\", "\\/", "(", ")", "->", "~"));
}
