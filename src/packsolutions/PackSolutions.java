package packsolutions;

import Person.*;
import ShippingOrder.ShippingOrder;
import base.Address;
import order.exceptions.ContainerException;
import order.exceptions.OrderException;
import order.exceptions.PositionException;
import order.packing.Color;
import orderPacking.*;

/**
 *
 * @author Filipa
 */
public class PackSolutions {

    public static void main(String[] args) {
        Address address1 = new Address("Porto", "Portugal", 70, "Amarante", "Rua das Searas");
        Address address2 = new Address("Lisboa", "Espanha", 69, "Felgueiras", "Rua das coives"); 
    
        address1.setCity("novaCidade");
        address1.setCountry("novoPais");
        address1.setState("novoEstado");
        address1.setNumber(5);
        address1.setStreet("novaRua");
        System.out.println(address1.toString());
    
    
    }
}
