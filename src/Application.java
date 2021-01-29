/**
 * <h1>Anti Aircraft Game!</h1>
 * The Anti_Aircraft_Game program implements an application that
 * creates random aircrafts and when mouse clicked, creates bullets towards mouse icon.
 * If 3 of the random aircrafts passes the map, games over.
 * Maximum aircraft on the map can be 10.
 * <p>
 * @author  Oguzhan Eroglu
 * @version 2.0
 * @since   2021-01-20
 */

import java.awt.EventQueue;
import javax.swing.*;

public class Application extends JFrame {

    public Application() {

        initUI();
    }

    /**
     * This method is used to initalize user interface. This method
     * creates an instance of Board class and adds mouse listener to it,
     * then adds board to interface.
     * @return Nothing
     */

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
    /**
     * This is the main method which creates an instance of Application class.
     * @param args Unused.
     * @return Nothing.
     */
    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            Application app = new Application();
            app.setVisible(true);
        });
    }
}