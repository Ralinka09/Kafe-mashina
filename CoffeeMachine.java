import java.util.Scanner;

public class CoffeeMachine {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Добре дошли в кафе машината!");
        System.out.println("Меню:");
        System.out.println("- еспресо - 1.50 лв.");
        System.out.println("- лате - 2.00 лв.");
        System.out.println("- капучино - 2.50 лв.");
        System.out.print("Моля, напишете коя напитка желаете: ");

        String choice = scanner.nextLine().toLowerCase();
        double price = 0;
        String drink = "";

        // Определяне на избраната напитка
        if (choice.equals("еспресо")) {
            drink = "Еспресо";
            price = 1.50;
        } else if (choice.equals("лате")) {
            drink = "Лате";
            price = 2.00;
        } else if (choice.equals("капучино")) {
            drink = "Капучино";
            price = 2.50;
        } else {
            System.out.println("Невалиден избор! Моля, опитайте отново.");
            return;
        }

        System.out.println("Избрали сте " + drink + ". Цена: " + price + " лв.");
        System.out.print("Моля, въведете сумата, която вкарвате: ");

        double money = scanner.nextDouble();

        // Проверка на сумата
        if (money < price) {
            double needed = price - money;
            System.out.println("Недостатъчно пари! Трябват ви още " + needed + " лв.");
        } else if (money == price) {
            System.out.println("Благодаря! Пригответе си вашето " + drink + ".");
        } else {
            double change = money - price;
            System.out.println("Благодаря! Пригответе си вашето " + drink + ".");
            System.out.println("Вашето ресто е: " + change + " лв.");
        }

        System.out.println("Хубав ден!");
    }
}