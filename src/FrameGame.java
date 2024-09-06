import javax.swing.*;

public class FrameGame extends JFrame {

    FrameGame() {
        PanelGame panel = new PanelGame();
        this.add(new PanelGame());
        this.setTitle("Snake");
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
    }
}
