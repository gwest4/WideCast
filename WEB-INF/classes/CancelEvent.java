import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.HashMap;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class CancelEvent extends HttpServlet {
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    HttpSession session = request.getSession();
    PrintWriter out = response.getWriter();
	StringBuilder sb = new StringBuilder();
	
	HashMap<String,String> userInfo = (HashMap<String,String>)session.getAttribute("userInfo");
	Double total;
	try {
		total = Double.valueOf(request.getParameter("total"));
	} catch (NumberFormatException e){
		System.out.println("MakePayment: error parsing total to double");
		total = 0.0;
		e.printStackTrace();
	}
	String e = request.getParameter("e");
	
	if (userInfo == null) {
		response.sendRedirect(response.encodeRedirectUrl(request.getContextPath()+"/SignIn"));
	} else {
		sb.append(LayoutProvider.getInstance().getLoggedInHeader(userInfo.get("name")));
		sb.append("<div id=\"body\">");
		NumberFormat formatter = new DecimalFormat("#0.00");  
		HashMap<String,String> eInfo = Catalog.getItem(e);
		sb.append("<h3>Cancel Confirmation</h3><p>You have cancelled the "+eInfo.get("type")+" <strong>"+eInfo.get("title")+"</strong>.<br>Your refund of " +
			"<strong>$"+formatter.format(total)+"</strong> is being processed.</p>");
		if (e != null) {
			List<String> ppv_evs;
			try {
				if (userInfo.get("events").equals("")) {
					ppv_evs = null;
				} else {
					ppv_evs = Arrays.asList(userInfo.get("events").split("\\,"));
				}
			} catch (Exception ex) {
				System.out.println("PayBill: error splitting ppv_evs");
				ppv_evs = null;
			}
			String remaining_evs = "";
			if (ppv_evs!=null && ppv_evs.size() > 0) {
				for (String ev: ppv_evs) {
					if (!ev.equals(e)) {
						remaining_evs += ev+",";
					}
				}
				if (remaining_evs.length() > 0)
					remaining_evs = remaining_evs.substring(0,remaining_evs.length()-1);
			} else {
				remaining_evs = "";
			}
			userInfo.put("events", remaining_evs);
		}
		sb.append("</div>");
	}
    out.println(sb.toString());
    out.close();
  }
  
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
		  response.sendRedirect(response.encodeRedirectUrl(request.getContextPath()+"/SignIn"));
  }
}
