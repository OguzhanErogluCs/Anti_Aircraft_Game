import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

public class Board extends JPanel {

    private int B_WIDTH;
    private int B_HEIGHT;
    private final int INITIAL_X = -40;
    private int DELAY = 40;
    private Image aircraftImage;
    private Image background;
    private Image tower;
    private Timer timer;
    private List<Aircraft> aircrafts = new ArrayList<>();

    Random random = new Random();

    public Board() {

        initBoard();
    }

    private void loadImage() {

        ImageIcon ii_background = new ImageIcon("imgs/background.png");
        background = ii_background.getImage();
        ImageIcon ii_tower = new ImageIcon("imgs/tower.png");
        tower = ii_tower.getImage();
        tower = tower.getScaledInstance(200, 200,  java.awt.Image.SCALE_SMOOTH);
        ImageIcon ii_aircraft = new ImageIcon("imgs/aircraft.png");
        aircraftImage = ii_aircraft.getImage();
        aircraftImage = aircraftImage.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH);
    }

    private void initBoard() {

        loadImage();

        B_WIDTH = background.getWidth(this);
        B_HEIGHT = background.getHeight(this);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

        timer = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int change = random.nextInt(110);
                if (change == 7 && aircrafts.size() < 10){
                    createAircraft();
                }
                if (aircrafts != null) {

                    List<Aircraft> willBeRemoved = new ArrayList<>();

                    for (Aircraft aircraft : aircrafts) {
                        int move = aircraft.getMove();
                        aircraft.setX(aircraft.getX() + move);

                        if (aircraft.getX() > B_WIDTH) {
                            willBeRemoved.add(aircraft);
                        }

                        repaint();
                    }
                    for (Aircraft aircraft : willBeRemoved){
                        aircrafts.remove(aircraft);
                    }
                }
            }
        });
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawAircraft(g);
    }

    private void createAircraft(){
        int randYLoc = random.nextInt(B_HEIGHT/3);
        int randMove = random.nextInt(10);
        aircrafts.add(new Aircraft(randYLoc, INITIAL_X, randMove));
        }


    private void drawAircraft(Graphics g) {

        g.drawImage(background, 0, 0, null);
        g.drawImage(tower,B_WIDTH/2-300, (B_HEIGHT/2)+110, null);
        if (aircrafts != null){
            for(Aircraft aircraft : aircrafts){
                g.drawImage(aircraftImage, aircraft.getX(), aircraft.getY(), this);
            }
        }
        Toolkit.getDefaultToolkit().sync();
    }

}