import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.HashMap;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import static java.net.URLEncoder.encode;

public class PaymentConfirmation extends HttpServlet {
  static int order = 847231;
	
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    HttpSession session = request.getSession();
    PrintWriter out = response.getWriter();
	StringBuilder sb = new StringBuilder();
	
	HashMap<String,String> userInfo = (HashMap<String,String>)session.getAttribute("userInfo");
	Double total;
	try {
		String s = request.getParameter("total");
		if (s==null) {
			total = (Double)session.getAttribute("total");
			session.removeAttribute("total");
		} else {
			total = Double.valueOf(s);
		}
	} catch (NumberFormatException e){
		System.out.println("MakePayment: error parsing total to double");
		total = 0.0;
		e.printStackTrace();
	}
	String bill = request.getParameter("bill");
	String customer = (String) session.getAttribute("customer");
	if (customer!=null) {
		session.removeAttribute("customer");
	}
	
	if (userInfo == null) {
		response.sendRedirect(response.encodeRedirectUrl(request.getContextPath()+"/SignIn"));
	} else {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String card = request.getParameter("card");
		String expiration = request.getParameter("expiration");
		String addr_1 = request.getParameter("addr_1");
		String addr_2 = request.getParameter("addr_2");
		String city = request.getParameter("city");
		if (customer!=null) {
			HashMap<String,String> customerInfo = Users.getUser(customer);
			name = customerInfo.get("name");
			email = customerInfo.get("email");
			card = customerInfo.get("card");
			expiration = customerInfo.get("expiration");
			addr_1 = customerInfo.get("addr_1");
			addr_2 = customerInfo.get("addr_2");
			city = customerInfo.get("city");
		}
		if (!(name.equals("")) && !(email.equals("")) && !(card.equals("")) && !(expiration.equals("")) && !(addr_1.equals("")) && !(city.equals(""))) {
			HashMap<String,String> orderData = new HashMap<String,String>();
			orderData.put("name", name);
			orderData.put("email", email);
			orderData.put("card", card);
			orderData.put("expiration", expiration);
			orderData.put("addr_1", addr_1);
			orderData.put("addr_2", addr_2);
			orderData.put("city", city);
			order++;
			Orders.addOrder(String.valueOf(order), orderData);
		} else {
			response.sendRedirect(response.encodeRedirectUrl(request.getContextPath()+"/Payment?error="+encode("missing parameter")+"&subtotal="+total));
		}
		
		sb.append(LayoutProvider.getInstance().getLoggedInHeader(userInfo.get("name")));
		sb.append("<div id=\"body\">");
		NumberFormat formatter = new DecimalFormat("#0.00"); 
		if (userInfo.get("role").equals("customer"))
			sb.append("<h3>Payment Confirmation</h3><p>Your payment of <strong>$"+formatter.format(total)+"</strong> is being processed.</p>");
		if (userInfo.get("role").equals("manager") || userInfo.get("role").equals("account_specialist"))
			sb.append("<h3>Payment Confirmation</h3><p>A bill has been sent to the customer (username:"+customer+")</p>");
		if (bill != null) {
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
			String remaining_bills = "";
			if (bills!=null && bills.size() > 0) {
				for (String b: bills) {
					if (!b.equals(bill)) {
						remaining_bills += b+",";
					}
				}
				if (remaining_bills.length() > 0)
					remaining_bills = remaining_bills.substring(0,remaining_bills.length()-1);
			} else {
				remaining_bills = "";
			}
			userInfo.put("bills", remaining_bills);
			
			HashMap<String,String> billInfo = Catalog.getItem(bill);
			sb.append("<p>You have paid off this month's <strong>"+billInfo.get("title")+"</strong> "+billInfo.get("type")+".</p>");
		}
		sb.append("</div>");
		session.removeAttribute("oldCart");
	}
    out.println(sb.toString());
    out.close();
  }
  
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
		  response.sendRedirect(response.encodeRedirectUrl(request.getContextPath()+"/SignIn"));
  }
}
