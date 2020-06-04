package shippingOrder;

import order.base.ICustomer;
import order.base.IPerson;
import order.base.OrderStatus;
import exceptions.*;
import order.packing.IContainer;
import shippingorder.IShippingOrder;

/**
 *
 * @author Filipa
 */
public class ShippingOrder implements IShippingOrder{

    @Override
    public boolean addContainer(IContainer ic) throws orderException, containerException {

    }

    @Override
    public boolean removeContainer(IContainer ic) throws orderException, containerException {

    }

    @Override
    public boolean existsContainer(IContainer ic) {

    }

    @Override
    public int findContainer(String string) {

    }

    @Override
    public IPerson getDestination() {

    }

    @Override
    public void setDestination(IPerson ip) {

    }

    @Override
    public ICustomer getCustomer() {

    }

    @Override
    public OrderStatus getStatus() {

    }

    @Override
    public void setStatus(OrderStatus os) throws orderException, containerException, positionException {

    }

    @Override
    public int getId() {

    }

    @Override
    public IContainer[] getContainers() {

    }

    @Override
    public void validate() throws containerException, positionException {

    }

    @Override
    public String summary() {

    }
    
}
