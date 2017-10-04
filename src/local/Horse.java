package local;

/**
 * Created by user on 16.04.2017.
 */
public class Horse {
    private String name;
    private double speed;
    private double distance;
    private boolean acceleration;
    private int visualState;

    public Horse(String name, double speed) {
        this.name = name;
        this.speed = speed;
    }

    public double getDistance() {
        return distance;
    }

    public String getName() {
        return name;
    }

    public boolean isAcceleration() {
        return acceleration;
    }

    public int getVisualState() {
        return visualState;
    }

    public void move() {
        double dx = speed * Math.random();
        distance = distance + dx;

//        acceleration = !acceleration;

        visualState = ++visualState > 12 ? 0 : visualState;

//        if (dx > speed / 2) {
//            acceleration = true;
//        } else {
//            acceleration = false;
//        }
    }

    public void draw() {

    }

    @Override
    public String toString() {
        return name;
    }
}
