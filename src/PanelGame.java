import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.Instant;
import java.util.Random;

public class PanelGame extends JPanel implements ActionListener {

    static final int width = 800;
    static final int height = 600;
    static final int sizeSquare = 25;
    static final int delay = 100;
    static final int Body_units = height * width / sizeSquare;
    boolean bugsInside = false;
    int bugsX;
    int bugsY;
    int bodyPart = 5;
    char direction = 'D';
    int score;
    boolean gameRun = false;
    Timer timer;
    Random random;
    int x[] = new int[Body_units];
    int y[] = new int[Body_units];

    PanelGame() {
        random = new Random();
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKey());
        startGame();
    }

    public void startGame() {
        newBugs();
        timer = new Timer(delay, this);
        gameRun = true;
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if(gameRun) {
            /// siatka
            for (int i = 0; i < height / sizeSquare; i++) {
                g.drawLine(0, i * sizeSquare, width, i * sizeSquare);
            }
            for (int i = 0; i < width / sizeSquare; i++) {

                g.drawLine(i * sizeSquare, 0, i * sizeSquare, height);
            }
            /// robak
            g.setColor(Color.PINK);
            g.fillOval(bugsX, bugsY, sizeSquare, sizeSquare);

            /// rysowanie węża

            for (int i = 0; i < bodyPart; i++) {
                if (i % 2 == 0) {
                    g.setColor(Color.green);
                } else {
                    g.setColor(Color.PINK);
                }
                g.fillRect(x[i], y[i], sizeSquare, sizeSquare);

                /// score
                g.setColor(Color.red);
                g.setFont(new Font("ITALIC", Font.BOLD, 40));
                FontMetrics metrics = getFontMetrics(g.getFont());
                g.drawString("Score: " + score, (width - metrics.stringWidth("Score: " + score)) / 2, g.getFont().getSize());

            }
        }
        else {
            gameOver(g);
        }

    }

    public void move() {
        for (int i = bodyPart; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        /// cross borderline
        if (x[0] >= width && direction == 'R') {
            x[0] = 0 - sizeSquare;
        } else if (x[0] <= 0 && direction == 'L') {
            x[0] = width;
        }

        if (y[0] >= height && direction == 'D') {
            y[0] = 0 - sizeSquare;
        } else if (y[0] <= 0 && direction == 'U') {
            y[0] = height;
        }

        switch (direction) {
            case 'U':
                y[0] = y[0] - sizeSquare;
                break;
            case 'D':
                y[0] = y[0] + sizeSquare;
                break;
            case 'L':
                x[0] = x[0] - sizeSquare;
                break;
            case 'R':
                x[0] = x[0] + sizeSquare;
                break;
        }
    }

    public void newBugs() {
        do {
            bugsX = random.nextInt(width / sizeSquare) * sizeSquare;
            bugsY = random.nextInt(height / sizeSquare) * sizeSquare;
            for (int i = bodyPart; i > 0; i--) {
                if (x[i] == bugsX || y[i] == bugsY) {
                    bugsInside = true;
                } else {
                    bugsInside = false;
                }
            }
        }
        while (bugsInside);
    }

    public void checkbugs() {
        if (x[0] == bugsX && y[0] == bugsY) {
            bodyPart++;
            score++;
            newBugs();
        }
    }

    public void checkCollision() {
        for (int i = bodyPart; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                gameRun = false;
            }
        }
        if (!gameRun) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        //Score
        g.setColor(Color.red);
        g.setFont(new Font("ITALIC", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + score, (width - metrics1.stringWidth("Score: " + score)) / 2, g.getFont().getSize());
        //Game Over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (width - metrics2.stringWidth("Game Over")) / 2, height / 2);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (gameRun) {
            move();
            checkbugs();
            checkCollision();
        }
        repaint();

    }

    public class MyKey extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
            }

        }
    }
}
