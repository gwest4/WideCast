import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map.Entry;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import static java.net.URLEncoder.encode;

public class ShoppingCart extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		StringBuilder sb = new StringBuilder();

		HashMap<String,String> userInfo = (HashMap<String,String>)session.getAttribute("userInfo");
		HashMap<String,HashMap<String,String>> cart = (HashMap<String,HashMap<String,String>>) session.getAttribute("cart");
		if (userInfo != null) {
			sb.append(LayoutProvider.getInstance().getLoggedInHeader(userInfo.get("name")));
		} else {
			response.sendRedirect(response.encodeRedirectUrl(request.getContextPath()+"/SignIn"));
		}
		String error = request.getParameter("error");
		String role = userInfo.get("role");
		String customer = null;
		if (role.equals("manager") || role.equals("account_specialist")) {
			customer = request.getParameter("customer");
		}
		
		if (cart == null) {
			cart = (HashMap<String,HashMap<String,String>>) session.getAttribute("oldCart");
			if (cart == null)
				cart = new HashMap<String,HashMap<String,String>>();
			session.setAttribute("cart", cart);
		}
		String add_id = request.getParameter("add_id");
		String remove_id = request.getParameter("remove_id");
		double subtotal = 0.0;
		
		sb.append("<div id=\"body\">");
		if (role.equals("customer")) {
			sb.append("<h2>Shopping Cart</h2>");
		} else if(role.equals("manager") || role.equals("account_specialist")) {
			if (customer != null && !(customer.equals(""))) {
				HashMap<String,String> customerInfo = Users.getUser(customer);
				if (customerInfo != null) {
					sb.append("<h3>Editing profile for customer: "+customerInfo.get("name")+"</h3>");
				} else {
					response.sendRedirect(response.encodeRedirectUrl(request.getContextPath()+"/ShoppingCart?error="+encode("no such customer '"+customer+"'")+
						"&add_id="+add_id+"&remove_id="+remove_id));
					out.println(sb.toString());
					out.close();
					return;
				}
			} else {
				if (error != null) {
					sb.append("<h3>There was an error processing your submitted information: "+error+"</h3>");
				}
				sb.append("<p>Enter the username of the customer for whom you are providing assistance.</p>");
				sb.append("<form action=\"ShoppingCart\" method=\"get\">");
				sb.append("<table>");
				sb.append("<tr><td>Customer username</td><td><input name=\"customer\" type=\"text\" required></input></td></tr>");
				sb.append("</table>");
				if (add_id!=null)
					sb.append("<input type=\"hidden\" name=\"add_id\" value=\""+add_id+"\"></input>");
				if (remove_id!=null)
					sb.append("<input type=\"hidden\" name=\"remove_id\" value=\""+remove_id+"\"></input>");
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
		if (add_id != null) {
			cart.put(add_id, Catalog.getItem(add_id));
			sb.append("<h3>An item has been added to your cart.</h3>");
		} else if (remove_id != null){
			cart.remove(remove_id);
			sb.append("<h3>An item has been removed from your cart.</h3>");
		}
		String URLparam = "";
		if (role.equals("manager") || role.equals("account_specialist")) {
			URLparam = "&customer="+customer;
		}
		if (!cart.isEmpty()) {
			sb.append("<p>To check out, click the 'Check Out' button under the list of cart items, or " +
			"<a href=\"Products?department=all"+URLparam+"\">continue shopping</a></p>");
		} else {
			sb.append("<p>Your cart is empty. Click <a href=\"Products?department=all"+URLparam+"\">here</a> to browse our products and services.</p>");
		}
		sb.append("<div id=\"products\">");
		if (!cart.isEmpty()) {
			sb.append("<table>");
			sb.append("<tr>"+
				"<th>Name</th>"+
				"<th>Type</th>"+
				"<th>Info</th>"+
				"<th>Price</th>"+
				"<th></th>"+
				"</tr>");
			for (Entry<String,HashMap<String,String>> entry : cart.entrySet()) {
				HashMap<String,String> data = (HashMap<String,String>) entry.getValue();
				sb.append("<tr>"+
				"<td>"+data.get("title")+"</td>"+
				"<td>"+data.get("type")+"</td>"+
				"<td>"+data.get("info")+"</td>"+
				"<td>$"+data.get("price")+"</td>"+
				"<td><form action=\"ShoppingCart\">"+
				"<input type=\"hidden\" name=\"remove_id\" value=\""+entry.getKey()+"\"></input>");
				if (customer!=null)
					sb.append("<input type=\"hidden\" name=\"customer\" value=\""+customer+"\"></input>");
				sb.append("<input id=\"submit\" type=\"submit\" value=\"Remove\"></input></form>"+
				"</td>"+
				"</tr>");
				subtotal += Double.valueOf(data.get("price"));
			}
			sb.append("</table>");
		} else {
			sb.append("<h3>Your cart is empty</h3>");
		}
		sb.append("</div>");
		NumberFormat formatter = new DecimalFormat("#0.00");     
		sb.append("<h3>Subtotal: $"+formatter.format(subtotal)+"</h3>");
		if (!cart.isEmpty()) {
			sb.append("<form action=\"Payment\" method=\"post\">");
			if (role.equals("manager") || role.equals("account_specialist"))
				sb.append("<input type=\"hidden\" name=\"customer\" value=\""+customer+"\"></input>");
			sb.append("<input type=\"hidden\" name=\"subtotal\" value=\""+subtotal+"\"></input>"+
					"<input id=\"submit\" type=\"submit\" value=\"Check Out\"></input></form>");
		}
		sb.append("</div>");
		out.println(sb.toString());
		out.close();
	}
}
