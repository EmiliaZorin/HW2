package game.entities.sportsman;

import game.enums.Discipline;
import game.enums.Gender;

import java.awt.*;

public class Skier extends WinterSportsman {
    /**
     * Constructor
     * @param name
     * @param age
     * @param gender
     * @param acceleration
     * @param maxSpeed
     * @param discipline
     */
    public Skier(String name, double age, Gender gender, double acceleration, double maxSpeed, Discipline discipline, Color color, int ID) {
        super(maxSpeed, acceleration, name, age, gender, discipline,color,ID);
    }

    /**
     * void
     * @return name of the Skier.
     */
    public String toString() {
        return "Skier: " + getName() + ", " + getAge() +
                ", " + getGender() + ", " + getAcceleration() + ", " + getMaxSpeed() +
                ", " + getDiscipline();
    }
}
