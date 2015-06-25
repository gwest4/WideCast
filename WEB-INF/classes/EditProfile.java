import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.HashMap;
import static java.net.URLEncoder.encode;

public class EditProfile extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    HttpSession session = request.getSession();
    PrintWriter out = response.getWriter();
	StringBuilder sb = new StringBuilder();
	
	HashMap<String,String> userInfo = (HashMap<String,String>)session.getAttribute("userInfo");
	if (userInfo == null) {
		response.sendRedirect(response.encodeRedirectUrl(request.getContextPath()+"/SignIn"));
	}
	String error = request.getParameter("error");
	
	sb.append(LayoutProvider.getInstance().getLoggedInHeader(userInfo.get("name")));
	String role = userInfo.get("role");
	String customer = null;
	if (role.equals("manager") || role.equals("account_specialist")) {
		customer = request.getParameter("customer");
	}
	sb.append("<div id=\"body\">");
	if (role.equals("customer")) {
		sb.append("<h2>Edit Profile</h2>");
		if (error != null) {
			sb.append("<h3>There was an error processing your submitted information: "+error+"</h3>");
		}
		sb.append("<p>Please fill out/confirm your profile information</p>");
	} else if (role.equals("manager") || role.equals("account_specialist")) {
		if (customer != null && !(customer.equals(""))) {
			HashMap<String,String> customerInfo = Users.getUser(customer);
			if (customerInfo != null) {
				sb.append("<h3>Editing profile for customer: "+customerInfo.get("name")+"</h3>");
			} else {
				response.sendRedirect(response.encodeRedirectUrl(request.getContextPath()+"/EditProfile?error="+encode("no such customer '"+customer+"'")));
				out.println(sb.toString());
				out.close();
				return;
			}
		} else {
			if (error != null) {
				sb.append("<h3>There was an error processing your submitted information: "+error+"</h3>");
			}
			sb.append("<p>Enter the username of the customer for whom you are providing assistance.</p>");
			sb.append("<form action=\"EditProfile\" method=\"get\">");
			sb.append("<table>");
			sb.append("<tr><td>Customer username</td><td><input name=\"customer\" type=\"text\" required></input></td></tr>");
			sb.append("</table>");
			sb.append("<input id=\"submit\" type=\"submit\" value=\"Submit\"></input>");
			sb.append("</form>");
			sb.append("</div>");
			out.println(sb.toString());
			out.close();
			return;
		}
	} else {
		sb.append("<h2>Error</h2>");
		sb.append("<p>You do not have access to this page.</p>");
		sb.append("</div>");
		out.println(sb.toString());
		out.close();
		return;
	}
	HashMap<String,String> formInfo = new HashMap<String,String>();
	if (role.equals("manager") || role.equals("account_specialist")) {
		formInfo.putAll(Users.getUser(customer));
	} else {
		formInfo.putAll(userInfo);
	}
	sb.append("<form action=\"EditProfile\" method=\"post\">");
	sb.append("<table>");
	sb.append("<tr><td>Name</td><td><input name=\"name\" type=\"text\" value=\""+formInfo.get("name")+"\" required></input></td></tr>" +
		"<tr><td>Email</td><td><input name=\"email\" type=\"email\" value=\""+formInfo.get("email")+"\" required></input></td></tr>" +
		"<tr><td>Password</td><td><input name=\"password_1\" type=\"password\" value=\""+formInfo.get("password")+"\" required></input></td></tr>" +
		"<tr><td>Confirm Password</td><td><input name=\"password_2\" type=\"password\" value=\""+formInfo.get("password")+"\" required></input></td></tr>" +
		"<tr><td>Credit Card #</td><td><input name=\"card\" type=\"number\" value=\""+formInfo.get("card")+"\" required></input></td></tr>" +
		"<tr><td>Card Expiration (mm/yy)</td><td><input name=\"expiration\" type=\"text\" value=\""+formInfo.get("expiration")+"\" required></input></td></tr>" +
		"<tr><td>Address Line 1</td><td><input name=\"addr_1\" type=\"text\" value=\""+formInfo.get("addr_1")+"\" required></input></td></tr>" +
		"<tr><td>Address Line 2</td><td><input name=\"addr_2\" type=\"text\" value=\""+formInfo.get("addr_2")+"\"></input></td></tr>" +
		"<tr><td>City</td><td><input name=\"city\" type=\"text\" value=\""+formInfo.get("city")+"\" required></input></td></tr>");
	sb.append("</table>");
	if (role.equals("manager") || role.equals("account_specialist")) {
		sb.append("<input type=\"hidden\" name=\"customer\" value=\""+customer+"\"></input>");
	}
	sb.append("<input id=\"submit\" type=\"submit\" value=\"Submit\"></input>");
	sb.append("</form>");
	sb.append("</div>");
    out.println(sb.toString());
    out.close();
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    HttpSession session = request.getSession();
	
	HashMap<String,String> userInfo = (HashMap<String,String>)session.getAttribute("userInfo");
	if (userInfo == null) {
		response.sendRedirect(response.encodeRedirectUrl(request.getContextPath()+"/SignIn"));
	}
	String role = userInfo.get("role");
	
	String name = request.getParameter("name");
	String email = request.getParameter("email");
	String password_1 = request.getParameter("password_1");
	String password_2 = request.getParameter("password_2");
	String card = request.getParameter("card");
	String expiration = request.getParameter("expiration");
	String addr_1 = request.getParameter("addr_1");
	String addr_2 = request.getParameter("addr_2");
	String city = request.getParameter("city");
	if (!(name.equals("")) && !(email.equals("")) && !(password_1.equals("")) && !(password_2.equals("")) && !(card.equals("")) && !(expiration.equals(""))
		&& !(addr_1.equals("")) && !(city.equals(""))) {
		if (password_1.equals(password_2)) {
			HashMap<String,String> newData = new HashMap<String,String>();
			if (role.equals("customer")) {
				newData.putAll(userInfo);
				newData.put("name", name);
				newData.put("email", email);
				newData.put("password", password_1);
				newData.put("card", card);
				newData.put("expiration", expiration);
				newData.put("addr_1", addr_1);
				newData.put("addr_2", addr_2);
				newData.put("city", city);
				Users.getUsers().put(userInfo.get("username"),newData);
			} else {
				//This would update the database to reflect changes in user profile
				String customer = request.getParameter("customer");
				HashMap<String,String> customerInfo = Users.getUser(customer);
				newData.putAll(customerInfo);
				newData.putAll(userInfo);
				newData.put("name", name);
				newData.put("email", email);
				newData.put("password", password_1);
				newData.put("card", card);
				newData.put("expiration", expiration);
				newData.put("addr_1", addr_1);
				newData.put("addr_2", addr_2);
				newData.put("city", city);
				Users.getUsers().put(customer,newData);
			}
			response.sendRedirect(response.encodeRedirectUrl(request.getContextPath()+"/Home"));
		} else {
			response.sendRedirect(response.encodeRedirectUrl(request.getContextPath()+"/EditProfile?error="+encode("mismatched passwords")));
		}
	} else {
		response.sendRedirect(response.encodeRedirectUrl(request.getContextPath()+"/EditProfile?error="+encode("missing parameter")));
	}
  }
}
