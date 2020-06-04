package base;
import order.base.IPerson;
import order.base.IAddress;

/**
 *
 * @author Filipa
 */
public class Person implements IPerson{

    private IAddress address;
    private String name;
    
    public Person(IAddress address, String name) {
        this.address = address;
        this.name = name;
    }
    
    @Override
    public IAddress getAddress() {
        return this.address;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setAddress(IAddress ia) {
        this.address = ia;
    }

    @Override
    public void setName(String string) {
        this.name = string;
    }
    
    @Override
    public String toString(){
        return "Name: " + this.name
             + "\n Address: " + this.address.toString();
    }
    
}
