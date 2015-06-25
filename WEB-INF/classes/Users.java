import java.util.HashMap;
import java.util.Map.Entry;
import java.util.ArrayList;

public class Users {
	private static HashMap<String,HashMap<String,String>>  users =
		new HashMap<String,HashMap<String,String>>();
	private static boolean initialized = false;
	
	public static HashMap<String,HashMap<String,String>> getUsers() {
		if (!initialized) {
			init();
		}
		return users;
	}
	
	public static HashMap<String,String> getUser(String username) {
		if (!initialized) {
			init();
		}
		return users.get(username);
	}
	
	public static ArrayList<HashMap<String,String>> getUsersWithRole(String role) {
		ArrayList<HashMap<String,String>> result = new ArrayList<HashMap<String,String>>();
		for (Entry<String,HashMap<String,String>> entry: getUsers().entrySet()) {
			if (entry.getValue().get("role").equals(role)) {
				result.add(entry.getValue());
			}
		}
		return result;
	}
	
	private static void init() {
		initialized = true;
		String key;
		HashMap<String,String> data;
		
		//USERS
		data = new HashMap<String,String>();
		key = "manager";
		data.put("username", key);
		data.put("password", "manager");
		data.put("name", "Humphrey Dunn");
		data.put("role", "manager");
		data.put("email", "manager@widecast.com");
		users.put(key, data);
		
		data = new HashMap<String,String>();
		key = "customer";
		data.put("username", key);
		data.put("password", "customer");
		data.put("name", "John Doe");
		data.put("role", "customer");
		data.put("email", "customer@email.com");
		data.put("card", "0000111122223333");
		data.put("expiration", "01/18");
		data.put("addr_1", "3950 N Lake Shore Dr");
		data.put("addr_2", "");
		data.put("city", "Chicago");
		data.put("services", "1,5");
		data.put("events", "22,23");
		data.put("bills", "1,5");
		users.put(key, data);
		
		data = new HashMap<String,String>();
		key = "technician";
		data.put("username", key);
		data.put("password", "technician");
		data.put("role", "technician");
		data.put("name", "Mortimer Sullivan");
		data.put("email", "technician@widecast.com");
		data.put("tickets", "1,2");
		users.put(key, data);
		
		data = new HashMap<String,String>();
		key = "account_specialist";
		data.put("username", key);
		data.put("password", "account_specialist");
		data.put("name", "Kirk Summers");
		data.put("role", "account_specialist");
		data.put("email", "account_specialist@widecast.com");
		users.put(key, data);
	}
	
	public static void main(String args[]) {
		System.out.println(Users.getUsersWithRole("technician"));
	}
}