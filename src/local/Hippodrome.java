package local;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 16.04.2017.
 */
public class Hippodrome implements Runnable {
    public static Hippodrome game;
    private List<Horse> horses;

    private JFrame frame;
    private JPanel main;
    private JButton btnCmd;         // Командная кнопка

    public List<Horse> getHorses() {
        return horses;
    }

    private void prepareRace() {
        horses = new ArrayList<Horse>();
        horses.add(new Horse("Homer", 3));
        horses.add(new Horse("Patrick", 3));
        horses.add(new Horse("Lucky", 3));
        btnCmd.setText(Constants.LABEL_START_RACE);
        draw();
    }

    private void makeGUI() {
        frame = new JFrame("Hippodrome");
//        frame.setTitle("Transparent JFrame Demo");

//        frame.setUndecorated(true);
        frame.setSize(800, 400);
        frame.setResizable(false);                            // No window resize
        frame.setLocationRelativeTo(null);                    // Располагать в центре экрана
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        main = new MainField();                       // Корневая панель
//        main.setBackground(Color.blue);                   // Синий цвет - будущая решётка
        frame.getContentPane().add(BorderLayout.CENTER, main);                       // Добавляем в окно
        btnCmd = new JButton(Constants.LABEL_START_RACE);
        btnCmd.setEnabled(true);
        frame.getContentPane().add(BorderLayout.SOUTH, btnCmd);                       // Добавляем в окно
        btnCmd.addActionListener(new CmdButtonActionListener());


//        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        frame.setLayout(new GridBagLayout());

//        frame.setOpacity(0.0f);
        frame.setVisible(true);

    }

    public void runGame() {
        makeGUI();
        prepareRace();

    }

    private boolean isRaceOver() {
        for (Horse horse : horses) {
            if (horse.getDistance() >= Constants.RACE_DISTANCE) {
                return true;
            }
        }

        return false;
    }

    public void run() {
        while (!isRaceOver()) {
//        for (int i = 0; i < 100; i++) {
            move();
            draw();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

//        printWinner();
        btnCmd.setText(Constants.LABEL_RESTART_RACE);
        btnCmd.setEnabled(true);
    }

    public void move() {
        for (Horse horse : horses) {
            horse.move();
        }
    }

    public void draw() {
        main.repaint();

//        for (Horse horse : horses) {
//            horse.draw();
//        }
    }

    private void printWinner() {
//        System.out.printf("Winner is %s!", getWinner());
        getWinner();
    }

    private Horse getWinner() {
        Horse winner = null;
        for (Horse horse : horses) {
            if (winner == null || winner.getDistance() < horse.getDistance()) {
                winner = horse;
            }
        }

        return winner;
    }


    public static void main(String[] args) {
        game = new Hippodrome();
        game.runGame();

    }

    // Действия при нажатии на кнопку - в зависимости от текста кнопки
    class CmdButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();

            if (Constants.LABEL_START_RACE.equals(button.getText())) {
                button.setEnabled(false);
                new Thread(game).start();
            }

            if (Constants.LABEL_RESTART_RACE.equals(button.getText())) {    // Рестарт
                prepareRace();
            }

        }
    }

    class MainField extends JPanel {
//        String[] filenames = {"horse1.png", "horse2.png", "horse3.png", "horse4.png"};

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            setBackground(Color.white);

            g.setColor(Color.red);
            g.drawLine((int) Constants.RACE_DISTANCE * 3 + 46, 0, (int) Constants.RACE_DISTANCE * 3 + 46, this.getHeight());

            g.setColor(Color.black);

            for (int i = 0; i < horses.size(); i++) {

                int distance = (int) horses.get(i).getDistance();
                int x = 10 + distance * 3;
                int y = 40 + i * 100;

                g.drawImage(new ImageIcon(this.getClass().getResource("images/horse" + horses.get(i).getVisualState() + ".png")).getImage(), x, y, null);

                g.setFont(new Font("Tahoma", Font.PLAIN, 14));
                g.drawString(Hippodrome.game.getHorses().get(i).getName(), x, y);

            }

            if (isRaceOver()) {
                g.setFont(new Font("Tahoma", Font.BOLD, 80));
                g.drawString("Winner is " + getWinner() + "!", 50, main.getHeight() / 2 + 40);
            }

        }
    }

}
