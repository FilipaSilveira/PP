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
    private static final int MAX_ITEMS = 10;
    private String reference = "container";
    private containerStatus status = containerStatus.OPEN;
    private int occupiedVolume = 0;

    public Container(int Depth, int Height, int Lenght, int Volume) {
        super(Depth, Height, Lenght, Volume);
        this.item = new ItemPacked[MAX_ITEMS];
        this.reference += this.id;
        this.id++;
    }

    /**
     * Adiciona um novo ItemPacked ao contentor
     * @param iitem item a ser adicionado
     * @param ip posição em que o item será colocado
     * @param color cor usada para o item
     * @return false caso o item já exista no contentor e true - quando o item é inserido
     * @throws containerException caso algum parametro estiver a nulo ou o contentor estiver fechado
     */
    @Override
    public boolean addItem(IItem iitem, IPosition ip, Color color) throws containerException {
        if( (iitem != null && ip != null && color != null) || this.isClosed() ){
    
            for(int i=0; i<this.NumItems; i++){
                if(this.item[i].getItem().getReference().equals(iitem.getReference())){
                    return false; // este item já existe dentro do container
                }
            }
            
            if(this.NumItems < MAX_ITEMS){
                this.item[this.NumItems] = new ItemPacked(ip,color,iitem);
                this.NumItems ++;
                this.updateOccupiedVolume(); //atualiza o volume ocupado no contentor
                return true;
            }
            
            return false;
            // what is a collection?
            
        }else{
            throw new containerException();
        }
    }

    /**
     * Remove items que foram adicionados ao contentor
     * @param iitem que irá ser removido
     * @return false se o item não existir no contentor e true - se for removido
     * @throws ContainerException se o parametro for nulo ou o contentor estiver fechado
     */
    @Override
    public boolean removeItem(IItem iitem) throws ContainerException {
        if( (iitem != null) || this.isClosed() ){ //se for nulo ou se o contentor estiver fechado 
            for (int i = 0; i < this.NumItems; i++) {
                if (this.item[i] == iitem) {
                    for (int j = i; j < this.NumItems - 1; j++) {
                        this.item[i] = this.item[i + 1];
                    }
                    this.NumItems--;
                    this.updateOccupiedVolume(); //atualiza o volume caso seja removido o item
                    return true;
                }
            }
            return false;
        }else{
            throw new containerException();
        }
    }
    
    /**
     * @return true se estiver fora do contentor false - se não estiver
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
     * comparação entre caixas para overlaping
     * @return True - interceta ou False - não interceta
     */
    public boolean isOverlaping(){
        for(int i=0; i<this.NumItems - 1; i++){
            for (int j = i + 1; j < this.NumItems; j++) {
                if(i != j){ //se forem caixas diferentes                  
                    if( (this.item[i].getPosition().getX() <= this.item[j].getPosition().getX() + this.item[j].getItem().getLenght() && this.item[i].getPosition().getX() + this.item[i].getItem().getLenght() >= this.item[j].getPosition().getX()) &&
                        (this.item[i].getPosition().getY() <= this.item[j].getPosition().getY() + this.item[j].getItem().getDepth()&& this.item[i].getPosition().getY() + this.item[i].getItem().getDepth() >= this.item[j].getPosition().getY()) &&
                        (this.item[i].getPosition().getZ() <= this.item[j].getPosition().getZ() + this.item[j].getItem().getHeight()&& this.item[i].getPosition().getZ() + this.item[i].getItem().getHeight() >= this.item[j].getPosition().getZ())
                      ){
                        return true; //intercepta
                    }
                }
            }
        }
        return false; //não intercepta
    }
    
    /**
     * 
     * @throws containerException 
     * @throws positionException se algum item estiver fora do contentor ou subreposto noutro item
     */
    @Override
    public void validate() throws containerException, positionException{
        if(getRemainingVolume() >= 0){
            //se os itens não estiverem todos dentro do contentor
            if (isOutsideContainer()){
                throw new positionException();
            }
            //se houver itens sobrepostos no contentor
            if(isOverlaping()){
                throw new positionException();
            }
        }else{
            //se o volume não for menor ou igual ao volume atual
            throw new containerException();
        }
    }

    /**
     * Fechar o contentor e verificar se este pode ser fechado
     * @throws containerException
     * @throws positionException se algum item estiver fora do contentor ou subreposto noutro item
     */
    @Override
    public void close() throws containerException, positionException {
        if ( this.getOccupiedVolume() > this.getVolume() ){ //se o volume for maior que o volume atual
            throw new containerException();
        }
        if(isOutsideContainer()){
            throw new positionException();
        }
        if(isOverlaping()){
            throw new positionException();
        }
        this.status = containerStatus.CLOSED; //atualiza o status para CLOSED 
    }

    /**
     * Retorna o item com uma determinada referência
     * @param string referência - (identificador exclusivo) do item
     * @return o item caso este exista ou null se não existir
     */
    @Override
    public IItem getItem(String string) {
        for(int i=0; i<this.NumItems; i++){
            if(this.item[i].getItem().getReference().equals(string)){ //compara as referencias (identificador exclusivo) do item
                return this.item[i].getItem();
            }
        }
        return null;
    }

    /**
     * Atualiza o volume ocupado pelos items (ao adicionar ou remover items)
     */
    public void updateOccupiedVolume() {
        int occupied_Volume = 0;
        for(int i=0; i<this.NumItems; ++i){
            occupied_Volume += this.item[i].getItem().getVolume();
        }
        this.occupiedVolume = occupied_Volume;
    }
    
    /**
     * 
     * @return o volume ocupado no contentor
     */
    @Override
    public int getOccupiedVolume() {
        return this.occupiedVolume;
    }

    /**
     * Dá um array (sem posições nulas) para os itens embalados no contentor
     * @return os itens embalados no contentor
     */
    @Override
    public IItemPacked[] getPackedItems() {
        return this.item;
    }

    /**
     * 
     * @return referência do contentor
     */
    @Override
    public String getReference() {
        return this.reference;
    }

    /**
     * 
     * @return número de itens no contentor
     */
    @Override
    public int getNumberOfItems() {
        return this.NumItems;
    }

    /**
     * 
     * @return volume restante no contentor
     */
    @Override
    public int getRemainingVolume() {
        return (this.getVolume())-(this.getOccupiedVolume());
    }

    /**
     * Retornar se o contentor estiver fechado
     * @return true se o contentor estiver fechado false - caso não esteja
     */
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
        for(int i = 0; i < this.NumItems; i++){
            string += this.item[i].getItem().getDescription();
        }
        return "Dimensions(L,D,H,V): " + this.getLenght() +", "+  this.getDepth() +", "+ this.getHeight() +", "+ this.getVolume() +", "+ 
               "\nReference: " + this.reference + 
               "\nEstado: " + this.status + 
               "\nItems:" + string;
    }
}
