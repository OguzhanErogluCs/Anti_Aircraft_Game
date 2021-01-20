public class Bullet {

    private int xPos;
    private int yPos;
    private int width;
    private int height;
    private double xVelocity;
    private double yVelocity;

    public Bullet(int xPos, int yPos, int width, int height){
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
    }

    void update(final Board b){
        int x = b.getMouseX() - b.getWidth()/2;
        int y = -(b.getMouseY() - b.getHeight());
        xPos += x/30;
        yPos -= y/30;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
