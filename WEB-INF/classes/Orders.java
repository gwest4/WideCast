import java.util.HashMap;
import java.util.Map.Entry;

public class Orders {
	private static HashMap<String,HashMap<String,String>>  orders = new HashMap<String,HashMap<String,String>>();
	private static boolean initialized = false;
	
	public static HashMap<String,String> getOrder(String id) {
		if (!initialized) {
			init();
		}
		return orders.get(id);
	}
	
	public static void addOrder(String id, HashMap<String,String> data) {
		if (!initialized) {
			init();
		}
		orders.put(id, data);
	}
	
	private static void init() {
		initialized = true;
		String key;
		HashMap<String,String> data;
		
		//ORDERS
		data = new HashMap<String,String>();
		key = "847230";
		data.put("name", "John Doe");
		data.put("email", "customer@email.com");
		data.put("card", "0000111122223333");
		data.put("expiration", "01/18");
		data.put("addr_1", "3950 N Lake Shore Dr");
		data.put("addr_2", "");
		data.put("city", "Chicago");
		data.put("total", "8.00");
		orders.put(key, data);
		
		data = new HashMap<String,String>();
		key = "847231";
		data.put("name", "John Doe");
		data.put("email", "customer@email.com");
		data.put("card", "0000111122223333");
		data.put("expiration", "01/18");
		data.put("addr_1", "3950 N Lake Shore Dr");
		data.put("addr_2", "");
		data.put("city", "Chicago");
		data.put("total", "20.00");
		orders.put(key, data);
	}
	
	public static void main(String args[]) {
		System.out.println(Catalog.getTvPlans());
	}
	
}