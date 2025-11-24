import java.util.Scanner;

public class CoffeeMachine {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean поръчвайОще = true;

        System.out.println("Добре дошли в кафе машината!");

        while (поръчвайОще) {

            // ---- Меню ----
            System.out.println("\nМеню:");
            System.out.println("1. Еспресо - 1.50 лв.");
            System.out.println("2. Лате - 2.00 лв.");
            System.out.println("3. Капучино - 2.50 лв.");

            // ---- Избор на напитка ----
            String напитка = "";
            double цена = 0;
            boolean валиденИзбор = false;

            while (!валиденИзбор) {
                System.out.print("Изберете напитка (еспресо/лате/капучино): ");
                напитка = scanner.nextLine().toLowerCase();

                if (напитка.equals("еспресо")) {
                    цена = 1.50;
                    валиденИзбор = true;
                } else if (напитка.equals("лате")) {
                    цена = 2.00;
                    валиденИзбор = true;
                } else if (напитка.equals("капучино")) {
                    цена = 2.50;
                    валиденИзбор = true;
                } else {
                    System.out.println("Невалиден избор! Опитайте отново.");
                }
            }

            // ---- Брой напитки ----
            System.out.print("Колко броя желаете?: ");
            int брой = scanner.nextInt();
            scanner.nextLine(); // изчистване на реда

            double общо = 0;

            // ---- За всяка напитка отделно ----
            for (int i = 1; i <= брой; i++) {
                System.out.printf("%nПоръчка #%d:%n", i);
                double ценаЕкстри = 0;
                String добавки = "";

                System.out.println("Екстри (по избор): захар (+0.10), мляко (+0.20), сметана (+0.30), канела (+0.15)");
                System.out.print("Желаете ли екстри за тази напитка? (да/не): ");
                String екстриОтговор = scanner.nextLine().toLowerCase();

                if (екстриОтговор.equals("да")) {
                    System.out.print("Въведете екстрите (пример: захар, мляко): ");
                    String въведениЕкстри = scanner.nextLine().toLowerCase();

                    if (въведениЕкстри.contains("захар")) {
                        ценаЕкстри += 0.10;
                        добавки += "захар ";
                    }
                    if (въведениЕкстри.contains("мляко")) {
                        ценаЕкстри += 0.20;
                        добавки += "мляко ";
                    }
                    if (въведениЕкстри.contains("сметана")) {
                        ценаЕкстри += 0.30;
                        добавки += "сметана ";
                    }
                    if (въведениЕкстри.contains("канела")) {
                        ценаЕкстри += 0.15;
                        добавки += "канела ";
                    }
                }

                double ценаНапитка = цена + ценаЕкстри;
                общо += ценаНапитка;

                System.out.printf("Цена за напитка #%d: %.2f лв. (%s)%n",
                        i, ценаНапитка, добавки.isEmpty() ? "без екстри" : добавки);
            }

            // ---- Общо плащане ----
            System.out.printf("%nОбща цена за %d %s: %.2f лв.%n",
                    брой, capitalize(напитка), общо);

            System.out.print("Въведете сумата, която плащате: ");
            double пари = scanner.nextDouble();
            scanner.nextLine();

            if (пари < общо) {
                System.out.printf("Недостатъчно пари! Трябват още %.2f лв.%n", общо - пари);
            } else if (пари == общо) {
                System.out.println("Благодаря! Пригответе си вашето кафе ☕");
            } else {
                System.out.printf("Благодаря! Вашето ресто е %.2f лв.%n", пари - общо);
            }

            // ---- Нова поръчка ----
            System.out.print("\nЖелаете ли да поръчате още? (да/не): ");
            String отговор = scanner.nextLine().toLowerCase();
            if (!отговор.equals("да")) {
                поръчвайОще = false;
                System.out.println("Благодарим, че използвахте кафе машината! ☕");
            }
        }

        scanner.close();
    }

    static String capitalize(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}