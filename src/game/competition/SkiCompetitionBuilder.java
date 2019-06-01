package game.competition;

import game.arena.ArenaFactory;
import game.entities.sportsman.WinterSportsman;
import game.enums.Discipline;
import game.enums.Gender;
import game.enums.SnowSurface;
import game.enums.WeatherCondition;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class SkiCompetitionBuilder implements ISkiCompetitionBuilder {
    private int competitorsAmount;
    private Competition DefaultCompetition;

    /**
     * constructor for default competition
     * @param maxCompetitors
     */
    public SkiCompetitionBuilder(int maxCompetitors){
        this.competitorsAmount = maxCompetitors;
        this.DefaultCompetition = new Competition();
        buildMaxCompetitors();
    }

    /**
     * build default arena
     */
    public void buildArena(){
        try{
        ArenaFactory arenaFactory = new ArenaFactory();
        getDefaultCompetition().setArena(arenaFactory.getArena("WinterArena",700, SnowSurface.POWDER, WeatherCondition.SUNNY));
        }catch(NoSuchMethodException | ClassNotFoundException | SecurityException | InstantiationException | IllegalAccessException | InvocationTargetException e){
            System.out.println(e);
        }
    }

    /**
     * build default competitors
     */
    public void buildCompetitors(){
        WinterSportsman cloneableCompetitor = new WinterSportsman(
                100,1.1,"Israela Israeli",24,
                Gender.FEMALE, Discipline.FREESTYLE, Color.BLACK,123456789);
        for(int i=0;i<competitorsAmount;i++) {
            getDefaultCompetition().addCompetitor(((Competitor)cloneableCompetitor.clone()));
        }
    }

    /**
     * build max competitors
     */
    public void buildMaxCompetitors() {
        getDefaultCompetition().setMaxCompetitors(competitorsAmount);
    }

    /**
     * get
     * @return competition
     */
    public Competition getDefaultCompetition(){
        return DefaultCompetition;
    }

}

