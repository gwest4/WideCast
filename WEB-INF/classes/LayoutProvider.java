import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class LayoutProvider {
	private static LayoutProvider instance;
	private String css = "<link rel =\"stylesheet\" type=\"text/css\" href=\"./css/stylesheet.css\"></link>";
	private String loggedOutHeader;
	private String loggedInHeader;
	
	private LayoutProvider() {
		this.loggedOutHeader = getFileAsString("loggedOutHeader.html");
	}
	
	public static LayoutProvider getInstance() {
		if (instance == null) {
			instance = new LayoutProvider();
		}
		return instance;
	}
	
	public String getLoggedInHeader () { return getLoggedInHeader(null); }
	public String getLoggedInHeader (String userName) {
		return css+"<div id=\"header\">"+
			"<h1>WideCast</h1><form action=\"SignOut\" method=\"get\">"+
			"<label for=\"headform\">Signed in as <strong>"+userName+"</strong>  </label>"+
			"<input id=\"headform\" type=\"submit\" value=\"Sign Out\"/></form>"+
			"<form action=\"Home\" method=\"get\">"+
			"<input id=\"headform\" type=\"submit\" value=\"Home\"/></form></div>";
	}
	
	public String getLoggedOutHeader () {
		return css+loggedOutHeader;
	}
	
	public String getSingleButtonForm(String action, String method, String buttonValue) { return getSingleButtonForm(action,method,buttonValue,null); }
	public String getSingleButtonForm(String action, String method, String buttonValue, String label) {
		if (label == null) {
			label = "";
		} else {
			label = "<label for=\"submit\">"+label+"</label>";
		}
		return "<form action=\"" + action +
			"\" method=\""+ method + "\">"+label+"<input id=\"submit\" type=\"submit\" value=\""+ buttonValue +
			"\"></input></form>";
	}
	
	private String getFileAsString(String path) {
		try {
			URL url = getClass().getResource("html-elements/"+path);
			File loggedOutHeaderFile = new File(url.getPath());
			char c;
			int data;
			String output = "";
			if (loggedOutHeaderFile.canRead()) {
				FileReader reader = new FileReader(loggedOutHeaderFile);
				while ( (data=reader.read()) != -1) {
					c = (char) data;
					output += c;
				}
				return output;
			} else {
				System.out.println("LayoutProvider: couldn't find '"+path+"'");
			}
			return null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		LayoutProvider c = new LayoutProvider();
		System.out.println(c.getLoggedInHeader());
		System.out.println(c.getSingleButtonForm("SignIn", "post", "Sign In"));
		System.out.println(c.getSingleButtonForm("SignIn", "post", "Sign In","This is a label"));
	}
}
