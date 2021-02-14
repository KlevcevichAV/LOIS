/////////////////////////////////////////////////////////////////////////////////////////
// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант С: Проверить, является ли формула СКНФ
// Выполнена студентом грруппы 821701 БГУИР Клевцевич Александр Владимирович
// Класс предназначен для хранения формулы ввиде дерева

package parser;

public class ExpressionTree {
    private String expression;
    private ExpressionTree left;
    private ExpressionTree right;
    private final Parser root;

    public ExpressionTree(String expression, Parser root) {
        this.expression = expression;
        this.root = root;
        if (expression.length() < 3) {
            left = right = null;
            if (expression.charAt(0) == '!') {
                root.addElements((expression.length() != 2) ? expression : ("" + expression.charAt(1)));
            } else {
                root.addElements(expression);
            }
            return;
        }
        int checkSign = searchSignOutsideBrackets();
        if (checkSign == -1) {
            withoutBrackets();
        } else {
            left = new ExpressionTree(copy(0, checkSign), root);
            right = new ExpressionTree(copy(checkSign + 1, expression.length()), root);
            this.expression = "" + expression.charAt(checkSign);
        }

    }

    private String copy(int start, int end) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = start; i < end; i++) {
            stringBuilder.append(expression.charAt(i));
        }
        return stringBuilder.toString();
    }

    private int searchSignOutsideBrackets() {
        int check = 0;
        for (int i = 0; i < expression.length(); i++) {
            if ((expression.charAt(i) != '(' && expression.charAt(i) != ')') && check == 0) {
                if (Constant.SIGNS.contains("" + expression.charAt(i)) && expression.charAt(i) != '!') {
                    return i;
                }
            }
            if (expression.charAt(i) == '(') {
                check++;
            } else if (expression.charAt(i) == ')') {
                check--;
            }
        }
        return -1;
    }

    private void withoutBrackets() {
        String expression = copy(1, this.expression.length() - 1);
        left = new ExpressionTree(expression, root);
        right = null;
    }

    public String getExpression() {
        return expression;
    }

    public ExpressionTree getLeft() {
        return left;
    }

    public ExpressionTree getRight() {
        return right;
    }
}
