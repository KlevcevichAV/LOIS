/////////////////////////////////////////////////////////////////////////////////////////
// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант С: Проверить, является ли формула СКНФ
// Выполнена студентом грруппы 821701 БГУИР Клевцевич Александр Владимирович
// Класс предназначен для указания файла с формулой для проверки

package config;

public class Config {

    public static final String FILE_NAME = "1";
    public static final String FILE_FORMAT = "txt";
    public static final String IN_FILE_PATH = System.getProperty("user.dir") + "/files/in/" + FILE_NAME + "." + FILE_FORMAT;
}
