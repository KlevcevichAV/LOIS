/////////////////////////////////////////////////////////////////////////////////////////
// Лабораторная работа №2 по дисциплине ЛОИС
// Вариант 9: Построить СДНФ для заданной формулы
// Выполнена студентом грруппы 821701 БГУИР Клевцевич Александр Владимирович
// Класс предназначен для проведения теста знаний пользователя

package test;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Test {
    private final List<Question> test;
    private final int COUNT_QUESTIONS = 10;

    public Test() {
        test = new ArrayList<>();
        for (int i = 0; i < COUNT_QUESTIONS; i++) {
            test.add(new Question());
        }
    }

    public void run() {
        int result = 0;
        for (int i = 0; i < COUNT_QUESTIONS; i++) {
            System.out.println("\t" + (i + 1) + ". " + test.get(i).getQuestionFormula());
            for (int j = 0; j < Question.AMOUNT_VARIANTS; j++) {
                System.out.println("\t\t" + (j + 1) + ". " + test.get(i).getVariants().get(j));
            }
            System.out.print("Your choice: ");
            Scanner in = new Scanner(System.in);
            try {
                int num = in.nextInt();
                if (test.get(i).check(num - 1)) {
                    System.out.println("Correctly!");
                    result++;
                } else {
                    System.out.println("Wrong!");
                    test.get(i).output();
                }
            } catch (InputMismatchException e) {
                i--;
                System.out.println("Please enter correct data!");
            }
        }

        System.out.printf("Your result: %d(%d of %d)", result, result, test.size());
    }
}
