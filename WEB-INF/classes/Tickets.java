import java.util.HashMap;
import java.util.Map.Entry;

public class Tickets {
	private static HashMap<String,HashMap<String,String>>  tickets = new HashMap<String,HashMap<String,String>>();
	private static boolean initialized = false;
	private static int ticket_num = 0;
	
	public static HashMap<String,String> getTicket(String id) {
		if (!initialized) {
			init();
		}
		return tickets.get(id);
	}
	
	public static String addTicket(String assignee, String creator, String info) {
		if (!initialized) {
			init();
		}
		HashMap<String,String> ticketData = new HashMap<String,String>();
		ticketData.put("assignee", assignee);
		ticketData.put("creator", creator);
		ticketData.put("info", info);
		ticket_num++;
		tickets.put(String.valueOf(ticket_num), ticketData);
		return (String.valueOf(ticket_num));
	}
	
	private static void init() {
		initialized = true;
		ticket_num = 2;
		String key;
		HashMap<String,String> data;
		
		//TICKETS
		data = new HashMap<String,String>();
		key = "1";
		data.put("creator", "account_specialist");
		data.put("assignee", "technician");
		data.put("info", "John Doe (username:customer) not able to access account");
		tickets.put(key, data);
		
		data = new HashMap<String,String>();
		key = "2";
		data.put("creator", "manager");
		data.put("assignee", "technician");
		data.put("info", "Call Customer Support Desk to ask about new routers.");
		tickets.put(key, data);
	}
}