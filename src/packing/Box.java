package packing;

import order.packing.IBox;

/**
 *
 * @author Filipa
 */
public class Box implements IBox{
    private int Depth;
    private int Height;
    private int Lenght;
    private int Volume;

    public Box(int Depth, int Height, int Lenght, int Volume) {
        this.Depth = Depth;
        this.Height = Height;
        this.Lenght = Lenght;
        this.Volume = Depth * Height * Lenght;
    }
    
    @Override
    public int getDepth() {
        return this.Depth;
    }

    @Override
    public int getHeight() {
        return this.Height;
    }

    @Override
    public int getLenght() {
        return this.Lenght;
    }

    @Override
    public int getVolume() {
        return this.Volume;
    }
    
    /**
     *
     * @return
     */
    @Override
    public String toString(){
        return "Depth: " + this.Depth + 
               "\nHeight: " + this.Height + 
               "\nLenght: " + this.Lenght + 
               "\nVolume: " + this.Volume;
    }
}
