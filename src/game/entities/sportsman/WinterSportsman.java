package game.entities.sportsman;

import game.arena.Arena;
import game.arena.IArena;
import game.arena.WinterArena;
import game.competition.Competitor;
import game.enums.*;
import game.states.Completed;
import game.states.IState;
import game.states.Injured;
import utilities.GameApp;
import utilities.Point;
import utilities.ValidationUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Observer;
import java.util.Random;

public class WinterSportsman extends Sportsman implements Competitor,IWinterSportsman {
    private Discipline discipline;
    private IState currentState;

    public static IArena StaticArena;
    /**
     * static function to set static variable 'winterarena'.
     * @param arena
     */
    public static void setArenaStatic(IArena arena) {
        if(arena==null){
            StaticArena = new WinterArena(700, SnowSurface.POWDER, WeatherCondition.SUNNY);
        }
        else {
            StaticArena = arena;
        }
    }

    private ArrayList<Observer> observer=new ArrayList<>();

    /**
     * Constructor
     * @param maxSpeed
     * @param acceleration
     * @param name
     * @param age
     * @param gender
     * @param discipline
     */
    public WinterSportsman(double maxSpeed, double acceleration, String name, double age, Gender gender, Discipline discipline, Color color, int ID) {
        super(maxSpeed, acceleration + League.calcAccelerationBonus(age), name, age, gender,color,ID);
        setDiscipline(discipline);
        setArenaStatic(StaticArena);

    }

    /**
     * @return discipline
     */
    public Discipline getDiscipline() {
        return discipline;
    }

    /**
     * @param discipline
     * @throws IllegalArgumentException
     */
    public void setDiscipline(Discipline discipline) throws IllegalArgumentException {
        ValidationUtils.assertNotNull(discipline);
        this.discipline = discipline;
    }

    /**
     * Initiating the race by placing each competitor in the start point.
     */
    public void initRace() {
        int i = GameApp.currentCompetitorsAmount;
        int imageSize = 50;
        setLocation(new Point(0, (i + 1) * imageSize * 2));
    }
    /**
     * get
     * @return winterarena
     */
    public static IArena getStaticArena() {
        return StaticArena;
    }
    /**
     * run method for the competitors.
     */
    public void run() {
        while (getLocation().getX() < ((Arena) StaticArena).getLength()) {
            this.move(StaticArena.getFriction());
            faith((int)((Arena) StaticArena).getLength());
            setChanged();
            notifyObservers();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
    /**
     * this method adds an observer to his Observables.
     * @param l
     */
    public void registerObserver(Observer l) {
        observer.add(l);
    }

    /**
     * cloning method - used to clone competitors
     * @return WinterSportsman
     */
    public Object clone(){
        Object clone = null;
        try {
            clone = super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println(e);
        }
        return clone;
    }

    /**
     * set
     * @param color
     */
    @Override
    public void setColor(Color color){
        super.setColor(color);
    }

    /**
     * set
     * @param extraAcc
     */
    @Override
    public void setAcceleration(double extraAcc){
        super.setAcceleration(extraAcc);
    }

    /**
     * set
     * @param state
     */
    public void setState(IState state){
        this.currentState = state;
    }

    /**
     * get
     * @return
     */
    public IState getState(){
        return currentState;
    }

    /**
     * method decides the state of the competitor
     * @param Y
     */
    public void faith(int Y){
        int number;
        Random randomNumber = new Random();
        number = randomNumber.nextInt(10);
        if(number<=5){
            setState(new Completed());
        }
        else{
            setState(new Injured(Y));
        }
    }
}

