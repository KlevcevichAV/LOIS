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
    private final List<String> test;
    private final int COUNT_QUESTIONS = 10;
    private final String MAIN_SIGN = "/\\";
    private final String SIGN = "\\/";
    public Test() {
        test = new ArrayList<>();
        for (int i = 0; i < COUNT_QUESTIONS; i++) {
            generateFormula();
        }
    }

    private void generateFormula() {
        int countElements = 2 + (int) (Math.random() * 2);
        TruthTable truthTable = new TruthTable(countElements);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < truthTable.getCountCon() - 1; i++) {
            builder.append("(");
        }
        int count = 0;
        for (int j = 0; j < truthTable.getRows(); j++) {
            if (truthTable.getTable()[j][countElements] == 0) {
                builder.append(createAtom(countElements, truthTable.getTable()[j]));
                if (count != 0) {
                    builder.append(")");
                }
                builder.append(MAIN_SIGN);
                count++;
            }
        }
        builder.setLength(builder.length() - 2);
        int checkTrue = (int) (Math.random() * 100);
        if ((checkTrue % 2 == 0)) {
            test.add(builder.toString());
        } else {
            String wrongExpression = makeError(builder.toString(), countElements);
            test.add(wrongExpression);
        }
    }

    private String createAtom(int countElements, int[] rowTruthTable) {
        StringBuilder atom = new StringBuilder();
        for (int i = 0; i < countElements - 1; i++) {
            atom.append("(");
        }
        int count = 0;
        for (int i = 0; i < countElements; i++) {
            atom.append((rowTruthTable[i] == 0) ? Constant.SYMBOLS.get(i) : ("(!" + Constant.SYMBOLS.get(i) + ")"));
            if (count != 0) {
                atom.append(")");
            }
            atom.append(SIGN);
            count++;
        }
        atom.setLength(atom.length() - 2);
        return atom.toString();
    }

    private String makeError(String expression, int countElements) {
        int typeError = (int) (Math.random() * 3);
        switch (typeError) {
//            change con on dis
            case 0: {
                int countConjunction = count(expression, MAIN_SIGN);
                int changeConjunction = 1 + (int) (Math.random() * countConjunction);
                return changeSign(expression, MAIN_SIGN, changeConjunction);
            }
//            change con on dis
            case 1: {
                int countDisjunction = count(expression, SIGN);
                int changeDisjunction = 1 + (int) (Math.random() * countDisjunction);
                return changeSign(expression, SIGN, changeDisjunction);
            }
//            delete brackets
            case 2: {
                return deleteBrackets(expression);
            }
            default:
                return "";
        }
    }

    private int count(String str, String target) {
        return (str.length() - str.replace(target, "").length()) / target.length();
    }

    private String changeSign(String expression, String character, int position) {
        String newCharacter = (MAIN_SIGN.equals(character)) ? SIGN : MAIN_SIGN;
        int positionSign = findSignForCount(expression, character, position);
        if (positionSign != -1) {
            return copy(expression, 0, positionSign) + newCharacter + copy(expression, positionSign + 2, expression.length());
        } else {
            positionSign = findSignForCount(expression, newCharacter, position);
            return copy(expression, 0, positionSign) + character + copy(expression, positionSign + 2, expression.length());
        }
    }

    private String deleteBrackets(String expression) {
        StringBuilder builder = new StringBuilder();
        int countOpenBrackets = countOpenBrackets(expression);
        int numberDeletedBracket = 1 + (int) (Math.random() * countOpenBrackets);
        int positionDeletedOpenBracket = searchPositionDeletedOpenBracket(expression, numberDeletedBracket);
        int positionDeletedClosedBracket = searchPositionDeletedClosedBracket(expression, positionDeletedOpenBracket);
        builder.append(copy(expression, 0, positionDeletedOpenBracket)).
                append(copy(expression, positionDeletedOpenBracket + 1, positionDeletedClosedBracket)).
                append(copy(expression, positionDeletedClosedBracket + 1, expression.length()));
        return builder.toString();
    }

    private int countOpenBrackets(String expression) {
        int count = 0;
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '(') {
                count++;
            }
        }
        return count;
    }

    private int searchPositionDeletedOpenBracket(String expression, int numberDeletedBrackets) {
        int count = 0;
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '(') {
                count++;
            }
            if(numberDeletedBrackets == count){
                return i;
            }
        }
        return -1;
    }

    private int searchPositionDeletedClosedBracket(String expression, int positionOpenClosed) {
        int check = 0;
        int saveCheck = -1;
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '(') {
                check++;
            }
            if(expression.charAt(i) == ')'){
                check--;
            }
            if(i == positionOpenClosed){
                saveCheck = check - 1;
            }
            if(saveCheck != -1 && check == saveCheck){
                return i;
            }
        }
        return -1;
    }

    private String copy(String expression, int start, int end) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = start; i < end; i++) {
            stringBuilder.append(expression.charAt(i));
        }
        return stringBuilder.toString();
    }

    private int findSignForCount(String expression, String sign, int counter) {
        int tempCount = 0;
        for (int i = 0; i < expression.length() - 1; i++) {
            if (sign.equals("" + expression.charAt(i) + expression.charAt(i + 1))) {
                tempCount++;
                if (tempCount == counter) {
                    return i;
                }
            }
        }
        return -1;
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

        System.out.printf("Your result: %d(%d of %d)", result, result, test.size());
    }
}
