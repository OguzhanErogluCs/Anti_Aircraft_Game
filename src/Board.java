import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;


public class Board extends JPanel implements MouseListener {

    private int B_WIDTH;
    private int B_HEIGHT;
    private final int INITIAL_X = -40;
    private int DELAY = 40;
    private int score = 0;
    private Image aircraftImage;
    private Image background;
    private Image tower;
    private Image bullet;
    private Timer timer;
    private int mouseX = -1;
    private int mouseY = -1;
    private int turretX;
    private int turretY;
    private JLabel l = new JLabel();

    Random random = new Random();

    Aircraft aircraftContainer = new Aircraft();
    Bullet bulletContainer = new Bullet();

    public Board() {

        initBoard();
    }

    /**
     * This method is used to get currentPath.
     * @return String This returns current path which program runs.
     */
    protected String getPath(){
        return System.getProperty("user.dir");
    }

    /**
     * This method loads images of background, tower, aircraft, bullet and scales them to needed width and height.
     * @param path This is the current path which program runs.
     * @return Nothing.
     */

    private void loadImage(String path) {

        ImageIcon ii_background = new ImageIcon(path+"/imgs/background.png");
        background = ii_background.getImage();
        ImageIcon ii_tower = new ImageIcon(path+"/imgs/tower.png");
        tower = ii_tower.getImage();
        tower = tower.getScaledInstance(200, 200,  java.awt.Image.SCALE_SMOOTH);
        ImageIcon ii_aircraft = new ImageIcon(path+"/imgs/aircraft.png");
        aircraftImage = ii_aircraft.getImage();
        aircraftImage = aircraftImage.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH);
        ImageIcon ii_bullet = new ImageIcon(path+"/imgs/bullet.png");
        bullet = ii_bullet.getImage();
        bullet = bullet.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH);

    }

    /**
     * This method initializes the game board.
     * Adds score table to board.
     * Sets a timer to create random aircrafts regularly.
     * @return Nothing.
     */

    private void initBoard() {

        loadImage(getPath());

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
                int randomToCreate = random.nextInt(100);
                int randYLoc = random.nextInt(B_HEIGHT/3);
                int randMove = random.nextInt(10)+1;
                aircraftContainer.createAircraft(randYLoc, randMove, INITIAL_X, randomToCreate);
                updateAircrafts(aircraftContainer.getAircrafts());
            }
        });
        timer.start();
    }

    protected void updateAircrafts(List<Aircraft> a){
        if (a != null) {

            List<Aircraft> willBeRemoved = new ArrayList<>();

            for (Aircraft aircraft : a) {
                int move = aircraft.getMove();
                aircraft.setX(aircraft.getX() + move);

                willBeRemoved = aircraftContainer.checkPassings(B_WIDTH, willBeRemoved, aircraft);
                willBeRemoved = checkCollisions(willBeRemoved);
                repaint();
            }
            aircraftContainer.removeAircraft(willBeRemoved);
        }
    }

    /**
     * This method checks whether there is a collision or not between each aircraft and bullet pairs.
     * If there is a collision, it removes the bullet and update willBeRemoved list of aircraft.
     * @param list This is the willBeRemoved list of aircraft
     * @return List Returns updated willBeRemoved list of aircraft.
     */
    public List<Aircraft> checkCollisions(List<Aircraft> list) {
        List<Bullet> willBeRemovedb = new ArrayList<>();
        for (Bullet b : bulletContainer.getBullets()) {

            Rectangle r1 = new Rectangle(b.getxPos(), b.getyPos(), b.getWidth(), b.getHeight());

            for (Aircraft a : aircraftContainer.getAircrafts()) {

                Rectangle r2 = new Rectangle(a.getX(), a.getY(), 120, 120);

                if (r1.intersects(r2)) {
                    score++;

                    l.setText("Score: " + String.valueOf(score));

                    list.add(a);
                    willBeRemovedb.add(b);
                }
            }
        }
        bulletContainer.removeBullets(willBeRemovedb);
        return list;
    }

    /**
     * This method draws all components.
     * If three aircrafts did not pass the map, draw() function called,
     * else drawGameOver()
     * @return Nothing.
     */

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!aircraftContainer.getGameOver()){
            draw(g);
        }else{
            drawGameOver(g);
        }
    }

    /**
     * This method draws gameover screen and shows final score.
     * @return Nothing.
     */

    private void drawGameOver(Graphics g) {

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 30);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.red);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2,
                B_HEIGHT / 2);
    }

    /**
     * This method draws regular game screen and shows current score.
     * @return Nothing.
     */

    private void draw(Graphics g) {

        g.drawImage(background, 0, 0, null);
        g.drawImage(tower,B_WIDTH/2-300, (B_HEIGHT/2)+110, null);
        if (aircraftContainer.getAircrafts() != null){
            for(Aircraft aircraft : aircraftContainer.getAircrafts()){
                g.drawImage(aircraftImage, aircraft.getX(), aircraft.getY(), this);
            }
        }
        if (bulletContainer.getBullets() != null){
            List<Bullet> willBeRemovedB = new ArrayList<>();
            for(Bullet b : bulletContainer.getBullets()){
                g.drawImage(bullet, b.getxPos(), b.getyPos(), this);
                b.update(this);
                if (b.getxPos()<0 || b.getyPos()<0 || b.getxPos()>B_WIDTH){
                    willBeRemovedB.add(b);
                }
            }
            bulletContainer.removeBullets(willBeRemovedB);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    public JLabel getL() {
        return l;
    }

    public void setL(JLabel l) {
        this.l = l;
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

    public Image getAircraftImage() {
        return aircraftImage;
    }

    public Image getTower() {
        return tower;
    }

    public Image getBullet() {
        return bullet;
    }

    public void setB_WIDTH(int b_WIDTH) {
        B_WIDTH = b_WIDTH;
    }

    /**
     * This method creates a bullet object when mouse clicked.
     * @return Nothing.
     */

    @Override
    public void mouseClicked(MouseEvent e) {
        bulletContainer.createBullet(turretX, turretY, 40, 40);
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