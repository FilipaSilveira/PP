/*
* Nome: Ana Filipa Sousa Silveira
* Número: 8160040
* Turma: LSIRC
*
* Nome: Rafael António Alves Maia
* Número: 8160489
* Turma: LSIRC
*/

package packing;

import order.packing.IItem;

/**
 *
 * @author Filipa
 */
public class Item extends Box implements IItem{
    private String description;
    private String reference;

    public Item(String description, String reference, int Depth, int Height, int Lenght, int Volume) {
        super(Depth, Height, Lenght, Volume);
        this.description = description;
        this.reference = reference;
    }

    @Override
    public String getReference() {
        return this.reference;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String string) {
        this.description=string;
    }
    
    @Override
     public String toString(){
        return "Dimensions: " + super.toString() + 
               "\nDescription: " + this.description + 
               "\nReference: " + this.reference;
    }
}
