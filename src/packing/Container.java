package packing;

import exceptions.*;
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
    private containerStatus status = containerStatus.OPEN;
    private int occupiedVolume = 0;

    public Container(int Depth, int Height, int Lenght, int Volume) {
        super(Depth, Height, Lenght, Volume);
        this.item = new ItemPacked[MAX_ITEMS];
        this.reference += this.id;
        this.id++;
    }

    
    @Override
    public boolean addItem(IItem iitem, IPosition ip, Color color) throws containerException {
        if( (iitem != null && ip != null && color != null) || this.isClosed() ){
            if(this.NumItems < MAX_ITEMS){
                this.item[this.NumItems] = new ItemPacked(ip,color,iitem);
                this.NumItems ++;
                this.updateOccupiedVolume();
                return true;
            }// TODO: else aumentar o tamanho do array
            return false;
        }else{
            throw new containerException();
        }
        
    }

    @Override
    public boolean removeItem(IItem iitem) throws ContainerException {
        if( (iitem != null) || this.isClosed() ){
            for (int i = 0; i < this.NumItems; i++) {
                if (this.item[i] == iitem) {
                    for (int j = i; j < this.NumItems - 1; j++) {
                        this.item[i] = this.item[i + 1];
                    }
                    this.NumItems--;
                    this.updateOccupiedVolume();
                    return true;
                }
            }
        }else{
            throw new containerException();
        }
        return false;
    }
    
    /**
     * retorna True se houver algo fora do contentor
     * se não retorna False 
     * @return 
     */
    public boolean isOutsideContainer(){
        for(int i=0; i<this.NumItems; i++){
            // se estiver fora do contentor
            if(this.item[i].getPosition().getX() + this.item[i].getItem().getLenght() > this.getLenght() 
               || this.item[i].getPosition().getY() + this.item[i].getItem().getDepth() > this.getDepth() 
               || this.item[i].getPosition().getZ() + this.item[i].getItem().getHeight() > this.getHeight()){ 
                return true;
            }
        }
        return false;
    }
    
    /**
     * True -> interceta
     * False -> não interceta
     * @return 
     */
    public boolean isOverlaping(){
        for(int i=0; i<this.NumItems - 1; i++){
            for (int j = i + 1; j < this.NumItems; j++) {
                if(i != j){ // se forem caixas diferentes                  
                    /*
                    https://developer.mozilla.org/en-US/docs/Games/Techniques/3D_collision_detection
                    a -> i
                    b -> j
                    return (a.minX <= b.maxX && a.maxX >= b.minX) &&
                           (a.minY <= b.maxY && a.maxY >= b.minY) &&
                           (a.minZ <= b.maxZ && a.maxZ >= b.minZ);
                    */
                    if( (this.item[i].getPosition().getX() <= this.item[j].getPosition().getX() + this.item[j].getItem().getLenght() && this.item[i].getPosition().getX() + this.item[i].getItem().getLenght() >= this.item[j].getPosition().getX()) &&
                        (this.item[i].getPosition().getY() <= this.item[j].getPosition().getY() + this.item[j].getItem().getDepth()&& this.item[i].getPosition().getY() + this.item[i].getItem().getDepth() >= this.item[j].getPosition().getY()) &&
                        (this.item[i].getPosition().getZ() <= this.item[j].getPosition().getZ() + this.item[j].getItem().getHeight()&& this.item[i].getPosition().getZ() + this.item[i].getItem().getHeight() >= this.item[j].getPosition().getZ())
                      ){
                        return true; // intercepta
                    }
                }
            }
        }
        return false; // não intercepta
    }
    

    @Override
    public void validate() throws containerException, positionException{
        if(getRemainingVolume() >= 0){ //não tem nada fora
            /*for(int i=0; i<this.NumItems; i++){
                // se estiver fora do contentor
                if(this.item[i].getPosition().getX() + this.item[i].getItem().getLenght() > this.getLenght() 
                   || this.item[i].getPosition().getY() + this.item[i].getItem().getDepth() > this.getDepth() 
                   || this.item[i].getPosition().getZ() + this.item[i].getItem().getHeight() > this.getHeight()){ 
                    throw new positionException();
                }
            }*/
            if (isOutsideContainer()){
                throw new positionException();
            }
            
            // comparação entre caixas para overlaping
            /*for(int i=0; i<this.NumItems - 1; i++){
                for (int j = i + 1; j < this.NumItems; j++) {
                    if(i != j){ // se forem caixas diferentes                  
                        if( (this.item[i].getPosition().getX() <= this.item[j].getPosition().getX() + this.item[j].getItem().getLenght() && this.item[i].getPosition().getX() + this.item[i].getItem().getLenght() >= this.item[j].getPosition().getX()) &&
                            (this.item[i].getPosition().getY() <= this.item[j].getPosition().getY() + this.item[j].getItem().getDepth()&& this.item[i].getPosition().getY() + this.item[i].getItem().getDepth() >= this.item[j].getPosition().getY()) &&
                            (this.item[i].getPosition().getZ() <= this.item[j].getPosition().getZ() + this.item[j].getItem().getHeight()&& this.item[i].getPosition().getZ() + this.item[i].getItem().getHeight() >= this.item[j].getPosition().getZ())
                          ){
                            throw new positionException();
                        }
                    }
                }
            }*/
            if(isOverlaping()){
                throw new positionException();
            }
            
        }else{
            throw new containerException();
        }
    }

    // usar validate() para ver se se pode fechar o contentor ???????????
    @Override
    public void close() throws containerException, positionException {
        if ( this.getOccupiedVolume() > this.getVolume() ){ // if volume is greater than current volume
            throw new containerException();
        }
        if(isOutsideContainer()){
            throw new positionException();
        }
        if(isOverlaping()){
            throw new positionException();
        }
        this.status = containerStatus.CLOSED;
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

    public void updateOccupiedVolume() {
        int occupied_Volume = 0;
        for(int i=0; i<this.NumItems; ++i){
            occupied_Volume += this.item[i].getItem().getVolume();
        }
        this.occupiedVolume = occupied_Volume;
    }
    
    @Override
    public int getOccupiedVolume() {
        return this.occupiedVolume;
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
        if(this.status == containerStatus.CLOSED){
            return true;
        }
        return false;
    }
    
    @Override
    public String toString(){
        
        String string = "";
        
        System.out.println(this.NumItems);
        for(int i = 0; i < this.NumItems; i++){
            
            string += this.item[i].getItem().getDescription();
        }
       
        return "Dimensions(L,D,H,V): " + this.getLenght() +", "+  this.getDepth() +", "+ this.getHeight() +", "+ this.getVolume() +", "+ 
               "\nReference: " + this.reference + 
               "\nEstado: " + this.status + 
               "\nItems:" + string;
    }
}
