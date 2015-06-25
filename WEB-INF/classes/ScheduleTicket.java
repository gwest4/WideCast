import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.HashMap;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class ScheduleTicket extends HttpServlet {
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    HttpSession session = request.getSession();
    PrintWriter out = response.getWriter();
	StringBuilder sb = new StringBuilder();
	
	HashMap<String,String> userInfo = (HashMap<String,String>)session.getAttribute("userInfo");
	String ticket = request.getParameter("ticket");
	
	if (userInfo == null) {
		response.sendRedirect(response.encodeRedirectUrl(request.getContextPath()+"/SignIn"));
	} else {
		if (userInfo.get("role").equals("technician")) {
			sb.append(LayoutProvider.getInstance().getLoggedInHeader(userInfo.get("name")));
			sb.append("<div id=\"body\">"); 
			sb.append("<h3>Schedule Confirmation</h3><p>You have scheduled <strong>ticket # "+ticket+"</strong></p>");
			if (ticket != null) {
				List<String> tickets;
				try {
					if (userInfo.get("tickets").equals("")) {
						tickets = null;
					} else {
						tickets = Arrays.asList(userInfo.get("tickets").split("\\,"));
					}
				} catch (Exception ex) {
					System.out.println("PayBill: error splitting tickets");
					tickets = null;
				}
				String remaining = "";
				if (tickets!=null && tickets.size() > 0) {
					for (String t: tickets) {
						if (!t.equals(ticket)) {
							remaining += t+",";
						}
					}
					if (remaining.length() > 0)
						remaining = remaining.substring(0,remaining.length()-1);
				} else {
					remaining = "";
				}
				userInfo.put("tickets", remaining);
			}
			sb.append("</div>");
		} else {
			sb.append("<h2>Error</h2>");
			sb.append("<p>You do not have access to this page.</p>");
			sb.append("</div>");
		}
	}
    out.println(sb.toString());
    out.close();
  }
  
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
		  response.sendRedirect(response.encodeRedirectUrl(request.getContextPath()+"/SignIn"));
  }
}
