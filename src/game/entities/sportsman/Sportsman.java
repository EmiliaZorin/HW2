package game.entities.sportsman;

import game.entities.MobileEntity;
import game.enums.Gender;
import utilities.ValidationUtils;

import java.awt.*;

public abstract class Sportsman extends MobileEntity {

    private String name;
    private double age;
    private Gender gender;
    private Color color;
    private int ID;


    /**
     * Constructor
     * @param maxSpeed
     * @param acceleration
     * @param name
     * @param age
     * @param gender
     */
    public Sportsman(double maxSpeed, double acceleration,String name,double age,Gender gender,Color color,int ID){
        super(maxSpeed,acceleration);
        setName(name);
        setAge(age);
        setGender(gender);
        setColor(color);
        setID(ID);
    }

    /**
     * get
     * @return color
     */
    public Color getColor() {
        return color;
    }

    /**
     * get
     * @return String
     */
    public String getNameColor() {
        return color.toString();
    }

    /**
     * set
     * @param color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * get
     * @return ID
     */
    public int getID() {
        return ID;
    }

    /**
     * set
     * @param ID
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * get
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * set
     * @param name
     * @throws IllegalArgumentException
     */
    public void setName(String name) throws IllegalArgumentException {
        ValidationUtils.assertNotNullOrEmptyString(name);
        this.name = name;
    }

    /**
     * get
     * @return age
     */
    public double getAge() {
        return age;
    }

    /**
     * set
     * @param age
     * @throws IllegalArgumentException
     */
    public void setAge(double age) throws IllegalArgumentException{
        ValidationUtils.assertNotNegative(age);
        this.age = age;
    }

    /**
     * get
     * @return gender
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * set
     * @param gender
     * @throws IllegalArgumentException
     */
    public void setGender(Gender gender) throws IllegalArgumentException{
        try {
            ValidationUtils.assertNotNull(gender);
        }catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        this.gender = gender;
    }
}
