import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.text.ParseException;


public class ManageTickets extends HttpServlet {
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
		
		String role = userInfo.get("role");
		
		sb.append("<div id=\"body\">");
		if (role.equals("technician")) {
			List<String> tickets;
			try {
				if (userInfo.get("tickets").equals("")) {
					tickets = null;
				} else {
					tickets = Arrays.asList(userInfo.get("tickets").split("\\,"));
				}
			} catch (Exception e) {
				System.out.println("PayBill: error splitting tickets");
				tickets = null;
			}
			
			sb.append("<h2>Manage Tickets</h2><p>To schedule a ticket, select it below.</p>");
			sb.append("<div id=\"products\">");
			if (tickets != null && tickets.size() > 0) {
				sb.append("<table>");
				sb.append("<tr>"+
					"<th>Creator</th>"+
					"<th>Assignee</th>"+
					"<th>Info</th>"+
					"<th></th>"+
					"</tr>");
				for (String ticket: tickets) {
					HashMap<String,String> ticketInfo = Tickets.getTicket(ticket);
					sb.append("<tr>"+
					"<td>"+ticketInfo.get("creator")+"</td>"+
					"<td>"+ticketInfo.get("assignee")+"</td>"+
					"<td>"+ticketInfo.get("info")+"</td>"+
					"<td>");
					sb.append("<form action=\"ScheduleTicket\" method=\"post\">"+
					"<input type=\"hidden\" name=\"ticket\" value=\""+ticket+"\"></input>"+
					"<input id=\"submit\" type=\"submit\" value=\"Schedule\"></input></form>");
					sb.append("</td>"+
					"</tr>");
				}
				sb.append("</table>");
			} else {
				sb.append("<h3>No tickets</h3>");
			}
			sb.append("</div>");
			
		} else if (role.equals("manager") || role.equals("account_specialist")) {
			sb.append("<h2>Create Ticket</h2><p>To create a ticket, fill out the 'Info' box then select a technician. Then click 'Create Ticket'.</p>");
			sb.append("<form action=\"CreateTicket\" method=\"post\">");
			sb.append("<label for=\"info\">Ticket information:  </label>");
			sb.append("<input id=\"info\" name=\"info\" type=\"text\" size=100></input>");
			sb.append("<div id=\"products\">");
			sb.append("<table>");
			sb.append("<tr>"+
				"<th>Technician</th>"+
				"<th>Email</th>"+
				"<th></th>"+
				"</tr>");
			ArrayList<HashMap<String,String>> technicians = Users.getUsersWithRole("technician");
			for (HashMap<String,String> techInfo: technicians) {
				sb.append("<tr>"+
				"<td>"+techInfo.get("name")+"</td>"+
				"<td>"+techInfo.get("email")+"</td>"+
				"<td><input id=\"submit\" name=\"assignee\" type=\"radio\" value=\""+techInfo.get("username")+"\"></input></td>");
				sb.append("</tr>");
			}
			sb.append("</table>");
			sb.append("</div>");
			sb.append("<input type=\"hidden\" name=\"creator\" value=\""+userInfo.get("username")+"\"></input>");
			sb.append("<input id=\"submit\" type=\"submit\" value=\"Submit\"></input>");
			sb.append("</form>");
		} else {
			sb.append("<h2>Error</h2>");
			sb.append("<p>You do not have access to this page.</p>");
			sb.append("</div>");
		}
		sb.append("</div>");
	}
    out.println(sb.toString());
    out.close();
  }
}
