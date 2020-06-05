package shippingOrder;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import shippingorder.IExporter;
import shippingorder.IShippingOrder;

/**
 *
 * @author Filipa
 */
public class Exporter implements IExporter{

    @Override
    public void export(IShippingOrder iso) throws IOException {
        
        FileWriter file;
        
        JSONObject obj = new JSONObject();
        
        obj.put("orderId",iso.getId());
        JSONObject destinationVar = new JSONObject();
        
        iso.getDestination();        
        
        
        obj.put("destination", destinationVar);

        
        iso.getContainers();


        obj.put("Name", "Crunchify.com");
        obj.put("Author", "App Shah");
 
        JSONArray company = new JSONArray();
        company.add("Company: Facebook");
        company.add("Company: PayPal");
        company.add("Company: Google");
        obj.put("Company List", company);
        
 
        // Constructs a FileWriter given a file name, using the platform's default charset
        file = new FileWriter("shippingOrder.json");
        file.write(obj.toJSONString());
        
    }
    
}
