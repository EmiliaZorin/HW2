package game.entities.sportsman;

import java.awt.*;

public class WSDecorator implements IWinterSportsman {
    protected IWinterSportsman winterSportsman;

    /**
     * constructor
     * @param winterSportsman
     */
    public WSDecorator(IWinterSportsman winterSportsman){
        this.winterSportsman = winterSportsman;
    }

    /**
     * set
     * @param color
     */
    @Override
    public void setColor(Color color) {
        this.winterSportsman.setColor(color);
    }

    /**
     * set
     * @param extraAcc
     */
    @Override
    public void setAcceleration(double extraAcc) {
        this.winterSportsman.setAcceleration(extraAcc);
    }

}
