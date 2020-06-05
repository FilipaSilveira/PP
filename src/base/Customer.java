/*
* Nome: Ana Filipa Sousa Silveira
* Número: 8160040
* Turma: LSIRC
*
* Nome: Rafael António Alves Maia
* Número: 8160489
* Turma: LSIRC
*/

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
                "\nBilling Address: " + this.BillingAddress.toString()
              + "\nCustomer ID: " + this.IDCustomer;
    }
    
}
