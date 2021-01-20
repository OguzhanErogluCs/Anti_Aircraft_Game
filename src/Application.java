import java.awt.EventQueue;
import javax.swing.*;

public class Application extends JFrame {

    public Application() {

        initUI();
    }

    private void initUI() {
        Board board = new Board();
        addMouseListener(board);

        add(board);

        setResizable(false);
        pack();

        setTitle("Game");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            Application app = new Application();
            app.setVisible(true);
        });
    }
}