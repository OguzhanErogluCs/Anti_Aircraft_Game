import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private int B_WIDTH;
    private int B_HEIGHT;
    private final int INITIAL_X = -40;
    private final int INITIAL_Y = 0;
    private final int DELAY = 25;

    private Image aircraft;
    private Image background;
    private Image tower;
    private Timer timer;
    private int x, y;

    public Board() {

        initBoard();
    }

    private void loadImage() {

        ImageIcon ii_aircraft = new ImageIcon("imgs/aircraft.png");
        aircraft = ii_aircraft.getImage();
        aircraft = aircraft.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH);
        ImageIcon ii_background = new ImageIcon("imgs/background.png");
        background = ii_background.getImage();
        ImageIcon ii_tower = new ImageIcon("imgs/tower.png");
        tower = ii_tower.getImage();
        tower = tower.getScaledInstance(200, 200,  java.awt.Image.SCALE_SMOOTH);
    }

    private void initBoard() {

        loadImage();

        B_WIDTH = background.getWidth(this);
        B_HEIGHT = background.getHeight(this);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        x = INITIAL_X;
        y = INITIAL_Y;

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawAircraft(g);
    }

    private void drawAircraft(Graphics g) {

        g.drawImage(background, 0, 0, null);
        g.drawImage(aircraft, x, y, this);
        g.drawImage(tower,B_WIDTH/2-300, (B_HEIGHT/2)+110, null);
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        x += 1;

        if (x > B_WIDTH) {

            y = INITIAL_Y;
            x = INITIAL_X;
        }

        repaint();
    }
}