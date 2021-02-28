/////////////////////////////////////////////////////////////////////////////////////////
// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант С: Проверить, является ли формула СКНФ
// Выполнена студентом грруппы 821701 БГУИР Клевцевич Александр Владимирович
// Класс предназначен для парсинга формул

package parser;


import static config.Config.*;

import java.util.*;

public class Parser {
    private final String EXPRESSION;

    private ExpressionTree tree;
    private boolean result;
    private String message;

    private TruthTable truthTable;

    private final Set<String> ELEMENTS;

    public Parser(String expression) {
        this.EXPRESSION = expression;
        ELEMENTS = new HashSet<>();
        result = false;
        message = "";
        try {
            checkFormula(expression);
            result = true;
            message = "Good!";
        } catch (SKNFException SKNFException) {
            message = SKNFException.getMessage();
        }
    }

    private void checkFormula(String expression) throws SKNFException {
        checkSymbols();
        checkBrackets();
    }

    private void checkSymbols() throws SKNFException {
        for (int i = 0; i < EXPRESSION.length(); i++) {
            if (!(SYMBOLS.contains("" + EXPRESSION.charAt(i)) || SIGNS.contains("" + EXPRESSION.charAt(i)))) {
                String sign = searchSign(EXPRESSION, i);
                if (!SIGNS.contains(sign)) {
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

    public boolean getResult() {
        return result;
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
}
