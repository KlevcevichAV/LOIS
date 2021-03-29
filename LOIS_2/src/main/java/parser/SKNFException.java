/////////////////////////////////////////////////////////////////////////////////////////
// Лабораторная работа №2 по дисциплине ЛОИС
// Вариант 9: Построить СДНФ для заданной формулы
// Выполнена студентом грруппы 821701 БГУИР Клевцевич Александр Владимирович
// Класс предназначен для исключений

package parser;

public class SKNFException extends Exception {
    private String message;
    private final int number;

    public String getMessage() {
        return message;
    }

    public SKNFException(int number) {
        this.number = number;
        switch (number) {
            case 1: {
                message = "More open brackets!";
                break;
            }
            case 2: {
                message = "More closing brackets!";
                break;
            }
            case 3: {
                message = "Syntax violated!";
                break;
            }
            case 4: {
                message = "Instead of a conjunction, another operation is found!";
                break;
            }
            case 5: {
                message = "Disjunction does not consist of all the variables in the list!";
                break;
            }
            case 6: {
                message = "Unknown character used!";
                break;
            }
            case 7: {
                message = "Disjunction has a conjunction operation!";
                break;
            }
            case 8: {
                message = "Elements are repeated in the disjunction!";
                break;
            }
            case 9: {
                message = "Repeated disjunction!";
                break;
            }
            case 10: {
                message = "Negation is not with the element!";
                break;
            }
            case 11: {
                message = "Not enough brackets";
                break;
            }
            case 12: {
                message = "Double negative";
                break;
            }
            case 13: {
                message = "0";
                break;
            }
            case 14: {
                message = "1";
                break;
            }
        }
    }

    public int getNumber() {
        return number;
    }
}
