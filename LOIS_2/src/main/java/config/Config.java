/////////////////////////////////////////////////////////////////////////////////////////
// Лабораторная работа №2 по дисциплине ЛОИС
// Вариант 8: Построить СКНФ для заданной формулы
// Выполнена студентом грруппы 821701 БГУИР Клевцевич Александр Владимирович
// Класс предназначен для указания файла с формулой для проверки

package config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Config {

    public static final String FILE_NAME = "1";
    public static final String FILE_FORMAT = "txt";
    public static final String IN_FILE_PATH = System.getProperty("user.dir") + "/files/in/" + FILE_NAME + "." + FILE_FORMAT;
    public static final String OUT_FILE_PATH = System.getProperty("user.dir") + "/files/out/" + FILE_NAME + "." + FILE_FORMAT;
    public static final List<String> SYMBOLS = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z","0","1"));
    public static final List<String> SIGNS = new ArrayList<>(Arrays.asList("!", "/\\", "\\/", "(", ")", "->", "~"));
    public static final List<String> OPERATIONS = new ArrayList<>(Arrays.asList("/\\", "\\/", "->", "~"));
    public static final String CONJUNCTION = "/\\";
    public static final String DISJUNCTION = "\\/";
    public static final String NEGATION = "!";
    public static final String IMPLICATION = "->";
    public static final String EQUIVALENCE = "~";
}
