package game.competition;

import java.lang.reflect.InvocationTargetException;

public class SkiCompetitionEngineer {
    private ISkiCompetitionBuilder builder;

    /**
     * constructor
     * @param builder
     */
    public SkiCompetitionEngineer(ISkiCompetitionBuilder builder){
        this.builder = builder;
    }

    /**
     * get default competition
     * @return
     */
    public Competition getDefCompetition(){
        return builder.getDefaultCompetition();
    }

    /**
     * constructing default competition
     */
    public void constructSkiCompetition(){
        try {
            builder.buildArena();
            builder.buildCompetitors();
            builder.buildMaxCompetitors();
        }catch(NoSuchMethodException | ClassNotFoundException | SecurityException | InstantiationException | IllegalAccessException | InvocationTargetException e){
            System.out.println(e);
        }
    }
}
