import java.util.Scanner;

public class CoffeeMachine {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean orderMore = true;

        System.out.println("Добре дошли в кафе машината!");

        while (orderMore) {

            // ---- Меню ----
            System.out.println("\nМеню:");
            System.out.println("1. Еспресо - 1.50 лв.");
            System.out.println("2. Лате - 2.00 лв.");
            System.out.println("3. Капучино - 2.50 лв.");

            // ---- Избор на напитка ----
            String drink = "";
            double price = 0;
            boolean validChoice = false;

            while (!validChoice) {
                System.out.print("Изберете напитка (еспресо/лате/капучино): ");
                drink = scanner.nextLine().toLowerCase();

                switch (drink) {
                    case "еспресо":
                        price = 1.50;
                        validChoice = true;
                        break;
                    case "лате":
                        price = 2.00;
                        validChoice = true;
                        break;
                    case "капучино":
                        price = 2.50;
                        validChoice = true;
                        break;
                    default:
                        System.out.println("Невалиден избор! Опитайте отново.");
                }
            }

            // ---- Брой напитки ----
            System.out.print("Колко броя желаете?: ");
            int quantity = Integer.parseInt(scanner.nextLine());

            double total = 0;

            // ---- За всяка напитка ----
            for (int i = 1; i <= quantity; i++) {
                System.out.printf("%nПоръчка #%d:%n", i);
                double extrasPrice = 0;
                String extrasList = "";

                System.out.println("Екстри (по избор): захар (+0.10), мляко (+0.20), сметана (+0.30), канела (+0.15)");
                System.out.print("Желаете ли екстри за тази напитка? (да/не): ");
                String extrasAnswer = scanner.nextLine().toLowerCase();

                if (extrasAnswer.equals("да")) {
                    System.out.print("Въведете екстрите (пример: захар, мляко): ");
                    String enteredExtras = scanner.nextLine().toLowerCase();

                    if (enteredExtras.contains("захар")) {
                        extrasPrice += 0.10;
                        extrasList += "захар ";
                    }
                    if (enteredExtras.contains("мляко")) {
                        extrasPrice += 0.20;
                        extrasList += "мляко ";
                    }
                    if (enteredExtras.contains("сметана")) {
                        extrasPrice += 0.30;
                        extrasList += "сметана ";
                    }
                    if (enteredExtras.contains("канела")) {
                        extrasPrice += 0.15;
                        extrasList += "канела ";
                    }
                }

                double drinkPrice = price + extrasPrice;
                total += drinkPrice;

                System.out.printf("Цена за напитка #%d: %.2f лв. (%s)%n",
                        i, drinkPrice, extrasList.isEmpty() ? "без екстри" : extrasList);
            }

            // ---- Общо плащане ----
            System.out.printf("%nОбща цена за %d %s: %.2f лв.%n",
                    quantity, capitalize(drink), total);

            System.out.print("Въведете сумата, която плащате: ");
            double money = parseInput(scanner);

            while (money < total) {
                System.out.printf("Недостатъчно пари! Трябват още %.2f лв.%n", total - money);
                System.out.print("Въведи допълнителните пари: ");
                money += parseInput(scanner);
            }

            if (Math.abs(money - total) < 0.001) {
                System.out.println("Благодаря! Пригответе си вашето кафе ☕");
            } else {
                System.out.printf("Благодаря! Вашето ресто е %.2f лв.%n", money - total);
            }

            // ---- Нова поръчка ----
            System.out.print("\nЖелаете ли да поръчате още? (да/не): ");
            String answer = scanner.nextLine().toLowerCase();

            if (!answer.equals("да")) {
                orderMore = false;
                System.out.println("Благодарим, че използвахте кафе машината! ☕");
            }
        }

        scanner.close();
    }

    // Метод за автоматично приемане на запетая или точка
    static double parseInput(Scanner scanner) {
        String input = scanner.nextLine().replace(",", ".").trim();
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("Невалидно число! Опитайте отново:");
            return parseInput(scanner); // рекурсивно повтаря, докато не се въведе валидно число
        }
    }

    static String capitalize(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}
