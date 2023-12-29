import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Добро пожаловать в приложение BooleanOperations");
        System.out.println("Оно умеет взаимодействовать с функциями записанными в виде (!X1 & X2) | X3 или 1000 1110");
        System.out.println("Для записи функций можно использовать: '!' - отрицание, '|' - дизъюнкция, '&' - конъюнкция");
        BoolOperator operator = null;
        while (operator == null) {
            System.out.print("Введите функцию: ");
            String inputString = scanner.nextLine();
            try {
                operator = new BoolOperator(inputString);
            } catch (InvalidInputStringException ex) {
                System.out.println("Вы неверно ввели функцию, попробуйте еще раз");
            }
        }
        while (true) {
            System.out.println("Выбирете одну из возможных функций приложения");
            System.out.println("0 - Закончить работу с приложением");
            System.out.println("1 - Таблица истинности функции");
            System.out.println("2 - СКНФ функции");
            System.out.println("3 - СДНФ функции");
            System.out.println("4 - Сокращенная ДНФ функции");
            System.out.println("5 - Тупиковые ДНФ функции");
            System.out.println("6 - Таблица Квайна функции");
            System.out.println("7 - Минимальные ДНФ функции");
            System.out.println("8 - Полином Жегалкина функции");
            System.out.print("Введите ваш выбор: ");
            String input = scanner.nextLine().strip();
            if (check(input)) {
                if (input.equals("0")) break;
                choose(input, operator);
            } else {
                System.out.println("Неверный ввод. Попробуйте еще раз");
            }

        }
    }

    private static boolean check(String string) {
        Pattern p = Pattern.compile("[0-8]");
        Matcher m = p.matcher(string);
        return m.matches();
    }

    private static void choose(String input, BoolOperator operator) {
        switch (input) {
            case "1":
                operator.table();
                break;
            case "2":
                operator.scnf();
                break;
            case "3":
                operator.sdnf();
                break;
            case "4":
                operator.abbrDNF();
                break;
            case "5":
                operator.tupicDNF();
                break;
            case "6":
                operator.quineTable();
                break;
            case "7":
                operator.minimumDNF();
                break;
            case "8":
                operator.polynomial();
                break;
        }
    }
}