import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.HashMap;
import java.util.Arrays;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.text.ParseException;


public class ManageEvents extends HttpServlet {
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
		List<String> ppv_evs;
		try {
			if (userInfo.get("events").equals("")) {
				ppv_evs = null;
			} else {
				ppv_evs = Arrays.asList(userInfo.get("events").split("\\,"));
			}
		} catch (Exception e) {
			System.out.println("PayBill: error splitting ppv_evs");
			ppv_evs = null;
		}
		sb.append(LayoutProvider.getInstance().getLoggedInHeader(userInfo.get("name")));
		sb.append("<div id=\"body\">");
		sb.append("<h2>Manage PPV Events</h2><p>To pay a bill, select it below.</p>");
		sb.append("<div id=\"products\">");
		if (ppv_evs != null && ppv_evs.size() > 0) {
			sb.append("<table>");
			sb.append("<tr>"+
				"<th>Name</th>"+
				"<th>Service</th>"+
				"<th>Info</th>"+
				"<th>Balance</th>"+
				"<th></th>"+
				"</tr>");
			for (String e: ppv_evs) {
				HashMap<String,String> ppvInfo = Catalog.getItem(e);
				sb.append("<tr>"+
				"<td>"+ppvInfo.get("title")+"</td>"+
				"<td>"+ppvInfo.get("type")+"</td>"+
				"<td>"+ppvInfo.get("info")+"</td>"+
				"<td>$"+ppvInfo.get("price")+"</td>"+
				"<td>");
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy K:m a");
				Calendar e_cal = Calendar.getInstance();
				Calendar cur_cal = Calendar.getInstance();
				try {
					e_cal.setTime(formatter.parse(ppvInfo.get("info").substring(6)));
				} catch (ParseException ex){
					System.out.println("ManageEvents: error parsing e_cal/setting time");
					ex.printStackTrace();
				}
				// System.out.println(formatter.format(e_cal.getTime()));
				// System.out.println(formatter.format(cur_cal.getTime()));
				long time_diff = e_cal.getTimeInMillis()-cur_cal.getTimeInMillis();
				long hours = ((time_diff/1000)/60)/60;
				//System.out.println("time_diff:"+time_diff);
				//System.out.println("hour_diff:"+hours);
				if (hours > 24.0) {
					sb.append("<form action=\"CancelEvent\" method=\"post\">"+
					"<input type=\"hidden\" name=\"total\" value=\""+ppvInfo.get("price")+"\"></input>"+
					"<input type=\"hidden\" name=\"e\" value=\""+e+"\"></input>"+
					"<input id=\"submit\" type=\"submit\" value=\"Cancel\"></input></form>");
				} else {
					sb.append("<p style=\"color: red;\">This event cannot be cancelled</p>");
				}
				sb.append("</td>"+
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
