package base;

import order.base.IAddress;
import order.base.ICustomer;

/**
 *
 * @author Filipa
 */
public class Customer extends Person implements ICustomer{

    private IAddress BillingAddress;
    private static int ID = 0;
    private final int IDCustomer;
    
    public Customer(IAddress address, String name) {
        super(address, name);
        this.BillingAddress = address;
        this.IDCustomer = this.ID;
        this.ID++;
    }

    @Override
    public IAddress getBillingAddress() {
        return this.BillingAddress;
    }

    @Override
    public int getCustomerId() {
        return this.IDCustomer;
    }

    @Override
    public void setBillingAddress(IAddress ia) {
        this.BillingAddress = (Address)ia;
    }

   @Override
    public String toString(){
        return super.toString() + 
                "\n Billing Address: " + this.BillingAddress.toString()
              + "\n Customer ID: " + this.IDCustomer;
    }
    
}
