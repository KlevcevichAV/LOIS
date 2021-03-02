/////////////////////////////////////////////////////////////////////////////////////////
// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант С: Проверить, является ли формула СКНФ
// Выполнена студентом грруппы 821701 БГУИР Клевцевич Александр Владимирович
// Класс предназначен для проверки формулы и для проверки знаний пользователя

import config.Config;
import parser.Formula;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IOException {
        String expression = "";
        StringBuilder builder = new StringBuilder(expression);
        try (FileInputStream fin = new FileInputStream(Config.IN_FILE_PATH)) {
            while (fin.available() > 0) {
                int oneByte = fin.read();
                builder.append(((char) oneByte));
            }
            expression = builder.toString();
        } catch (FileNotFoundException ex) {
            System.out.println("File not find!!!");
        }

        try(FileWriter writer = new FileWriter(Config.OUT_FILE_PATH, true))
        {
            System.out.println(expression);
            System.out.println();
            writer.write(expression);
            writer.write("\n");

            Formula formula = new Formula(expression);
            if(formula.isResult()){
                System.out.println(formula.getResultParser());
                System.out.println("\n");
                writer.write(formula.getResultParser() + "\n");
                writer.write("\n");
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
