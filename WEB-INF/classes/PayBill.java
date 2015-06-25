import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.HashMap;
import java.util.Arrays;
import java.util.List;

public class PayBill extends HttpServlet {
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
		List<String> bills;
		try {
			if (userInfo.get("bills").equals("")) {
				bills = null;
			} else {
				bills = Arrays.asList(userInfo.get("bills").split("\\,"));
			}
		} catch (Exception e) {
			System.out.println("PayBill: error splitting bills");
			bills = null;
		}
		sb.append(LayoutProvider.getInstance().getLoggedInHeader(userInfo.get("name")));
		sb.append("<div id=\"body\">");
		sb.append("<h2>Pay Bill</h2><p>To pay a bill, select it below.</p>");
		sb.append("<div id=\"products\">");
		if (bills != null && bills.size() > 0) {
			sb.append("<table>");
			sb.append("<tr>"+
				"<th>Name</th>"+
				"<th>Service</th>"+
				"<th>Info</th>"+
				"<th>Balance</th>"+
				"<th></th>"+
				"</tr>");
			for (String bill: bills) {
				HashMap<String,String> billInfo = Catalog.getItem(bill);
				sb.append("<tr>"+
				"<td>"+billInfo.get("title")+"</td>"+
				"<td>"+billInfo.get("type")+"</td>"+
				"<td>"+billInfo.get("info")+"</td>"+
				"<td>$"+billInfo.get("price")+"</td>"+
				"<td><form action=\"Payment\" method=\"post\">"+
				"<input type=\"hidden\" name=\"subtotal\" value=\""+billInfo.get("price")+"\"></input>"+
				"<input type=\"hidden\" name=\"bill\" value=\""+bill+"\"></input>"+
				"<input id=\"submit\" type=\"submit\" value=\"Pay\"></input></form>"+
				"</td>"+
				"</tr>");
			}
			sb.append("</table>");
		} else {
			sb.append("<h3>No outstanding balances</h3>");
		}
		sb.append("</div>");
		sb.append("</div>");
	}
    out.println(sb.toString());
    out.close();
  }
}
