/*
* Nome: Ana Filipa Sousa Silveira
* Número: 8160040
* Turma: LSIRC
*
* Nome: Rafael António Alves Maia
* Número: 8160489
* Turma: LSIRC
*/

package packsolutions;

import base.*;
import exceptions.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import order.base.OrderStatus;
import packing.*;
import shippingOrder.*;
import order.packing.*;
import org.json.simple.parser.ParseException;
import packing_gui.*;

/**
 *
 * @author Filipa
 */
public class PackSolutions {

    public static void main(String[] args) {
        
        // Address
        Address address_1 = new Address("Porto", "PT", 1, "a1", "a1");
        Address address_2 = new Address("Lisboa", "PT", 2, "b2", "b2");
        Address address_3 = new Address("Alentejo", "PT", 3, "c3", "c3");
        Address address_4 = new Address("Algarve", "PT", 4, "d4", "d4");
        
        System.out.println(address_1.toString());
        address_1.setCity("Coimbra");
        System.out.println(address_1.toString());
        
        // Customer
        Customer cliente_1 = new Customer(address_1, "cli_1");
        Customer cliente_2 = new Customer(address_2, "cli_2");
        
        System.out.println(cliente_1.toString());
        System.out.println(cliente_2.toString());
        
        // Item
        Item item_1 = new Item("item_1","1",1,1,1,1);
        Item item_2 = new Item("item_2","2",2,2,2,2);
        
        // Container
        Container con_1 = new Container(8, 8, 8, 2);
        Container con_2 = new Container(2, 2, 2, 2); // volume tem que ser 8
        Container con_3 = new Container(2, 2, 2, 2); // volume tem que ser 8
        Container con_4 = new Container(2, 2, 2, 2); // volume tem que ser 8
        
        System.out.println(con_1.getVolume()); // retorna volume certo 
        
        // ShippingOrder
        ShippingOrder so_1 = new ShippingOrder(cliente_1, cliente_2);
        
        // Position
        Position pos_1 = new Position(0, 0, 0);
        Position pos_2 = new Position(0, 3, 0);
        Position pos_3 = new Position(0, 4, 0);
        Position pos_4 = new Position(0, 6, 0);
        
        // add items to container
        try { 
            
            System.out.println(con_1.addItem(item_2, pos_1, Color.silver));
            System.out.println(con_1.getItem("2")); // retorna referência de objeto
            System.out.println(con_1.getItem("1")); // não encontra retorna null
            
            System.out.println(con_1.addItem(item_1, pos_2, Color.green));
            
        } catch (containerException ex) {
            Logger.getLogger(PackSolutions.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // closing Container
        try {
            con_1.close();
            con_2.close();
            con_3.close();
            con_4.close();
        } catch (containerException ex) {
            Logger.getLogger(PackSolutions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (positionException ex) {
            Logger.getLogger(PackSolutions.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // add Container to ShipingOrder
        try {
            System.out.println(so_1.summary());
            so_1.addContainer(con_1);
            System.out.println(so_1.summary());
            
            // não posso adicionar o mesmo contentor duas vezes
            System.out.println(so_1.addContainer(con_1)); 
            
            so_1.addContainer(con_1);
            so_1.addContainer(con_2);
            so_1.addContainer(con_3);
            so_1.addContainer(con_4);
            
          
            System.out.println("----- Final -----");
            System.out.println(so_1.summary());
            
            //ficheiro
            Exporter exportJson = new Exporter();
            
            try { //tries to save to a file
                
                exportJson.export(so_1);
                System.out.println("Exported!");
                
            } catch (IOException ex) {
                Logger.getLogger(PackSolutions.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            PackingGUI gui = new PackingGUI();
            String filepath = "shippingOrder.json";
            
            
            if(gui.validate(filepath)){ // mostra a minha INFO!
                try {
                
                gui.render(filepath);
                

                } catch (IOException ex) {
                    Logger.getLogger(PackSolutions.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(PackSolutions.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } catch (orderException ex) {
            Logger.getLogger(PackSolutions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (containerException ex) {
            Logger.getLogger(PackSolutions.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
