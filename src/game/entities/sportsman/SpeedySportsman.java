package game.entities.sportsman;

public class SpeedySportsman  extends WSDecorator {
    /**
     * constructor
     * @param winterSportsman
     */
    public SpeedySportsman(IWinterSportsman winterSportsman){
        super(winterSportsman);
    }
}
