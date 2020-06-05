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
            
        }else{
            Container[] newContainer = new Container[MAX_CONTAINERS + 5]; // novo contentor com mais 5 espaços
            System.arraycopy(this.Containers, 0, newContainer, 0, this.Containers.length);
            this.Containers = newContainer;
            this.Containers[this.numContainers] = (Container)ic;
            this.numContainers++;
            this.MAX_CONTAINERS = MAX_CONTAINERS + 5;
            return true;
        }
    }

    @Override
    public boolean removeContainer(IContainer ic) throws orderException, containerException {
        
        if(this.status != OrderStatus.IN_TREATMENT){
            throw new orderException();
        }
        if(ic == null){
            throw new containerException();
        }    
        for (int i = 0; i < this.numContainers; i++) {
            if (this.Containers[i].getReference().equals(ic.getReference())) { // encontra contentor
                for (int j = i; j < this.numContainers - 1; j++) {
                    this.Containers[i] = this.Containers[i + 1];
                }
                this.numContainers--;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean existsContainer(IContainer ic) {
        for(int i=0; i<this.numContainers; i++){
            if(this.Containers[i].getReference().equals(ic.getReference())){
                return true;
            }
        }
        return false;
    }

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

    @Override // TODO: check if it's correct in the end
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

    @Override
    public IContainer[] getContainers() {
        /*Container allContainers[] = new Container[this.numContainers];
        allContainers = this.Containers;
        
        return (IContainer[])allContainers;*/
        return this.Containers.clone();
    }

    @Override
    public void validate() throws containerException, positionException {
       for(int i=0; i<this.numContainers; ++i){
            /*if(this.Containers[i].isClosed() == false){
                System.out.println("Os contentores nao estao todos fechados!");
            }*/
            this.Containers[i].validate();
        } 
    }

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

