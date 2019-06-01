package game.entities.sportsman;

import game.enums.Discipline;
import game.enums.Gender;

import java.awt.*;

public class Snowboarder extends WinterSportsman {
    /**
     * Constructor.
     * @param name
     * @param age
     * @param gender
     * @param acceleration
     * @param maxSpeed
     * @param discipline
     */
    public Snowboarder(String name, double age, Gender gender, double acceleration, double maxSpeed, Discipline discipline, Color color, int ID){
        super(maxSpeed,acceleration,name,age,gender,discipline,color,ID);
    }

    /**
     * void
     * @return name of the snowboarder
     */
    public String toString() {
        return "Snowboarder: " + getName() + ", " + getAge() +
                ", " + getGender() + ", " + getAcceleration() + ", " + getMaxSpeed() +
                ", " + getDiscipline();
    }

}
