package game.entities.sportsman;

public class ColoredSportsman extends WSDecorator {
    /**
     * constructor
     * @param winterSportsman
     */
    public ColoredSportsman(IWinterSportsman winterSportsman){
        super(winterSportsman);
    }

}
