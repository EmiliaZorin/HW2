package game.entities;
import utilities.Point;

public interface IMobileEntity {
    void move(double friction);
    Point getThisLocation();
}
