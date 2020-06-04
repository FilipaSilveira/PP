package packing;

import order.exceptions.PositionException;
import order.packing.IPosition;

/**
 *
 * @author Filipa
 */

public class Position implements IPosition {
    private int x;
    private int y;
    private int z;

    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int getZ() {
        return this.z;
    }

    @Override
    public void setX(int i) throws PositionException {
        this.x = i;
    }

    @Override
    public void setY(int i) throws PositionException {
        this.y = i;
    }

    @Override
    public void setZ(int i) throws PositionException {
        this.z = i;
    }
}
