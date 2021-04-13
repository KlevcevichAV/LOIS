/////////////////////////////////////////////////////////////////////////////////////////
// Лабораторная работа №2 по дисциплине ЛОИС
// Вариант 8: Построить СКНФ для заданной формулы
// Выполнена студентом грруппы 821701 БГУИР Клевцевич Александр Владимирович
// Класс предназначен для создания и хранения формулы
package parser;

import config.Config;

import java.util.ArrayList;
import java.util.List;

import static config.Config.*;

public class Formula {
    private String formulaSKNF;
    private ExpressionTree tree;
    private TruthTable truthTable;
    private List<String> ELEMENTS;
    private Parser parser;
    private boolean result = true;

    public Formula(String expression) {
        try {
            if ("1".equals(expression) || "0".equals(expression)) {
                result = false;
                throw new SKNFException("1".equals(expression) ? 14 : 13);
            }
            parser = new Parser(expression);
            tree = parser.getTree();
            ELEMENTS = new ArrayList<>(parser.getELEMENTS());
            createTruthTable();
            formulaSKNF = generateFormula();
//            output();
        } catch (SKNFException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void output() {
        ELEMENTS.add("f");
        truthTable.output(ELEMENTS);
        System.out.println(formulaSKNF);
    }

    private void createTruthTable() throws SKNFException {
        truthTable = new TruthTable(ELEMENTS.size());
        for (int i = 0; i < truthTable.getCountRows(); i++) {
            truthTable.setValueRow(i, determineValue(truthTable.getRow(i), tree));
        }
//        System.out.println("Don?e!");
    }

    private boolean determineValue(int[] value, ExpressionTree tree) throws SKNFException {
        switch (tree.getOperation()) {
            case CONJUNCTION: {
                return determineValue(value, tree.getLeft()) & determineValue(value, tree.getRight());
            }
            case DISJUNCTION: {
                return determineValue(value, tree.getLeft()) | determineValue(value, tree.getRight());
            }
            case NEGATION: {
                return !determineValue(value, tree.getLeft());
            }
            case IMPLICATION: {
                return !determineValue(value, tree.getLeft()) | determineValue(value, tree.getRight());
            }
            case EQUIVALENCE: {
                return (!determineValue(value, tree.getLeft()) & !determineValue(value, tree.getRight())) |
                        (determineValue(value, tree.getLeft()) & determineValue(value, tree.getRight()));
            }
            case "": {
                List<String> list = new ArrayList<>(ELEMENTS);
                if ("1".equals(tree.getExpression()) || "0".equals(tree.getExpression())) {
                    return "1".equals(tree.getExpression());
                }
                return value[list.indexOf(tree.getExpression())] == 1;
            }
            default: {
                //!!!!!!!!!!!!!!!!!!!!!!!!
                throw new SKNFException(13);
            }
        }
    }

    private String generateFormula() {
        StringBuilder builder = new StringBuilder();
        if (truthTable.countDis() == 0) {
            return "0";
        }
        builder.append("(".repeat(Math.max(0, truthTable.countDis() - 1)));
        int count = 0;
        for (int j = 0; j < truthTable.getCountRows(); j++) {
            if (truthTable.getValueRow(j) == 0) {
                builder.append(createAtom(ELEMENTS.size(), truthTable.getTable()[j]));
                if (count != 0) {
                    builder.append(")");
                }
//                String MAIN_SIGN = "\\/";
                String MAIN_SIGN = "/\\";
                builder.append(MAIN_SIGN);
                count++;
            }
        }
        builder.setLength(builder.length() - 2);
        return builder.toString();
    }

    private String createAtom(int countElements, int[] rowTruthTable) {
        StringBuilder atom = new StringBuilder();
        atom.append("(".repeat(Math.max(0, countElements - 1)));
        int count = 0;
        for (int i = 0; i < countElements; i++) {
            atom.append((rowTruthTable[i] == 0) ? Config.SYMBOLS.get(i) : ("(!" + Config.SYMBOLS.get(i) + ")"));
            if (count != 0) {
                atom.append(")");
            }
//            String SIGN = "/\\";
            String SIGN = "\\/";
            atom.append(SIGN);
            count++;
        }
        atom.setLength(atom.length() - 2);
        return atom.toString();
    }

    public String getResultParser() {
        return parser.getMessage();
    }

    public boolean isResult() {
        return result;
    }
}
