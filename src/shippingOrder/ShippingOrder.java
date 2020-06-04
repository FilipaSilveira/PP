package shippingOrder;

import order.base.ICustomer;
import order.base.IPerson;
import order.base.OrderStatus;
import order.exceptions.ContainerException;
import order.exceptions.OrderException;
import order.exceptions.PositionException;
import order.packing.IContainer;
import shippingorder.IShippingOrder;

/**
 *
 * @author Filipa
 */
public class ShippingOrder implements IShippingOrder{

    @Override
    public boolean addContainer(IContainer ic) throws OrderException, ContainerException {

    }

    @Override
    public boolean removeContainer(IContainer ic) throws OrderException, ContainerException {

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
    public void setStatus(OrderStatus os) throws OrderException, ContainerException, PositionException {

    }

    @Override
    public int getId() {

    }

    @Override
    public IContainer[] getContainers() {

    }

    @Override
    public void validate() throws ContainerException, PositionException {

    }

    @Override
    public String summary() {

    }
    
}
