/*
* Nome: Ana Filipa Sousa Silveira
* Número: 8160040
* Turma: LSIRC
*
* Nome: Rafael António Alves Maia
* Número: 8160489
* Turma: LSIRC
*/

package shippingOrder;

import base.Customer;
import order.base.ICustomer;
import order.base.IPerson;
import order.base.OrderStatus;
import exceptions.*;
import order.packing.IContainer;
import packing.Container;
import shippingorder.IShippingOrder;

/**
 *
 * @author Filipa
 */

public class ShippingOrder implements IShippingOrder{
    Container Containers[];
    OrderStatus status;
    private static int id = 0;
    private final int idContainer;
    Customer customer, destination;
    private static int MAX_CONTAINERS = 10;
    private int numContainers = 0;

    public ShippingOrder(Customer customer, Customer destination) {
        this.Containers = new Container[MAX_CONTAINERS];
        this.status = OrderStatus.IN_TREATMENT;
        this.customer = customer;
        this.destination = destination;
        this.idContainer = id;
        this.id++;
        
    }
    
    /**
     * Adiciona um novo contentor a ShippingOrder
     * @param ic contentor a ser adicionado
     * @return false se o contentor já existir na ShippingOrder ou true - so o contentor for inserido na ShippingOrder
     * @throws orderException se o status do pedido não for igual a IN_TREATMENT
     * @throws containerException se algum parametro for nulo ou o contentor não estiver fechado
     */
    @Override
    public boolean addContainer(IContainer ic) throws orderException, containerException {
        if(this.status != OrderStatus.IN_TREATMENT){
            throw new orderException();
        }
        
        if(ic.isClosed() == false){
            throw new containerException();
        }
        
        if(ic == null){
            throw new containerException();
        }
        
        // vê se contentor já existe no array Container
        for(int i=0; i < this.numContainers; i++){ 
            if( this.Containers[i].getReference().equals(ic.getReference()) ){
                return false;
            }
        }
        // se houver espaço no arry insere
        if(this.numContainers < ShippingOrder.MAX_CONTAINERS){
            this.Containers[this.numContainers] = (Container)ic;
            this.numContainers++;
            return true;
            
        }else{ //aumenta o contentor com mais 5 espaços
            Container[] newContainer = new Container[MAX_CONTAINERS + 5];
            //copia o arraey Containers (posição 0) para o newContainer (posição 0) com o tamanho de Containers 
            System.arraycopy(this.Containers, 0, newContainer, 0, this.Containers.length);
            this.Containers = newContainer;
            this.Containers[this.numContainers] = (Container)ic;
            this.numContainers++;
            this.MAX_CONTAINERS = MAX_CONTAINERS + 5;
            return true;
        }
    }

    /**
     * Remove um contentor da shipping order
     * @param ic contentor a ser removido
     * @return true - se o contentor for removido ou false - se não for
     * @throws orderException se o status não for IN_TREATMENT
     * @throws containerException se o parâmetro for nulo
     */
    @Override
    public boolean removeContainer(IContainer ic) throws orderException, containerException {
        if(this.status != OrderStatus.IN_TREATMENT){
            throw new orderException();
        }
        if(ic == null){
            throw new containerException();
        }    
        for (int i = 0; i < this.numContainers; i++) {
            //encontra contentor comparando as referencias
            if (this.Containers[i].getReference().equals(ic.getReference())) { 
                for (int j = i; j < this.numContainers - 1; j++) {
                    this.Containers[i] = this.Containers[i + 1];
                }
                this.numContainers--;
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se o contentor existe na shipping order
     * @param ic contentor a verificar se existe
     * @return true - se o contentor existir ou false - se não existir
     */
    @Override
    public boolean existsContainer(IContainer ic) {
        for(int i=0; i<this.numContainers; i++){
            if(this.Containers[i].getReference().equals(ic.getReference())){
                return true;
            }
        }
        return false;
    }

    /**
     * Procura um determinado contentor com base na sua referência
     * @param string referencia do contentor a encontrar
     * @return - o número do índice que se refere à posição ocupada pelo contentor na lista de shipping order
     */
    @Override
    public int findContainer(String string) {
        for(int i=0; i<this.numContainers; i++){
            if(this.Containers[i].getReference().equals(string)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public IPerson getDestination() {
        return this.destination;
    }

    @Override
    public void setDestination(IPerson ip) {
        this.destination = (Customer)ip;
    }

    @Override
    public ICustomer getCustomer() {
        return (ICustomer)this.customer;
    }

    @Override
    public OrderStatus getStatus() {
        return this.status;
    }

    /**
     * Muda o estado
     * @param os order status
     * @throws orderException
     * @throws containerException
     * @throws positionException
     */
    @Override //TODO: check if it's correct in the end
    public void setStatus(OrderStatus os) throws orderException, containerException, positionException {
         if( os == OrderStatus.IN_TREATMENT ){
            
            if( this.status == OrderStatus.IN_TREATMENT || this.status == OrderStatus.CLOSED ){
                throw new orderException();
            }
            
            this.status=OrderStatus.AWAITS_TREATMENT;
           
        }else{
            if( os == OrderStatus.CLOSED ){
                
                if( this.status == OrderStatus.CLOSED ){
                    throw new orderException();
                }
                this.status=OrderStatus.IN_TREATMENT;
                this.validate(); //ContainerException && PositionException
            }else{
                if( os == OrderStatus.SHIPPED ){
                    
                    this.status=OrderStatus.CLOSED;
                }
            }
        }
    }

    @Override
    public int getId() {
        return this.idContainer;
    }

    /**
     * 
     * @return um array (sem posições nulas) para os contentores na shipping order
     */
    @Override
    public IContainer[] getContainers() {
        return this.Containers.clone();
    }

    /**
     * Verifica se algum contentor é inválido
     * @throws containerException
     * @throws positionException 
     */
    @Override
    public void validate() throws containerException, positionException {
       for(int i=0; i<this.numContainers; ++i){
            this.Containers[i].validate();
        } 
    }

    /**
     * 
     * @return uma string com um resumo dos contentores existentes e os seus itens
     */
    @Override
    public String summary() {
        String string = "";
        for(int i = 0; i < this.numContainers; i++){
            string += this.Containers[i].toString();
            string += "\n\n";
        }
        return string;
    }
}