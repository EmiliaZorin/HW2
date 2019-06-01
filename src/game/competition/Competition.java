package game.competition;

import game.arena.IArena;
import game.entities.IMobileEntity;
import game.entities.sportsman.WinterSportsman;
import utilities.ValidationUtils;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Competition implements Observer {
    private IArena arena;
    private int maxCompetitors;
    private ArrayList<Competitor>activeCompetitors;
    private ArrayList<Competitor>finishedCompetitors;

    /**
     * default constructor
     */
    public Competition(){
        activeCompetitors = new ArrayList<>();
        finishedCompetitors = new ArrayList<>();
    }
    /**
     * constructor
     * @param arena
     * @param maxCompetitors
     */
    public Competition(IArena arena, int maxCompetitors) {
        activeCompetitors = new ArrayList<>();
        finishedCompetitors = new ArrayList<>();
        if(WinterSportsman.getStaticArena()!=null) {
            setArena(arena);
        }
        setMaxCompetitors(maxCompetitors);
    }

    /**
     * get
     * @return activeCompetitors ArrayList
     */
    public ArrayList<Competitor> getActiveCompetitors() {
        return activeCompetitors;
    }

    /**
     * get
     * @return finishedCompetitors ArrayList
     */
    public ArrayList<Competitor> getFinishedCompetitors() {
        return finishedCompetitors;
    }

    /**
     * get
     * @return arena
     */
    public IArena getArena() {
        return arena;
    }

    /**
     * set
     * @param arena
     * @throws IllegalArgumentException
     */
    public void setArena(IArena arena) throws IllegalArgumentException {
        ValidationUtils.assertNotNull(arena);
        this.arena = arena;
    }

    /**
     * set
     * @return maxCompetitors
     */
    public int getMaxCompetitors() {
        return maxCompetitors;
    }

    /**
     * set
     * @param maxCompetitors
     * @throws IllegalArgumentException
     */
    public void setMaxCompetitors(int maxCompetitors) throws IllegalArgumentException {
        ValidationUtils.assertNotNegative(maxCompetitors);
        this.maxCompetitors = maxCompetitors;
    }

    /**
     * Checking whether the racer is valid to compete in the race.
     * @param competitor
     * @return true\false
     * @throws IllegalArgumentException
     */
    public boolean isValidCompetitor(Competitor competitor) throws IllegalArgumentException {
        try {
            ValidationUtils.checkValidCompetitor(this, competitor);
        }catch (IllegalArgumentException e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    /**
     * Adding the competitor to the race only if he is a valid competitor or if there is room in the competition.
     * @param competitor
     */
    public void addCompetitor(Competitor competitor) {
        ValidationUtils.assertNotNull(competitor);
        competitor.initRace();
        if (activeCompetitors.size() <= getMaxCompetitors()) {
            if (isValidCompetitor(competitor)) {
                activeCompetitors.add(competitor);
                ((Observable) competitor).addObserver(this);
            }
        }
        else
            throw new IllegalStateException(this.getArena() +  " is full max = " + maxCompetitors);
    }

    /**
     * Each competitor makes a move.
     */
    public void playTurn(){
        for(int i=0;i<activeCompetitors.size();i++)
            (new Thread(activeCompetitors.get(i))).start();
    }

    /**
     * Checking if there are competitors in the race.
     * @return true\false
     */
    public boolean hasActiveCompetitors(){
        return activeCompetitors.size()>0;
    }

    /**
     * this function is responsible for updating the arrays of the competitors. when a competitor finishes the race
     * he needs to be transferred from active competitors to finished competitors.
     * If two competitors finish the race in the same time 'synchronized' will take care of that.
     * @param o
     * @param args
     */
    @Override
    public synchronized void update(Observable o, Object args){
        if(arena.isFinished((IMobileEntity) o)){
            if(activeCompetitors.contains(o)){
                finishedCompetitors.add((Competitor)o);
                activeCompetitors.remove(o);
            }
        }
    }
}
