import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.HashMap;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class CreateTicket extends HttpServlet {
  public void doPost(HttpServletRequest request, HttpServletResponse response)
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
		
		String assignee = request.getParameter("assignee");
		String creator = request.getParameter("creator");
		String info = request.getParameter("info");
		
		sb.append("<div id=\"body\">"); 
		if (userInfo.get("role").equals("manager") || userInfo.get("role").equals("account_specialist")) {
			if (assignee != null && creator!=null && info!=null) {
				String ticketNumber = Tickets.addTicket(assignee, creator, info);
				String tech = Users.getUser(assignee).get("name");
				sb.append("<h2>Ticket Created</h2>");
				sb.append("<p>Ticket #<strong>"+ticketNumber+"</strong> has been created and assigned to <strong>"+tech+"</strong>.</p>");
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
