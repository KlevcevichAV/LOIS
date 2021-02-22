/////////////////////////////////////////////////////////////////////////////////////////
// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант С: Проверить, является ли формула СКНФ
// Выполнена студентом грруппы 821701 БГУИР Клевцевич Александр Владимирович
// Класс предназначен для проведения теста знаний пользователя

package test;

import parser.Constant;
import parser.Parser;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Test {
    private List<String> test;
    private final int COUNT_QUESTIONS = 10;

    public Test() {
        test = new ArrayList<>();
        for (int i = 0; i < COUNT_QUESTIONS; i++) {
            generateFormula();
        }
    }

    private void generateFormula() {
        int countElements = 2 + (int) (Math.random() * 2);
        TruthTable truthTable = new TruthTable(countElements);
        StringBuilder builder = new StringBuilder("");
        for (int i = 0; i < truthTable.getCountCon() - 1; i++){
            builder.append("(");
        }
        int count = 0;
        for (int j = 0; j < truthTable.getRows(); j++) {
            if (truthTable.getTable()[j][countElements] == 0) {
                builder.append(createAtom(countElements, truthTable.getTable()[j]));
                if(count != 0){
                    builder.append(")");
                }
                builder.append("&");
                count++;
            }
        }
        builder.deleteCharAt(builder.length() - 1);
        int checkTrue = (int) (Math.random() * 2);
        if ((checkTrue == 0)) {
            test.add(builder.toString());
        } else {
            test.add(makeError(builder.toString(), countElements));
        }
    }

    private String createAtom(int countElements, int[] rowTruthTable) {
        StringBuilder atom = new StringBuilder();
        for (int i = 0; i < countElements - 1; i++){
            atom.append("(");
        }
        int count = 0;
        for (int i = 0; i < countElements; i++) {
            atom.append((rowTruthTable[i] == 0) ? Constant.SYMBOLS.get(i) : ("(!" + Constant.SYMBOLS.get(i) + ")"));
            if(count != 0){
                atom.append(")");
            }
            atom.append("|");
            count++;
        }
        atom.setLength(atom.length() - 1);
        return atom.toString();
    }

    private String makeError(String expression, int countElements) {
        int typeError = (int) (Math.random() * 3);
        switch (typeError) {
            case 0: {
                int countConjunction = count(expression, "&");
                int changeConjunction = 1 + (int) (Math.random() * countConjunction);
                return changeSign(expression, '&', changeConjunction);
            }
            case 1: {
                int countDisjunction = count(expression, "|");
                int changeDisjunction = 1 + (int) (Math.random() * countDisjunction);
                return changeSign(expression, '|', changeDisjunction);
            }
            case 2: {
                return findDeletedSymbol(countElements, expression);
            }
            default:
                return "";
        }
    }

    private String findDeletedSymbol(int countElements, String expression) {
        int counterSymbols = 0;
        for (int i = 0; i < countElements; i++) {
            for (int j = 0; j < expression.length(); j++) {
                if (Constant.SYMBOLS.contains("" + expression.charAt(j))) {
                    counterSymbols++;
                }
            }
        }
        int pointerDeletedSymbol = 1 + (int) (Math.random() * counterSymbols);
        counterSymbols = 0;
        for (int i = 0; i < countElements; i++) {
            for (int j = 0; j < expression.length(); j++) {
                if (Constant.SYMBOLS.contains("" + expression.charAt(j))) {
                    if (counterSymbols == pointerDeletedSymbol - 1) {
                        StringBuilder builder = new StringBuilder(expression);
                        if (j == 0) {
                            builder.delete(j, 2);
                        } else {
                            if (expression.charAt(j - 1) == '!') {
                                builder.delete(((expression.charAt(j - 2) == '(') ? (j - 1) : (j - 2)), ((expression.charAt(j - 2) == '(') ? (j + 2) : (j + 1)));
                            } else {
                                builder.delete(((expression.charAt(j - 1) == '(') ? j : (j - 1)), ((expression.charAt(j - 1) == '(') ? (j + 2) : (j + 1)));
                            }
                        }
                        return builder.toString();
                    } else {
                        counterSymbols++;
                    }
                }
            }
        }
        return expression;
    }

    public void run() {
        int result = 0;
        for (int i = 0; i < test.size(); i++) {
            System.out.println("\t" + (i + 1) + ". " + test.get(i));
            System.out.print("1. Yes;\n2. No.\nYour choice: ");
            Scanner in = new Scanner(System.in);
            try {
                int num = in.nextInt();
                boolean choice = num == 1;
                Parser parser = new Parser(test.get(i));
                if (choice == parser.getResult()) {
                    System.out.println("Correctly!");
                    result++;
                } else {
                    System.out.println("Wrong!");
                    System.out.println(parser.getMessage() + "\n");
                }
            } catch (InputMismatchException e) {
                i--;
                System.out.println("Please enter correct data!");
            }
        }

        System.out.printf("Your result: %s(%d of %d)", result, result, test.size());
    }

    private int count(String str, String target) {
        return (str.length() - str.replace(target, "").length()) / target.length();
    }

    private String changeSign(String expression, char character, int position) {
        StringBuilder builder = new StringBuilder(expression);
        char newCharacter = (character == '&') ? '|' : '&';
        int number = findSignForCount(expression, character, position);
        if (number != -1) {
            builder.setCharAt(number, newCharacter);
        }
        return builder.toString();
    }

    private int findSignForCount(String expression, char sign, int counter) {
        int tempCount = 0;
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == sign) {
                tempCount++;
            }
            if (tempCount == counter) {
                return i;
            }
        }
        return -1;
    }
}
