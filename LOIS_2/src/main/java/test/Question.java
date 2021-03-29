/////////////////////////////////////////////////////////////////////////////////////////
// Лабораторная работа №2 по дисциплине ЛОИС
// Вариант 9: Построить СДНФ для заданной формулы
// Выполнена студентом грруппы 821701 БГУИР Клевцевич Александр Владимирович
// Класс предназначен для создания вопроса с вариантами ответа
package test;

import config.Config;
import org.apache.maven.surefire.shade.org.apache.commons.lang3.StringUtils;
import parser.Formula;

import java.util.ArrayList;
import java.util.List;

import static config.Config.*;

public class Question {
    private String questionFormula;
    private String result;
    private List<String> variants;
    private Formula formula;
    private final String SIGN = "/\\";
    private final String MAIN_SIGN = "\\/";
    public static final int AMOUNT_VARIANTS = 5;


    private String previousAtom;
    private String previousOperation;

    public Question() {
        variants = new ArrayList<>();
        int amountElements = 2 + (int) (Math.random() * 2);
        int amountOperation = 3 + (int) (Math.random() * 7);
        questionFormula = generateFormula(amountElements, amountOperation);
        formula = new Formula(questionFormula);
        result = formula.getFormulaSDNF();
        int numberAnswer = ("0".equals(result))? AMOUNT_VARIANTS - 1 : (int) (Math.random() * 4);
        for (int i = 0; i < AMOUNT_VARIANTS; i++) {
            if (numberAnswer == i) {
                variants.add(result);
            } else {
                if(i == AMOUNT_VARIANTS - 1){
                    variants.add("0");
                    continue;
                }
                String tempFormula = generateRandomFormula(amountElements);
                if (variants.contains(tempFormula)) {
                    i--;
                } else {
                    variants.add(tempFormula);
                }
            }
        }

    }

    private String generateFormula(int amountElements, int amountOperation) {
        StringBuilder builder = new StringBuilder(StringUtils.repeat('(', amountOperation));
        previousAtom = generateAtom(amountElements);
        previousOperation = "";
        builder.append(previousAtom);
        for (int i = 0; i < amountOperation; i++) {
            int numberOperation = (int) (Math.random() * OPERATIONS.size());
            String atom = generateAtom(amountElements);
            String operation = OPERATIONS.get(numberOperation);
            if(previousAtom.equals(atom) || previousOperation.equals(operation)){
                i--;
                continue;
            }
            previousAtom = atom;
            previousOperation = operation;
            builder.append(operation).append(atom).append(")");
        }
        return builder.toString();
    }

    private String generateAtom(int amountElements) {
        int numberElement = (int) (Math.random() * amountElements);
        int negative = (int) (Math.random() * 2);
        return (negative == 0) ? SYMBOLS.get(numberElement) : "(!" + SYMBOLS.get(numberElement) + ")";
    }

    private String generateRandomFormula(int countElements) {
        RandomTruthTable truthTable = new RandomTruthTable(countElements);
        StringBuilder builder = new StringBuilder();
        builder.append("(".repeat(Math.max(0, truthTable.getCountCon() - 1)));
        int count = 0;
        for (int j = 0; j < truthTable.getRows(); j++) {
            if (truthTable.getTable()[j][countElements] == 0) {
                builder.append(generateAtom(countElements, truthTable.getTable()[j]));
                if (count != 0) {
                    builder.append(")");
                }
                builder.append(MAIN_SIGN);
                count++;
            }
        }
        builder.setLength(builder.length() - 2);
        return builder.toString();
    }

    private String generateAtom(int countElements, int[] rowTruthTable) {
        StringBuilder atom = new StringBuilder();
        for (int i = 0; i < countElements - 1; i++) {
            atom.append("(");
        }
        int count = 0;
        for (int i = 0; i < countElements; i++) {
            atom.append((rowTruthTable[i] == 0) ? Config.SYMBOLS.get(i) : ("(!" + Config.SYMBOLS.get(i) + ")"));
            if (count != 0) {
                atom.append(")");
            }
            atom.append(SIGN);
            count++;
        }
        atom.setLength(atom.length() - 2);
        return atom.toString();
    }

    public boolean check(int pointer) {
        return result.equals(variants.get(pointer));
    }

    public String getQuestionFormula() {
        return questionFormula;
    }

    public List<String> getVariants() {
        return variants;
    }

    public int getAMOUNT_VARIANTS() {
        return AMOUNT_VARIANTS;
    }

    public void output() {
        formula.output();
    }
}
