import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.HashMap;

public class Home extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    HttpSession session = request.getSession();
    PrintWriter out = response.getWriter();
	StringBuilder sb = new StringBuilder();
	
	HashMap<String,String> userInfo = (HashMap<String,String>)session.getAttribute("userInfo");
	if (userInfo == null) {
		response.sendRedirect(response.encodeRedirectUrl(request.getContextPath()+"/SignIn"));
	} else {
		sb.append(LayoutProvider.getInstance().getLoggedInHeader(userInfo.get("name")));
		sb.append("<div id=\"body\">");
		String role = userInfo.get("role");
		if (role.equals("customer")) {
			sb.append("<h2>Welcome to WideCast</h2>");
			sb.append("<form action=\"Products\" method=\"get\">"+
			"<input type=\"hidden\" name=\"department\" value=\"all\"></input>"+
			"<input id=\"submit\" type=\"submit\" value=\"Shop for products and services\"></input></form>");
			sb.append(LayoutProvider.getInstance().getSingleButtonForm("ManageEvents","get","Manage subscribed PPV Events"));
			sb.append(LayoutProvider.getInstance().getSingleButtonForm("PayBill","get","Pay bill"));
			sb.append(LayoutProvider.getInstance().getSingleButtonForm("EditProfile","get","Edit profile information"));
			sb.append(LayoutProvider.getInstance().getSingleButtonForm("ShoppingCart","get","Shopping Cart"));
		} else if (role.equals("manager")) {
			sb.append("<h2>Management Dashboard</h2>");
			sb.append("<h4>Manage Technical Support</h4>");
			sb.append(LayoutProvider.getInstance().getSingleButtonForm("Tickets","get","Create a new incident ticket"));
			sb.append("<h4>Manage Customer Support</h4>");
			sb.append(LayoutProvider.getInstance().getSingleButtonForm("EditProfile","get","Edit a customer's information"));
			sb.append(LayoutProvider.getInstance().getSingleButtonForm("CreateAccount","get","Create a new customer account"));
			sb.append("<form action=\"Products\" method=\"get\">"+
			"<input type=\"hidden\" name=\"department\" value=\"all\"></input>"+
			"<input id=\"submit\" type=\"submit\" value=\"Create an order for a customer\"></input></form>");
		} else if (role.equals("technician")) {
			sb.append("<h2>Technical Support Dashboard</h2>");
			sb.append(LayoutProvider.getInstance().getSingleButtonForm("Tickets","get","Manage incident tickets"));
		} else if (role.equals("account_specialist")) {
			sb.append("<h2>Customer Support Dashboard</h2>");
			sb.append(LayoutProvider.getInstance().getSingleButtonForm("EditProfile","get","Edit a customer's information"));
			sb.append(LayoutProvider.getInstance().getSingleButtonForm("CreateAccount","get","Create a new customer account"));
			sb.append(LayoutProvider.getInstance().getSingleButtonForm("Tickets","get","Create a new incident ticket"));
			sb.append("<form action=\"Products\" method=\"get\">"+
			"<input type=\"hidden\" name=\"department\" value=\"all\"></input>"+
			"<input id=\"submit\" type=\"submit\" value=\"Create an order for a customer\"></input></form>");
		}
		sb.append("</div>");
	}
    out.println(sb.toString());
    out.close();
  }
}
