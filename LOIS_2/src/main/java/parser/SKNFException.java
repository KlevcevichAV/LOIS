/////////////////////////////////////////////////////////////////////////////////////////
// Лабораторная работа №1 по дисциплине ЛОИС
// Вариант С: Проверить, является ли формула СКНФ
// Выполнена студентом грруппы 821701 БГУИР Клевцевич Александр Владимирович
// Класс предназначен для исключений

package parser;

public class SKNFException extends Exception {
    private String message;
    private int number;

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
                message = "Atom has a conjunction operation!";
                break;
            }
            case 8: {
                message = "Elements are repeated in the atom!";
                break;
            }
            case 9: {
                message = "Repeated atom!";
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
        }
    }

    public int getNumber() {
        return number;
    }
}
