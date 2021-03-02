package parser;

import config.Config;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static config.Config.*;

public class Formula {
    private String formula;
    private String formulaSDNF;
    private ExpressionTree tree;
    private TruthTable truthTable;
    private List<String> ELEMENTS;
    private Parser parser;
    private boolean result = true;
    private final String MAIN_SIGN = "\\/";
    private final String SIGN = "/\\";

    public Formula(String expression) {
        try {
            if ("1".equals(expression) || "0".equals(expression)) {
                result = false;
                throw new SKNFException("1".equals(expression) ? 14 : 13);
            }
            parser = new Parser(expression);
            formula = expression;
            tree = parser.getTree();
            ELEMENTS = new ArrayList<>(parser.getELEMENTS());
            createTruthTable();
            formulaSDNF = generateFormula();
            output();
        } catch (SKNFException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void output() {
        try (FileWriter writer = new FileWriter(Config.OUT_FILE_PATH, true)) {
            ELEMENTS.add("f");
            truthTable.output(ELEMENTS);
            truthTable.outputInFile(ELEMENTS);
            System.out.println(formulaSDNF);
            writer.write(formulaSDNF);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void createTruthTable() throws SKNFException {
        truthTable = new TruthTable(ELEMENTS.size());
        for (int i = 0; i < truthTable.getCountRows(); i++) {
            System.out.println(i);
            truthTable.setValueRow(i, determineValue(truthTable.getRow(i), tree));
        }
        System.out.println("Done!");
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
        if (truthTable.countDis() == truthTable.getCountRows()) {
            return "1";
        }
        System.out.println("Print brackets: ");
        for (int i = 0; i < truthTable.countDis() - 1; i++) {
            if (i % 1000 == 0) System.out.println(i);
            builder.append("(");
        }
        int count = 0;
        System.out.println("generate dis:");
        for (int j = 0; j < truthTable.getCountRows(); j++) {
            if (truthTable.getValueRow(j) == 1) {
                builder.append(createAtom(ELEMENTS.size(), truthTable.getTable()[j]));
                if (count != 0) {
                    builder.append(")");
                }
                builder.append(MAIN_SIGN);
                count++;
            }
            if (j % 1000 == 0) System.out.println(j);
        }
        builder.setLength(builder.length() - 2);
        return builder.toString();
    }

    private String createAtom(int countElements, int[] rowTruthTable) {
        StringBuilder atom = new StringBuilder();
        for (int i = 0; i < countElements - 1; i++) {
            atom.append("(");
        }
        int count = 0;
        for (int i = 0; i < countElements; i++) {
            atom.append((rowTruthTable[i] == 1) ? Config.SYMBOLS.get(i) : ("(!" + Config.SYMBOLS.get(i) + ")"));
            if (count != 0) {
                atom.append(")");
            }
            atom.append(SIGN);
            count++;
        }
        atom.setLength(atom.length() - 2);
        return atom.toString();
    }

    public String getResultParser() {
        return parser.getMessage();
    }

    public String getFormula() {
        return formula;
    }

    public ExpressionTree getTree() {
        return tree;
    }

    public TruthTable getTruthTable() {
        return truthTable;
    }

    public List<String> getELEMENTS() {
        return ELEMENTS;
    }

    public boolean isResult() {
        return result;
    }
}
