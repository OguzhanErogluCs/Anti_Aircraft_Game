public class Aircraft {

    private int y;
    private int x;
    private int move;


    public Aircraft(int y, int x, int move){
        this.y = y;
        this.x = x;
        this.move = move;
    }

    public int getX() {

        return x;
    }

    public void setX(int x){
        this.x = x;
    }

    public int getY() {

        return y;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getMove() {

        return move;
    }

    public void setMove(int move){
        this.move = move;
    }

}
