/////////////////////////////////////////////////////////////////////////////////////////
// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант С: Проверить, является ли формула СКНФ
// Выполнена студентом грруппы 821701 БГУИР Клевцевич Александр Владимирович
// Класс предназначен для хранения формулы ввиде дерева

package parser;

public class ExpressionTree {
    private String expression;
    private String operation;
    private ExpressionTree left;
    private ExpressionTree right;
    private final Parser root;

    public ExpressionTree(String expression, Parser root) throws SKNFException {
        this.expression = expression;
        this.root = root;
        if (expression.length() == 1) {
            left = right = null;
            root.addElements(expression);
            return;
        }
        if (expression.charAt(0) == '!') {
            this.operation = "!";
            right = null;
            left = new ExpressionTree(copy(expression, 1, expression.length()), root);
            return;
        }
        try {
            withoutBrackets();
        } catch (SKNFException sknfException) {
            throw new SKNFException(sknfException.getNumber());
        }

    }

    private void withoutBrackets() throws SKNFException {
        if (expression.charAt(0) == '(' && expression.charAt(expression.length() - 1) == ')') {
            String expression = copy(this.expression, 1, this.expression.length() - 1);
            int pointerSign = searchSignOutsideBrackets(expression);
            if (pointerSign == 0) {
                right = null;
                left = new ExpressionTree(expression, root);
                operation = searchSign(expression, pointerSign);
                return;
            }
            // !
            if (pointerSign == -1) {
                throw new SKNFException(3);
            }
            operation = searchSign(expression, pointerSign);
            String leftExpression = copy(expression, 0, pointerSign);
            if (operation.length() == 2) {
                pointerSign += 1;
            }
            String rightExpression = copy(expression, pointerSign + 1, expression.length());
            left = new ExpressionTree(leftExpression, root);
            right = new ExpressionTree(rightExpression, root);
        } else {
            throw new SKNFException(11);
        }
    }

    private String copy(String expression, int start, int end) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = start; i < end; i++) {
            stringBuilder.append(expression.charAt(i));
        }
        return stringBuilder.toString();
    }

    private int searchSignOutsideBrackets(String expression) {
        int check = 0;
        for (int i = 0; i < expression.length(); i++) {
            if ((expression.charAt(i) != '(' && expression.charAt(i) != ')') && check == 0) {
                String sign = searchSign(expression, i);
                if (Constant.SIGNS.contains(sign)) {
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

    private String searchSign(String expression, int pointer) {
        if (expression.charAt(pointer) == '!' || expression.charAt(pointer) == '~')
            return expression.charAt(pointer) + "";
        return "" + expression.charAt(pointer) + expression.charAt(pointer + 1);
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

    public String getOperation() {
        return operation;
    }
}