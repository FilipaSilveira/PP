package packing;

import order.exceptions.ContainerException;
import order.exceptions.PositionException;
import order.packing.Color;
import order.packing.IContainer;
import order.packing.IItem;
import order.packing.IItemPacked;
import order.packing.IPosition;
import order.exceptions.ContainerException;

/**
 *
 * @author Filipa
 */
public class Container extends Box implements IContainer{

    private ItemPacked[] item;
    private int NumItems = 0;
    private static int id = 0;
    private static final int MAX_ITEMS = 50;
    private String reference = "container";
    private statusContainer status = statusContainer.OPEN;

    public Container(int Depth, int Height, int Lenght, int Volume) {
        super(Depth, Height, Lenght, Volume);
        this.item = new ItemPacked[MAX_ITEMS];
        this.reference += this.id;
        this.id++;
    }
    
    
    
    @Override
    public boolean addItem(IItem iitem, IPosition ip, Color color) throws ContainerException {
        if(this.NumItems < MAX_ITEMS){
            this.item[this.NumItems] = new ItemPacked(ip,color,iitem);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeItem(IItem iitem) throws ContainerException {
        for (int i = 0; i < this.NumItems; i++) {
            if (this.item[i] == iitem) {
                for (int j = i; j < this.NumItems - 1; j++) {
                    this.item[i] = this.item[i + 1];
                    this.NumItems--;
                }
                return true;
            }
        }
            return false;
    }
    /*
    Validates the container structure considering:
    if the volume if lesser or equal to the current volume
    if all items are inside the container
    if non of the items inside the container are overlapping
    Throws:
    ContainerException - if the volume greater than the current volume
    PositionException - if some item is outside (or is overflowing) the 
    container or if some item is overlapping with other item
    */
    
    public boolean isInsideContainer(){
        
    }
    
    @Override
    public void validate() throws ContainerException, PositionException {
        if(getRemainingVolume() >= 0){ //não tem nada fora
            for(int i=0; i<this.NumItems; i++){
                if(this.item[i].getPosition().getX() + this.item[i].getItem().getLenght()){
                
                }
            }
        }else{
            throw new ContainerException();
        }
    }

    @Override
    public void close() throws ContainerException, PositionException {
        this.status = statusContainer.CLOSED;
    }

    @Override
    public IItem getItem(String string) {
        for(int i=0; i<this.NumItems; i++){
            if(this.item[i].getItem().getReference().equals(string)){
                return this.item[i].getItem();
            }
        }
        return null;
    }

    @Override
    public int getOccupiedVolume() {
        int occupiedVolume = 0;
        for(int i=0; i<this.NumItems; ++i){
            occupiedVolume += this.item[i].getItem().getVolume();
        }
        return occupiedVolume;
    }

    @Override
    public IItemPacked[] getPackedItems() {
        return this.item;
    }

    @Override
    public String getReference() {
        return this.reference;
    }

    @Override
    public int getNumberOfItems() {
        return this.NumItems;
    }

    @Override
    public int getRemainingVolume() {
        return (this.getVolume())-(this.getOccupiedVolume());
    }

    @Override
    public boolean isClosed() {
        if(this.status == statusContainer.CLOSED){
            return true;
        }
        return false;
    }
    
}
