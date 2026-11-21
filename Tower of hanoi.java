import javax.swing.*;
import java.awt.*;

public class TowerOfHanoiGUI extends JPanel {

    int disks = 3;
    int[][] rods = new int[3][10];
    int[] count = new int[3];

    public TowerOfHanoiGUI() {
        setPreferredSize(new Dimension(700, 450));

        for (int i = disks; i >= 1; i--) {
            rods[0][count[0]++] = i;
        }
    }

    // Draw the rods and disks
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.drawLine(150, 100, 150, 350);
        g.drawLine(350, 100, 350, 350);
        g.drawLine(550, 100, 550, 350);

        g.drawLine(50, 350, 250, 350);
        g.drawLine(250, 350, 450, 350);
        g.drawLine(450, 350, 650, 350);

        for (int r = 0; r < 3; r++) {
            for (int i = 0; i < count[r]; i++) {

                int disk = rods[r][i];
                int width = 40 + disk * 25;
                int x = 150 + r * 200 - width / 2;
                int y = 350 - (i + 1) * 25;

                g.setColor(Color.BLUE);
                g.fillRoundRect(x, y, width, 22, 10, 10);

                g.setColor(Color.WHITE);
                g.drawString("" + disk, x + width / 2, y + 16);
            }
        }

        g.setColor(Color.BLACK);
        g.drawString("Rod A", 130, 380);
        g.drawString("Rod B", 330, 380);
        g.drawString("Rod C", 530, 380);
    }

    // Move a disk between rods
    void moveDisk(int from, int to) {
        int disk = rods[from][--count[from]];
        rods[to][count[to]++] = disk;

        repaint();

        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Recursive Tower of Hanoi solution
    void solve(int n, int source, int auxiliary, int destination) {

        if (n == 1) {
            moveDisk(source, destination);
            return;
        }

        solve(n - 1, source, destination, auxiliary);

        moveDisk(source, destination);

        solve(n - 1, auxiliary, source, destination);
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Tower of Hanoi");

        TowerOfHanoiGUI tower = new TowerOfHanoiGUI();

        frame.add(tower);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        new Thread(() -> {
            tower.solve(3, 0, 1, 2);
        }).start();
    }
}
