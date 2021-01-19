import java.awt.EventQueue;
import javax.swing.JFrame;

public class Application extends JFrame {

    public Application() {

        initUI();
    }

    private void initUI() {

        add(new Board());

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