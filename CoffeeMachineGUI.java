import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class Напитка {
    String име;
    double цена;
    List<String> добавки;

    Напитка(String име, double цена) {
        this.име = име;
        this.цена = цена;
        this.добавки = new ArrayList<>();
    }

    double ценаСДобавки() {
        double total = цена;
        for (String добавка : добавки) {
            switch (добавка) {
                case "Захар" -> total += 0.10;
                case "Мляко" -> total += 0.20;
                case "Сметана" -> total += 0.30;
                case "Канела" -> total += 0.15;
            }
        }
        return total;
    }

    @Override
    public String toString() {
        return име + (добавки.isEmpty() ? "" : " " + добавки) + " - " + String.format("%.2f", ценаСДобавки()) + " лв";
    }
}

public class CoffeeMachineGUI extends JFrame {

    private final JComboBox<String> напиткиBox;
    private final JSpinner бройSpinner;
    private final JTextField париField;
    private final JTextArea резултатArea;
    private final JTextArea менюArea;
    private final JButton бутонДобавки, бутонДобави, бутонПлащане, бутонОткажи;

    private final Map<String, Double> напиткиЦени = new LinkedHashMap<>();
    private final List<Напитка> поръчка = new ArrayList<>();
    private List<String> избраниДобавки = new ArrayList<>();

    public CoffeeMachineGUI() {
        setTitle("☕ Кафе Машина");
        setSize(700, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(250, 240, 230));

        JLabel заглавие = new JLabel("Добре дошли в кафе машината!", JLabel.CENTER);
        заглавие.setFont(new Font("SansSerif", Font.BOLD, 18));
        заглавие.setForeground(new Color(80, 50, 20));
        add(заглавие, BorderLayout.NORTH);

        // Меню
        напиткиЦени.put("Еспресо", 1.50);
        напиткиЦени.put("Лате", 2.00);
        напиткиЦени.put("Капучино", 2.50);
        напиткиЦени.put("Мока", 2.80);
        напиткиЦени.put("Американо", 1.80);

        менюArea = new JTextArea();
        менюArea.setEditable(false);
        менюArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        менюArea.setBackground(new Color(255, 250, 240));
        менюArea.setBorder(BorderFactory.createTitledBorder("📋 Меню"));

        StringBuilder меню = new StringBuilder();
        for (Map.Entry<String, Double> entry : напиткиЦени.entrySet()) {
            меню.append(String.format("%-10s  %.2f лв.\n", entry.getKey(), entry.getValue()));
        }
        менюArea.setText(меню.toString());
        add(new JScrollPane(менюArea), BorderLayout.WEST);

        JPanel панел = new JPanel(new GridLayout(0, 1, 5, 5));
        панел.setBackground(new Color(250, 240, 230));

        напиткиBox = new JComboBox<>(напиткиЦени.keySet().toArray(new String[0]));
        бройSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        париField = new JTextField();
        бутонДобавки = new JButton("Избери добавки");
        бутонДобави = new JButton("Добави напитка 🔁");
        бутонПлащане = new JButton("Плащане 💳");
        бутонОткажи = new JButton("Откажи ❌");

        панел.add(new JLabel("Изберете напитка:"));
        панел.add(напиткиBox);
        панел.add(new JLabel("Колко броя желаете:"));
        панел.add(бройSpinner);
        панел.add(бутонДобавки);
        панел.add(бутонДобави);
        панел.add(new JLabel("Въведете сумата за плащане:"));
        панел.add(париField);
        панел.add(бутонПлащане);
        панел.add(бутонОткажи);

        add(панел, BorderLayout.CENTER);

        резултатArea = new JTextArea(10, 30);
        резултатArea.setEditable(false);
        резултатArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        резултатArea.setBorder(BorderFactory.createTitledBorder("📦 Поръчка"));
        add(new JScrollPane(резултатArea), BorderLayout.SOUTH);

        // Действия
        бутонДобавки.addActionListener(e -> избериДобавки());
        бутонДобави.addActionListener(e -> добавиНапитка());
        бутонПлащане.addActionListener(e -> плащане());
        бутонОткажи.addActionListener(e -> откажиПоръчка());

        обновиРезултатArea(); // Показва съобщение за благодарност в началото
    }

    private void избериДобавки() {
        JCheckBox захар = new JCheckBox("Захар (+0.10)");
        JCheckBox мляко = new JCheckBox("Мляко (+0.20)");
        JCheckBox сметана = new JCheckBox("Сметана (+0.30)");
        JCheckBox канела = new JCheckBox("Канела (+0.15)");

        JPanel екстриПанел = new JPanel(new GridLayout(0, 1));
        екстриПанел.add(захар);
        екстриПанел.add(мляко);
        екстриПанел.add(сметана);
        екстриПанел.add(канела);

        int избор = JOptionPane.showConfirmDialog(this, екстриПанел,
                "Изберете добавки за текущата напитка",
                JOptionPane.OK_CANCEL_OPTION);

        избраниДобавки.clear();

        if (избор == JOptionPane.OK_OPTION) {
            if (захар.isSelected()) избраниДобавки.add("Захар");
            if (мляко.isSelected()) избраниДобавки.add("Мляко");
            if (сметана.isSelected()) избраниДобавки.add("Сметана");
            if (канела.isSelected()) избраниДобавки.add("Канела");
        }
    }

    private void добавиНапитка() {
        String напиткаИме = (String) напиткиBox.getSelectedItem();
        double цена = напиткиЦени.get(напиткаИме);
        int брой = (Integer) бройSpinner.getValue();

        for (int i = 1; i <= брой; i++) {
            Напитка n = new Напитка(напиткаИме, цена);
            n.добавки.addAll(избраниДобавки);
            поръчка.add(n);
        }
        избраниДобавки.clear();
        обновиРезултатArea();
    }

    private void обновиРезултатArea() {
        резултатArea.setText("");
        if (поръчка.isEmpty()) {
            резултатArea.setText("Благодарим Ви, че избрахте нашата кафе машина! ☕\n");
            return;
        }

        double общо = 0;
        int номер = 1;
        for (Напитка n : поръчка) {
            резултатArea.append(String.format("Напитка #%d: %s\n", номер++, n));
            общо += n.ценаСДобавки();
        }
        резултатArea.append(String.format("\nОбщо за плащане: %.2f лв.\n", общо));
    }

    private void плащане() {
        if (поръчка.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Няма добавени напитки!");
            return;
        }

        double общо = поръчка.stream().mapToDouble(Напитка::ценаСДобавки).sum();
        double пари = 0;

        String въведениПари = париField.getText().trim();
        if (!въведениПари.isEmpty()) {
            try {
                пари = Double.parseDouble(въведениПари);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Невалидна сума!");
                return;
            }
        }

        while (пари < общо) {
            double остава = общо - пари;
            String още = JOptionPane.showInputDialog(this,
                    String.format("Недостатъчно пари! Трябват още %.2f лв. Въведете допълнителна сума:", остава));
            if (още == null) return;
            try {
                пари += Double.parseDouble(още);
                париField.setText(String.valueOf(пари));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Невалидна сума!");
            }
        }

        double ресто = пари - общо;

        резултатArea.append(String.format("\nОбщо: %.2f лв.\n", общо));
        резултатArea.append(String.format("Платено: %.2f лв.\n", пари));
        if (ресто > 0) {
            резултатArea.append(String.format("Ресто: %.2f лв.\n", ресто));
        }

        резултатArea.append("\nПриготвяне на кафето");
        SwingWorker<Void, String> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 0; i < 5; i++) {
                    Thread.sleep(500);
                    publish(".");
                }
                return null;
            }

            @Override
            protected void process(List<String> chunks) {
                for (String s : chunks) {
                    резултатArea.append(s);
                }
            }

            @Override
            protected void done() {
                резултатArea.append("\nВашето кафе е готово ☕\n");
                поръчка.clear();
                париField.setText("");
                обновиРезултатArea();
            }
        };
        worker.execute();
    }

    private void откажиПоръчка() {
        int потвърди = JOptionPane.showConfirmDialog(
                this,
                "Сигурни ли сте, че искате да откажете поръчката?",
                "Отказ",
                JOptionPane.YES_NO_OPTION
        );

        if (потвърди == JOptionPane.YES_OPTION) {
            поръчка.clear();
            избраниДобавки.clear();
            париField.setText("");
            бройSpinner.setValue(1);
            напиткиBox.setSelectedIndex(0);
            обновиРезултатArea();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CoffeeMachineGUI().setVisible(true));
    }
}
