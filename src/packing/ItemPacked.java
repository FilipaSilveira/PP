package packing;

import order.packing.Color;
import order.packing.IItem;
import order.packing.IItemPacked;
import order.packing.IPosition;

/**
 *
 * @author Filipa
 */
public class ItemPacked implements IItemPacked{

    private IPosition position;
    private Color color;
    private IItem item;

    public ItemPacked(IPosition position, Color color, IItem item) {
        this.position = position;
        this.color = color;
        this.item = item;
    }
 
    
    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public IItem getItem() {
        return this.item;
    }

    @Override
    public IPosition getPosition() {
        return this.position;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void setPosition(IPosition ip) {
        this.position = ip;
    }
    
    @Override
    public String toString(){
        return "Color: " + this.color + 
               "\nItem: " + this.item.toString() + 
               "\nPosition: " + this.position;
    }
}
