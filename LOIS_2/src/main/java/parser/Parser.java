/////////////////////////////////////////////////////////////////////////////////////////
// Лабораторная работа №2 по дисциплине ЛОИС
// Вариант 8: Построить СКНФ для заданной формулы
// Выполнена студентом грруппы 821701 БГУИР Клевцевич Александр Владимирович
// Класс предназначен для парсинга формул

package parser;

import config.Config;

import java.util.*;

public class Parser {
    private final String EXPRESSION;

    private final ExpressionTree tree;
    private String message;

    private final Set<String> ELEMENTS;

    public Parser(String expression) throws SKNFException {
        this.EXPRESSION = expression;
        ELEMENTS = new HashSet<>();
        message = "";
        try {
            checkSymbols();
            checkBrackets();
            tree = new ExpressionTree(expression, this);
            message = "Good!";

        } catch (SKNFException sknfException) {
            throw new SKNFException(sknfException.getNumber());
        }
    }

    private void checkSymbols() throws SKNFException {
        if (EXPRESSION.length() == 1) {
            if (!Config.SYMBOLS.contains(EXPRESSION)) {
                throw new SKNFException(6);
            }
        }
        for (int i = 0; i < EXPRESSION.length(); i++) {
            if (!(Config.SYMBOLS.contains("" + EXPRESSION.charAt(i)) || Config.SIGNS.contains("" + EXPRESSION.charAt(i)))) {
                String sign = searchSign(EXPRESSION, i);
                if (!Config.SIGNS.contains(sign)) {
                    throw new SKNFException(6);
                } else {
                    if (sign.length() == 2) {
                        i++;
                    }
                }
            }
        }
    }

    private String searchSign(String expression, int pointer) {
        if (expression.charAt(pointer) == '!' || expression.charAt(pointer) == '~')
            return expression.charAt(pointer) + "";
        return "" + expression.charAt(pointer) + expression.charAt(pointer + 1);
    }

    private void checkBrackets() throws SKNFException {
        if (EXPRESSION.contains(")(")) {
            throw new SKNFException(3);
        }
        if (EXPRESSION.charAt(0) == ')') {
            throw new SKNFException(3);
        }
        if (EXPRESSION.charAt(0) != '(' && EXPRESSION.length() != 1) {
            throw new SKNFException(3);
        }
        if (EXPRESSION.charAt(0) == '(' && EXPRESSION.charAt(EXPRESSION.length() - 1) != ')') {
            throw new SKNFException(3);
        }
        int checkOpen = 0;
        int checkClose = 0;
        for (int i = 0; i < EXPRESSION.length(); i++) {
            if (EXPRESSION.charAt(i) == '(') {
                checkOpen++;
            } else if (EXPRESSION.charAt(i) == ')') {
                checkClose++;
            }
        }
        if (checkOpen > checkClose) {
            throw new SKNFException(1);
        }
        if (checkClose > checkOpen) {
            throw new SKNFException(2);
        }
    }

    public String getMessage() {
        return message;
    }

    public void addElements(String element) {
        ELEMENTS.add(element);
    }

    public Set<String> getELEMENTS() {
        return ELEMENTS;
    }

    public ExpressionTree getTree() {
        return tree;
    }
}
