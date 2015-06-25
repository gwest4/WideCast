import java.util.HashMap;
import java.util.Map.Entry;

public class Catalog {
	private static HashMap<String,HashMap<String,String>>  products = new HashMap<String,HashMap<String,String>>();
	private static boolean initialized = false;
	
	public static HashMap<String,HashMap<String,String>> getProducts(String department) {
		if (department==null) {
			if (!initialized) {
				init();
			}
			return products;
		}
		if (department.equals("tv_plans")) {
			return getTvPlans();
		} else if (department.equals("internet_plans")) {
			return getInternetPlans();
		} else if (department.equals("games")) {
			return getGames();
		} else if (department.equals("sport_events")) {
			return getSportsEvents();
		} else if (department.equals("movies")) {
			return getMovies();
		} else {
			if (!initialized) {
				init();
			}
			return products;
		}
	}
	
	public static HashMap<String,String> getItem(String id) {
		if (!initialized) {
			init();
		}
		return products.get(id);
	}
	
	public static HashMap<String,HashMap<String,String>> getTvPlans() {
		if (!initialized) {
			init();
		}
		HashMap<String,HashMap<String,String>>  tv_plans = new HashMap<String,HashMap<String,String>>();
		for (Entry<String,HashMap<String,String>> entry: products.entrySet()) {
			if (entry.getValue().get("type").equals("TV Plan"))
				tv_plans.put(entry.getKey(),entry.getValue());
		}
		return tv_plans;
	}
	
	public static HashMap<String,HashMap<String,String>> getInternetPlans() {
		if (!initialized) {
			init();
		}
		HashMap<String,HashMap<String,String>>  internet_plans = new HashMap<String,HashMap<String,String>>();
		for (Entry<String,HashMap<String,String>> entry: products.entrySet()) {
			if (entry.getValue().get("type").equals("Internet Plan"))
				internet_plans.put(entry.getKey(),entry.getValue());
		}
		return internet_plans;
	}
	
	public static HashMap<String,HashMap<String,String>> getGames() {
		if (!initialized) {
			init();
		}
		HashMap<String,HashMap<String,String>>  games = new HashMap<String,HashMap<String,String>>();
		for (Entry<String,HashMap<String,String>> entry: products.entrySet()) {
			if (entry.getValue().get("type").equals("Game"))
				games.put(entry.getKey(),entry.getValue());
		}
		return games;
	}
	
	public static HashMap<String,HashMap<String,String>> getSportsEvents() {
		if (!initialized) {
			init();
		}
		HashMap<String,HashMap<String,String>>  sport_events = new HashMap<String,HashMap<String,String>>();
		for (Entry<String,HashMap<String,String>> entry: products.entrySet()) {
			if (entry.getValue().get("type").equals("PPV Sport Event"))
				sport_events.put(entry.getKey(),entry.getValue());
		}
		return sport_events;
	}
	
	public static HashMap<String,HashMap<String,String>> getMovies() {
		if (!initialized) {
			init();
		}
		HashMap<String,HashMap<String,String>>  movies = new HashMap<String,HashMap<String,String>>();
		for (Entry<String,HashMap<String,String>> entry: products.entrySet()) {
			if (entry.getValue().get("type").equals("PPV Movie"))
				movies.put(entry.getKey(),entry.getValue());
		}
		return movies;
	}
	
	private static void init() {
		initialized = true;
		String key;
		HashMap<String,String> data;
		
		//TV PLANS
		data = new HashMap<String,String>();
		key = "1";
		data.put("title", "Basic");
		data.put("price", "30.00");
		data.put("info", "50 channels");
		data.put("type", "TV Plan");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "2";
		data.put("title", "BasicPlus");
		data.put("price","50.00");
		data.put("info","100 channels");
		data.put("type", "TV Plan");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "3";
		data.put("title", "Ultimate");
		data.put("price","90.00");
		data.put("info","200 channels");
		data.put("type", "TV Plan");
		products.put(key, data);
		
		//INTERNET PLANS
		data = new HashMap<String,String>();
		key = "4";
		data.put("title", "SpeedLane");
		data.put("price", "20.00");
		data.put("info", "20/5 Mbps");
		data.put("type", "Internet Plan");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "5";
		data.put("title", "LightLane");
		data.put("price","40.00");
		data.put("info","50/10 Mbps");
		data.put("type", "Internet Plan");
		products.put(key, data);
		
		//ONLINE GAME RENTAL
		data = new HashMap<String,String>();
		key = "6";
		data.put("title", "Grand Theft Auto V");
		data.put("price","10.00");
		data.put("info","Rating: 96");
		data.put("type", "Game");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "7";
		data.put("title", "The Witcher 3: Wild Hunt");
		data.put("price","10.00");
		data.put("type", "Game");
		data.put("info","Rating: 93");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "8";
		data.put("title", "Pillars of Eternity");
		data.put("price","8.00");
		data.put("type", "Game");
		data.put("info","Rating: 90");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "9";
		data.put("title", "Kerbal Space Program");
		data.put("type", "Game");
		data.put("price","4.00");
		data.put("info","Rating: 89");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "10";
		data.put("title", "Heroes of the Storm");
		data.put("price","8.00");
		data.put("info","Rating: 88");
		data.put("type", "Game");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "11";
		data.put("title", "Cities: Skylines");
		data.put("price","4.00");
		data.put("info","Rating: 86");
		data.put("type", "Game");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "12";
		data.put("title", "Galactic Civilizations III");
		data.put("price","8.00");
		data.put("info","Rating: 85");
		data.put("type", "Game");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "13";
		data.put("title", "Grim Fandango Remastered");
		data.put("price","4.00");
		data.put("type", "Game");
		data.put("info","Rating: 84");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "14";
		data.put("title", "Project CARS");
		data.put("type", "Game");
		data.put("price","8.00");
		data.put("info","Rating: 83");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "15";
		data.put("title", "Hearthstone: Blackrock Mountain");
		data.put("price","8.00");
		data.put("info","Rating: 83");
		data.put("type", "Game");
		products.put(key, data);
		
		//PPV LIVE SPORTS
		data = new HashMap<String,String>();
		key = "16";
		data.put("title", "Cubs vs Diamondbacks");
		data.put("price","4.00");
		data.put("info","Date: 5/24/2015 4:00 PM");
		data.put("type", "PPV Sport Event");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "17";
		data.put("title", "Cubs vs Nationals 1");
		data.put("price","4.00");
		data.put("info","Date: 5/25/2015 6:00 PM");
		data.put("type", "PPV Sport Event");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "18";
		data.put("title", "Cubs vs Nationals 2");
		data.put("price","4.00");
		data.put("info","Date: 5/26/2015 4:00 PM");
		data.put("type", "PPV Sport Event");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "19";
		data.put("title", "Cubs vs Nationals 3");
		data.put("price","4.00");
		data.put("info","Date: 5/27/2015 3:00 PM");
		data.put("type", "PPV Sport Event");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "20";
		data.put("title", "Cubs vs Royals 1");
		data.put("price","4.00");
		data.put("info","Date: 5/29/2015 3:00 PM");
		data.put("type", "PPV Sport Event");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "21";
		data.put("title", "Cubs vs Royals 2");
		data.put("price","4.00");
		data.put("info","Date: 5/30/2015 12:00 PM");
		data.put("type", "PPV Sport Event");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "22";
		data.put("title", "Cubs vs Royals 3");
		data.put("price","4.00");
		data.put("info","Date: 5/31/2015 11:00 AM");
		data.put("type", "PPV Sport Event");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "23";
		data.put("title", "Cubs vs Marlins 1");
		data.put("price","4.00");
		data.put("info","Date: 6/18/2015 5:00 PM");
		data.put("type", "PPV Sport Event");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "24";
		data.put("title", "Cubs vs Marlins 2");
		data.put("price","4.00");
		data.put("info","Date: 6/19/2015 5:00 PM");
		data.put("type", "PPV Sport Event");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "25";
		data.put("title", "Cubs vs Marlins 3");
		data.put("price","4.00");
		data.put("info","Date: 6/20/2015 4:00 PM");
		data.put("type", "PPV Sport Event");
		products.put(key, data);

		//PPV Movies
		data = new HashMap<String,String>();
		key = "26";
		data.put("title", "Mad Max: Fury Road");
		data.put("price","3.00");
		data.put("info","Rating: 87");
		data.put("type", "PPV Movie");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "27";
		data.put("title", "Tomorrowland");
		data.put("price","3.00");
		data.put("info","Rating: 68");
		data.put("type", "PPV Movie");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "28";
		data.put("title", "San Andreas");
		data.put("price","3.00");
		data.put("info","Rating: 67");
		data.put("type", "PPV Movie");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "29";
		data.put("title", "Avengers: Age of Ultron");
		data.put("price","3.00");
		data.put("info","Rating: 80");
		data.put("type", "PPV Movie");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "30";
		data.put("title", "Pitch Perfect 2");
		data.put("price","3.00");
		data.put("info","Rating: 71");
		data.put("type", "PPV Movie");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "31";
		data.put("title", "Poltergeist");
		data.put("price","3.00");
		data.put("info","Rating: 52");
		data.put("type", "PPV Movie");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "32";
		data.put("title", "Ex Machinima");
		data.put("price","3.00");
		data.put("info","Rating: 78");
		data.put("type", "PPV Movie");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "33";
		data.put("title", "Jurassic World");
		data.put("price","3.00");
		data.put("info","Rating: 82");
		data.put("type", "PPV Movie");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "34";
		data.put("title", "Aloha");
		data.put("price","3.00");
		data.put("info","Rating: 53");
		data.put("type", "PPV Movie");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "35";
		data.put("title", "Black Mass");
		data.put("price","3.00");
		data.put("info","Rating: 70");
		data.put("type", "PPV Movie");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "36";
		data.put("title", "The Age of Adaline");
		data.put("price","3.00");
		data.put("info","Rating: 73");
		data.put("type", "PPV Movie");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "37";
		data.put("title", "Spy");
		data.put("price","3.00");
		data.put("info","Rating: 75");
		data.put("type", "PPV Movie");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "38";
		data.put("title", "Furious Seven");
		data.put("price","3.00");
		data.put("info","Rating: 77");
		data.put("type", "PPV Movie");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "39";
		data.put("title", "Chappie");
		data.put("price","3.00");
		data.put("info","Rating: 71");
		data.put("type", "PPV Movie");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "40";
		data.put("title", "Star Wars: Episode VII - The Force Awakens");
		data.put("price","3.00");
		data.put("info","Rating: 99");
		data.put("type", "PPV Movie");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "41";
		data.put("title", "Home");
		data.put("price","3.00");
		data.put("info","Rating: 68");
		data.put("type", "PPV Movie");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "42";
		data.put("title", "Entourage");
		data.put("price","3.00");
		data.put("info","Rating: 82");
		data.put("type", "PPV Movie");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "43";
		data.put("title", "Inside Out");
		data.put("price","3.00");
		data.put("info","Rating: 91");
		data.put("type", "PPV Movie");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "44";
		data.put("title", "Fifty Shades of Grey");
		data.put("price","3.00");
		data.put("info","Rating: 42");
		data.put("type", "PPV Movie");
		products.put(key, data);
		
		data = new HashMap<String,String>();
		key = "45";
		data.put("title", "Focus");
		data.put("price","3.00");
		data.put("type", "PPV Movie");
		data.put("info","Rating: 66");
		products.put(key, data);
	}
	
	public static void main(String args[]) {
		System.out.println(Catalog.getTvPlans());
	}
}