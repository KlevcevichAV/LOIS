/////////////////////////////////////////////////////////////////////////////////////////
// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант С: Проверить, является ли формула СКНФ
// Выполнена студентом грруппы 821701 БГУИР Клевцевич Александр Владимирович
// Класс предназначен для парсинга формул

package parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Parser {
    private String expression;
    private ExpressionTree tree;
    private boolean result;
    private String message;
    private Set<String> ELEMENTS;
    private List<String> ATOMS;

    public Parser(String expression) {
        this.expression = expression;
        ELEMENTS = new HashSet<>();
        ATOMS = new ArrayList<>();
        result = false;
        message = "";
        try {
            checkSymbols();
            checkBrackets();
            tree = new ExpressionTree(expression, this);
            searchAtoms(tree, 0);
            if (ATOMS.size() != ATOMS.stream().distinct().count()) {
                throw new SKNFException(9);
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
        for (int i = 0; i < expression.length(); i++) {
            if (!(Constant.SYMBOLS.contains("" + expression.charAt(i)) || Constant.SIGNS.contains("" + expression.charAt(i)))) {
                throw new SKNFException(6);
            }
        }
    }

    private void checkBrackets() throws SKNFException {
        if (expression.contains(")(")) {
            throw new SKNFException(3);
        }
        if (expression.charAt(0) == ')') {
            throw new SKNFException(3);
        }
        if (expression.charAt(0) != '(' && expression.length() != 1) {
            throw new SKNFException(3);
        }
        if (expression.charAt(0) == '(' && expression.charAt(expression.length() - 1) != ')') {
            throw new SKNFException(3);
        }
        int checkOpen = 0;
        int checkClose = 0;
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '(') {
                checkOpen++;
            } else if (expression.charAt(i) == ')') {
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

    private void searchAtoms(ExpressionTree tree, int code) throws SKNFException {
        if (tree.getExpression().equals("&")) {
            searchAtoms(tree.getLeft(), 1);
            searchAtoms(tree.getRight(), 1);
            return;
        }
        // '!' ?
        if (Constant.SIGNS.contains(tree.getExpression())) {
            throw new SKNFException(4);
        }
        if (code == 1) {
            ATOMS.add(tree.getExpression());
            return;
        }
        if (code == 0 && !tree.getExpression().contains("&") && tree.getExpression().length() < 8) {
            ATOMS.add(tree.getExpression());
            return;
        }
        searchAtoms(tree.getLeft(), 0);
    }

    private void checkAtomsForAllElements() throws SKNFException {
//        if(ATOMS.size() == 1) throw new SKNFException(4);
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
                }
                if (count > 1) throw new SKNFException(8);
            }

        }
    }

    private void checkAtomsForOperations() throws SKNFException {
        for (String atom : ATOMS) {
            if (atom.contains("&")) {
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
