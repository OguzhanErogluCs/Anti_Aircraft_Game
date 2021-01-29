import java.util.ArrayList;
import java.util.List;

public class Aircraft {

    private int y;
    private int x;
    private int move;
    static private List<Aircraft> aircrafts = new ArrayList<>();


    public Aircraft(int y, int x, int move){
        this.y = y;
        this.x = x;
        this.move = move;
    }

    public Aircraft(){

    }

    public void createAircraft(int randYLoc, int randMove, int INITIAL_X, int randomToCreate){
        if (randomToCreate == 7 && aircrafts.size() < 10){
            aircrafts.add(new Aircraft(randYLoc, INITIAL_X, randMove));
        }
    }

    public void removeAircraft(List<Aircraft> willBeRemoved){
        for (Aircraft aircraft : willBeRemoved){
            aircrafts.remove(aircraft);
        }
    }

    public int getX() {

        return x;
    }

    public void setX(int x){
        this.x = x;
    }

    public static List<Aircraft> getAircrafts() {
        return aircrafts;
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
