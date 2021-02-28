/////////////////////////////////////////////////////////////////////////////////////////
// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант С: Проверить, является ли формула СКНФ
// Выполнена студентом грруппы 821701 БГУИР Клевцевич Александр Владимирович
// Класс предназначен для парсинга формул

package parser;

import java.util.*;

public class Parser {
    private final String EXPRESSION;
    private final String MAIN_SIGN = "/\\";
    private final String SIGN = "\\/";

    private ExpressionTree tree;
    private boolean result;
    private String message;

    private final Set<String> ELEMENTS;
    private final List<String> ATOMS;

    public Parser(String expression) {
        this.EXPRESSION = expression;
        ELEMENTS = new HashSet<>();
        ATOMS = new ArrayList<>();
        result = false;
        message = "";
        try {
            checkSymbols();
            checkBrackets();
            tree = new ExpressionTree(expression, this);
            checkNegation(tree, 0);
            searchAtoms(tree);
            if (ATOMS.size() != ATOMS.stream().distinct().count()) {
                throw new SKNFException(9);
            }
            if (ATOMS.size() == 1) {
                int count = 0;
                for (int i = 0; i < ATOMS.get(0).length(); i++) {
                    if (ATOMS.get(0).charAt(i) == '|') {
                        count++;
                    }
                }
                if (count > 1) {
                    throw new SKNFException(4);
                }
            }
            checkAtomsForAllElements();
            checkAtomsForOperations();

            result = true;
            message = "Formula is SKNF!";

        } catch (SKNFException SKNFException) {
            message = SKNFException.getMessage();
        }
    }

    private void checkSymbols() throws SKNFException {
        for (int i = 0; i < EXPRESSION.length(); i++) {
            if (!(Constant.SYMBOLS.contains("" + EXPRESSION.charAt(i)) || Constant.SIGNS.contains("" + EXPRESSION.charAt(i)))) {
                String sign = searchSign(EXPRESSION, i);
                if (!Constant.SIGNS.contains(sign)) {
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

    private void searchAtoms(ExpressionTree tree) throws SKNFException {
        if (MAIN_SIGN.equals(tree.getOperation())) {
            searchAtoms(tree.getLeft());
            searchAtoms(tree.getRight());
        } else {
            ATOMS.add(tree.getExpression());
        }

    }

    private void checkNegation(ExpressionTree tree, int code) throws SKNFException {
        if (code == 0) {
            if ("!".equals(tree.getOperation())) {
                if (Objects.isNull(tree.getRight())) {
                    checkNegation(tree.getLeft(), 1);
                    return;
                } else {
                    throw new SKNFException(10);
                }
            }
            if (Objects.nonNull(tree.getLeft())) checkNegation(tree.getLeft(), 0);
            if (Objects.nonNull(tree.getRight())) checkNegation(tree.getRight(), 0);
        } else {
            if (Objects.nonNull(tree.getRight())) {
                throw new SKNFException(10);
            }
            if (Objects.nonNull(tree.getLeft())) checkNegation(tree.getLeft(), 1);
        }
    }

    private void checkAtomsForAllElements() throws SKNFException {
        for (String atom : ATOMS) {
            for (String element : ELEMENTS) {
                if (!atom.contains(element)) {
                    throw new SKNFException(5);
                }
                int count = 0;
                for (int i = 0; i < atom.length(); i++) {
                    if (element.equals("" + atom.charAt(i))) {
                        count++;
                    }
                    if (atom.charAt(i) == '!') {
                        i++;
                    }
                }
                if (count > 1) throw new SKNFException(8);
            }

        }
    }

    private void checkAtomsForOperations() throws SKNFException {
        for (String atom : ATOMS) {
            if (atom.contains(MAIN_SIGN)) {
                throw new SKNFException(7);
            }
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

    public void addAtoms(String atom) {
        ATOMS.add(atom);
    }

    public Set<String> getELEMENTS() {
        return ELEMENTS;
    }

    public List<String> getATOMS() {
        return ATOMS;
    }
}
