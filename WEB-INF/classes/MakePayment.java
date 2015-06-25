import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map.Entry;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import static java.net.URLEncoder.encode;

public class MakePayment extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
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
		
		Double subtotal;
		try {
			subtotal = Double.valueOf(request.getParameter("subtotal"));
		} catch (NumberFormatException e){
			System.out.println("MakePayment: error parsing subtotal to double");
			subtotal = 0.0;
			e.printStackTrace();
		}
		String bill = request.getParameter("bill");
		String customer = request.getParameter("customer");
		
		sb.append("<div id=\"body\">");
		sb.append("<h2>Payment</h2>");
		
		if (cart != null && !(cart.isEmpty()) && subtotal > 0) {
			sb.append("<p>Please review your cart. Click 'Continue' to continue with payment process.</p>");
			sb.append("<div id=\"products\">");
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
			}
			sb.append("</table>");
			sb.append("</div>");
			NumberFormat formatter = new DecimalFormat("#0.00");     
			sb.append("<h3>Total: $"+formatter.format(subtotal)+"</h3>");
			sb.append("<form action=\"Payment\" method=\"post\">"+
				"<input type=\"hidden\" name=\"subtotal\" value=\""+subtotal+"\"></input>");
			if (customer!=null)
					sb.append("<input type=\"hidden\" name=\"customer\" value=\""+customer+"\"></input>");
			sb.append("<input id=\"submit\" type=\"submit\" value=\"Continue\"></input></form>");
			session.setAttribute("oldCart", cart);
			session.removeAttribute("cart");
		} else if (subtotal > 0) {
			if (customer!=null) {
				session.setAttribute("customer", customer);
				session.setAttribute("total", subtotal);
				PaymentConfirmation servletObject = new PaymentConfirmation();
				servletObject.doPost(request, response);
				// response.sendRedirect(UrlProvider.getInstance().getBaseUrl(request)
						// + "/RezuMe/PaymentConfirmation");
				out.println(sb.toString());
				out.close();
				return;
			}
			sb.append("<p>Please fill out/confirm your payment information</p>");
			sb.append("<form action=\"PaymentConfirmation\" method=\"post\">");
			sb.append("<table>");
			sb.append("<tr><td>Name</td><td><input name=\"name\" type=\"text\" value=\""+userInfo.get("name")+"\" required></input></td></tr>" +
				"<tr><td>Email</td><td><input name=\"email\" type=\"email\" value=\""+userInfo.get("email")+"\" required></input></td></tr>" +
				"<tr><td>Credit Card #</td><td><input name=\"card\" type=\"number\" value=\""+userInfo.get("card")+"\" required></input></td></tr>" +
				"<tr><td>Card Expiration (mm/yy)</td><td><input name=\"expiration\" type=\"text\" value=\""+userInfo.get("expiration")+"\" required></input></td></tr>" +
				"<tr><td>Address Line 1</td><td><input name=\"addr_1\" type=\"text\" value=\""+userInfo.get("addr_1")+"\" required></input></td></tr>" +
				"<tr><td>Address Line 2</td><td><input name=\"addr_2\" type=\"text\" value=\""+userInfo.get("addr_2")+"\"></input></td></tr>" +
				"<tr><td>City</td><td><input name=\"city\" type=\"text\" value=\""+userInfo.get("city")+"\" required></input></td></tr>");
			sb.append("</table>");
			NumberFormat formatter = new DecimalFormat("#0.00");     
			sb.append("<h3>Total: $"+formatter.format(subtotal)+"</h3>");
			sb.append("<input type=\"hidden\" name=\"total\" value=\""+subtotal+"\"></input>");
			if (bill != null)
				sb.append("<input type=\"hidden\" name=\"bill\" value=\""+bill+"\"></input>");
			sb.append("<input id=\"submit\" type=\"submit\" value=\"Complete Payment\"></input>");
			sb.append("</form>");
		} else {
			response.sendRedirect(response.encodeRedirectUrl(request.getContextPath()+"/ShoppingCart"));
		}
		sb.append("</div>");
		out.println(sb.toString());
		out.close();
	}
}
