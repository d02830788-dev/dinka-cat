import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Main {

    private static double scale = 1.0;
    private static boolean growing = true;
    private static boolean catBlinking = false;
    private static int catBlinkCounter = 0;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Для моей малышки");
        Dimension size = new Dimension(700, 850);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        frame.setSize(size);
        frame.setPreferredSize(size);
        frame.setMaximumSize(size);
        frame.setMinimumSize(size);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        javax.swing.JLabel loveLabel = new javax.swing.JLabel("Я тебя люблю, Малышка!");
        loveLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loveLabel.setFont(new Font("Georgia", Font.BOLD, 36));
        loveLabel.setForeground(new Color(225, 40, 40));

        JPanel heartCanvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(230, 40, 40));

                int x = getWidth() / 2;
                int y = getHeight() / 2 - 20;

                double s = scale * 7.5;

                GeneralPath path = new GeneralPath();

                double xStart = x;
                double yStart = y + 3 * s;
                path.moveTo(xStart, yStart);

                path.curveTo(x - 2 * s, y - 9 * s, x - 15 * s, y - 11 * s, x - 15 * s, y - 2 * s);
                path.curveTo(x - 15 * s, y + 6 * s, x - 5 * s, y + 13 * s, x, y + 22 * s);
                path.curveTo(x + 5 * s, y + 13 * s, x + 15 * s, y + 6 * s, x + 15 * s, y - 2 * s);
                path.curveTo(x + 15 * s, y - 11 * s, x + 2 * s, y - 9 * s, xStart, yStart);

                path.closePath();
                g2.fill(path);
            }
        };
        heartCanvas.setBackground(Color.WHITE);
        heartCanvas.setMaximumSize(new Dimension(700, 400));
        heartCanvas.setPreferredSize(new Dimension(700, 400));
        heartCanvas.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel catCanvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(120, 120, 120));
                g2.setStroke(new java.awt.BasicStroke(3f));

                int x = getWidth() / 2;
                int y = getHeight() / 2;

                int[] xLeftEar = {x - 50, x - 45, x - 20};
                int[] yLeftEar = {y - 20, y - 55, y - 35};
                g2.drawPolygon(xLeftEar, yLeftEar, 3);

                int[] xRightEar = {x + 50, x + 45, x + 20};
                int[] yRightEar = {y - 20, y - 55, y - 35};
                g2.drawPolygon(xRightEar, yRightEar, 3);

                g2.drawOval(x - 55, y - 35, 110, 70);

                g2.drawArc(x - 12, y + 5, 12, 12, 180, 180);
                g2.drawArc(x, y + 5, 12, 12, 180, 180);

                if (catBlinking) {
                    g2.drawLine(x - 35, y - 5, x - 15, y - 5);
                    g2.drawLine(x + 15, y - 5, x + 35, y - 5);
                } else {
                    g2.drawArc(x - 35, y - 15, 20, 20, 0, 180);
                    g2.drawArc(x + 15, y - 15, 20, 20, 0, 180);
                }

                g2.drawLine(x - 50, y, x - 75, y - 5);
                g2.drawLine(x - 50, y + 5, x - 78, y + 7);

                g2.drawLine(x + 50, y, x + 75, y - 5);
                g2.drawLine(x + 50, y + 5, x + 78, y + 7);
            }
        };
        catCanvas.setBackground(Color.WHITE);
        catCanvas.setMaximumSize(new Dimension(700, 150));
        catCanvas.setPreferredSize(new Dimension(700, 150));
        catCanvas.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(loveLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(heartCanvas);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(catCanvas);
        panel.add(Box.createVerticalGlue());

        frame.getContentPane().add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Timer timer = new Timer(20, e -> {
            if (growing) {
                scale += 0.008;
                if (scale >= 1.15) {
                    growing = false;
                }
            } else {
                scale -= 0.012;
                if (scale <= 0.95) {
                    growing = true;
                }
            }

            heartCanvas.repaint();

            catBlinkCounter++;
            if (!catBlinking && catBlinkCounter >= 45) {
                catBlinking = true;
                catCanvas.repaint();
                catBlinkCounter = 0;
            } else if (catBlinking && catBlinkCounter >= 6) {
                catBlinking = false;
                catCanvas.repaint();
                catBlinkCounter = 0;
            }
        });

        timer.start();
    }
}
