import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

public class Board extends JPanel implements MouseListener {

    private int B_WIDTH;
    private int B_HEIGHT;
    private final int INITIAL_X = -40;
    private int DELAY = 20;
    private int score = 0;
    private Image aircraftImage;
    private Image background;
    private Image tower;
    private Image bullet;
    private Timer timer;
    private int mouseX;
    private int mouseY;
    private int turretX;
    private int turretY;
    private List<Aircraft> aircrafts = new ArrayList<>();
    private List<Bullet> bullets = new ArrayList<>();
    private int lose = 0;

    JLabel l = new JLabel();

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
        ImageIcon ii_bullet = new ImageIcon("imgs/bullet.png");
        bullet = ii_bullet.getImage();
        bullet = bullet.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH);

    }

    private void initBoard() {

        loadImage();

        B_WIDTH = background.getWidth(this);
        B_HEIGHT = background.getHeight(this);
        turretX = B_WIDTH/2-210;
        turretY = (B_HEIGHT/2)+110;
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));


        l.setText("Score: " + String.valueOf(score));
        JPanel p = new JPanel();
        p.add(l);
        this.add(p);

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
                            lose++;
                        }
                        willBeRemoved = checkCollisions(willBeRemoved);
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

    public List<Aircraft> checkCollisions(List<Aircraft> list) {
        List<Bullet> willBeRemovedb = new ArrayList<>();
        for (Bullet b : bullets) {

            Rectangle r1 = new Rectangle(b.getxPos(), b.getyPos(), b.getWidth(), b.getHeight());

            for (Aircraft a : aircrafts) {

                Rectangle r2 = new Rectangle(a.getX(), a.getY(), 120, 120);

                if (r1.intersects(r2)) {
                    score++;

                    l.setText("Score: " + String.valueOf(score));

                    list.add(a);
                    willBeRemovedb.add(b);
                }
            }
        }
        for (Bullet b : willBeRemovedb){
            bullets.remove(b);
        }
        return list;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (lose<3){
            draw(g);
        }else{
            drawGameOver(g);
        }
    }

    private void drawGameOver(Graphics g) {

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 30);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.red);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2,
                B_HEIGHT / 2);
    }

    private void createAircraft(){
        int randYLoc = random.nextInt(B_HEIGHT/3);
        int randMove = random.nextInt(10)+1;
        aircrafts.add(new Aircraft(randYLoc, INITIAL_X, randMove));
        }

    private void draw(Graphics g) {

        g.drawImage(background, 0, 0, null);
        g.drawImage(tower,B_WIDTH/2-300, (B_HEIGHT/2)+110, null);
        //g.drawImage(bullet,B_WIDTH/2-210, (B_HEIGHT/2)+110, null);
        if (aircrafts != null){
            for(Aircraft aircraft : aircrafts){
                g.drawImage(aircraftImage, aircraft.getX(), aircraft.getY(), this);
            }
        }
        if (bullets != null){
            List<Bullet> willBeRemovedB = new ArrayList<>();
            for(Bullet bulet : bullets){
                g.drawImage(bullet, bulet.getxPos(), bulet.getyPos(), this);
                bulet.update(this);
                if (bulet.getxPos()<0 || bulet.getyPos()<0 || bulet.getxPos()>B_WIDTH){
                    willBeRemovedB.add(bulet);
                }
            }
            for (Bullet bulet : willBeRemovedB){
                bullets.remove(bulet);
            }

        }
        Toolkit.getDefaultToolkit().sync();
    }

    public int getMouseX() {
        return mouseX;
    }

    public void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public void setMouseY(int mouseY) {
        this.mouseY = mouseY;
    }

    public int getTurretX() {
        return turretX;
    }

    public void setTurretX(int turretX) {
        this.turretX = turretX;
    }

    public int getTurretY() {
        return turretY;
    }

    public void setTurretY(int turretY) {
        this.turretY = turretY;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Bullet playerBullet = new Bullet(turretX, turretY, 40, 40);
        bullets.add(playerBullet);

        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}