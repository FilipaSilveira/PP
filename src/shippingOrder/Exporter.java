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

import java.io.FileWriter;
import java.io.IOException;
import order.packing.Color;
import order.packing.IContainer;
import order.packing.IItemPacked;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import packing.Container;
import packing.ItemPacked;
import shippingorder.IExporter;
import shippingorder.IShippingOrder;

/**
 *
 * @author Filipa
 */
public class Exporter implements IExporter{

    @Override
    public void export(IShippingOrder iso) throws IOException {
        //iso = (ShippingOrder) iso;
        FileWriter file;
        
        JSONObject obj = new JSONObject();
        obj.put("orderId",iso.getId());
        
        JSONObject destinationVar = new JSONObject();
        JSONObject addressVar = new JSONObject();
        
        addressVar.put("country",iso.getDestination().getAddress().getCountry());
        addressVar.put("number",iso.getDestination().getAddress().getNumber());
        addressVar.put("stress",iso.getDestination().getAddress().getStreet());
        addressVar.put("city",iso.getDestination().getAddress().getCity());
        addressVar.put("state",iso.getDestination().getAddress().getState());
        
        destinationVar.put("address",addressVar);
        destinationVar.put("name",iso.getDestination().getName());
        
        obj.put("destination", destinationVar);
        
        JSONArray containersVar = new JSONArray();
       
        //não está a ecrever para o ficheiro
        /*
        // for beggin
        for (IContainer cont: iso.getContainers()) {
            JSONObject containerVar = new JSONObject();
            
            containerVar.put("volume",cont.getVolume());
            containerVar.put("reference",cont.getReference());
            containerVar.put("depth",cont.getDepth());
            containerVar.put("color","white"); // fixed values
            containerVar.put("length",cont.getLenght());
            containerVar.put("colorEdge","black"); // fixed values
            
            JSONArray itemsVar = new JSONArray();
            
            for (IItemPacked item: cont.getPackedItems()) {
                
                JSONObject itemVar = new JSONObject();
                
                itemVar.put("reference", item.getItem().getReference());
                itemVar.put("depth", item.getItem().getDepth());
                itemVar.put("color", item.getColor());
                itemVar.put("x", item.getPosition().getX());
                itemVar.put("length", item.getItem().getLenght());
                itemVar.put("y", item.getPosition().getY());
                itemVar.put("description", item.getItem().getDescription());
                itemVar.put("z", item.getPosition().getZ());
                itemVar.put("colorEdge", item.getColor());
                itemVar.put("height", item.getItem().getHeight());
                
                itemsVar.add(itemVar);
            }
            
            containerVar.put("items",itemsVar);
            
            containerVar.put("height",cont.getHeight());
            containerVar.put("occupiedVolume",cont.getOccupiedVolume());

            containersVar.add(containerVar);
        }
        // for end
        */
        obj.put("containers",containersVar);
        
        obj.put("status",iso.getStatus());
        
        JSONObject customerVar = new JSONObject();
        
        JSONObject addressVarCustomer = new JSONObject();
        
        addressVarCustomer.put("country", iso.getCustomer().getAddress().getCountry());
        addressVarCustomer.put("number", iso.getCustomer().getAddress().getNumber());
        addressVarCustomer.put("stress", iso.getCustomer().getAddress().getStreet());
        addressVarCustomer.put("city", iso.getCustomer().getAddress().getCity());
        addressVarCustomer.put("state", iso.getCustomer().getAddress().getState());
        
        customerVar.put("address",addressVarCustomer);
        
        customerVar.put("name",iso.getCustomer().getName());
        customerVar.put("id",iso.getCustomer().getCustomerId());
        
        JSONObject billingAddressVar = new JSONObject();
        
        billingAddressVar.put("country", iso.getCustomer().getBillingAddress().getCountry());
        billingAddressVar.put("number", iso.getCustomer().getBillingAddress().getNumber());
        billingAddressVar.put("stress", iso.getCustomer().getBillingAddress().getStreet());
        billingAddressVar.put("city", iso.getCustomer().getBillingAddress().getCity());
        billingAddressVar.put("state", iso.getCustomer().getBillingAddress().getState());
        
        customerVar.put("billingAddress", billingAddressVar);
        
        obj.put("customer",customerVar);
 
        // Constructs a FileWriter given a file name, using the platform's default charset
        file = new FileWriter("shippingOrder.json");
        file.write(obj.toJSONString());
        file.close();
        
    }
    
}
