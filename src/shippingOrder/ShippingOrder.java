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
    private static int MAX_CONTAINERS = 5;
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
            // this.Containers = new Container[MAX_CONTAINERS];
            Container newContainers[] =  new Container[this.numContainers];
            newContainers = this.Containers;
            this.MAX_CONTAINERS += 15;
            this.Containers = new Container[MAX_CONTAINERS];
            this.Containers = newContainers;
            
            this.Containers[this.numContainers]=(Container)ic;
            this.numContainers++;
            return true; 
        }
    }

    @Override
    public boolean removeContainer(IContainer ic) throws orderException, containerException {
        int flag = 0;
        
        if(this.status != OrderStatus.IN_TREATMENT){
            throw new orderException();
        }
        if(ic == null){
            throw new containerException();
        }    
        for (int i = 0; i < this.numContainers; i++) {
            if (this.Containers[i] == ic) {
                for (int j = i; j < this.numContainers - 1; j++) {
                    this.Containers[i] = this.Containers[i + 1];
                    this.numContainers--;
                }
                flag = 1;
            }
        }
        if(flag == 0){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public boolean existsContainer(IContainer ic) {
        for(int i=0;i<this.numContainers;i++){
            if(this.Containers[i]==ic){
                return true;
            }
        }
        return false;
    }

    @Override
    public int findContainer(String string) {
        int pos;
        for(int i=0; i<this.numContainers; i++){
            if(this.Containers[i].getReference() == string){
                pos = i;
                return pos; //testar return i;
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

    @Override
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
        Container allContainers[] = new Container[this.numContainers];
        allContainers = this.Containers;
        
        return (IContainer[])allContainers;
    }

    @Override
    public void validate() throws containerException, positionException {
       for(int i=0; i<this.numContainers; ++i){
            if(this.Containers[i].isClosed() == false){
                System.out.println("Os contentores nao estao todos fechados!");
            }
        } 
    }

    @Override
    public String summary() {
        String string = "";
        
        for(int i = 0; i < this.numContainers; i++){
            string += this.Containers[i].toString();
        }

        return string;
    }
}

