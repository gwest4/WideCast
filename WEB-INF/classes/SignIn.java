import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.HashMap;

public class SignIn extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		StringBuilder sb = new StringBuilder();
		
		HashMap<String,String> userInfo = (HashMap<String,String>)session.getAttribute("userInfo");
		HashMap<String,String> loginProgress = (HashMap<String,String>)session.getAttribute("loginProgress");
		sb.append(LayoutProvider.getInstance().getLoggedOutHeader());
		if (userInfo != null) {
			// Home servletObject = new Home();
			// servletObject.doPost(request, response);
			response.sendRedirect(response.encodeRedirectUrl(request.getContextPath()+"/Home"));
		}
		String value = "";
		sb.append("<div id=\"body\">");		
		sb.append("<form action=\"SignIn\" method=\"post\">");
		// sb.append("<label for=\"role\">Sign in as: </label>");
		// sb.append("<select id=\"role\" name=\"role\">");
		// sb.append("<option value=\"customer\">Customer</option>");
		// sb.append("<option value=\"manager\">Manager</option>");
		// sb.append("<option value=\"account_specialist\">Account Specialist</option>");
		// sb.append("<option value=\"technician\">Technical Support Specialist</option>");
		// sb.append("</select><br>");
		sb.append("<label for=\"userName\">User name: </label>");
		if (loginProgress != null) {
			value = loginProgress.get("userName");
		} else {
			value = "";
		}
		sb.append("<input id=\"userName\" type=\"text\" name=\"userName\" value=\""+value+"\"></input><br>");
		sb.append("<label for=\"password\">Password: </label>");
		if (loginProgress != null) {
			value = loginProgress.get("password");
		} else {
			value = "";
		}
		sb.append("<input id=\"password\" type=\"password\" name=\"password\" value=\""+value+"\"></input><br>");
		sb.append("<input id=\"submit\" type=\"submit\" value=\"Sign In\"/>");
		sb.append("</form>");
		sb.append("</div>");

		out.println(sb.toString());
		out.close();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		
		HashMap<String,String> userInfo = (HashMap<String,String>)session.getAttribute("userInfo");
		//System.out.println("userInfo: "+userInfo);
		if (userInfo != null) {
			// Home servletObject = new Home();
			// servletObject.doPost(request, response);
			response.sendRedirect(response.encodeRedirectUrl(request.getContextPath()+"/Home"));
		} else {
			//String role = request.getParameter("role");
			String userName = request.getParameter("userName");
			String password = request.getParameter("password");
			if (/*role!=null &&*/ userName!=null && password!=null) {
				HashMap<String,String> loginProgress = new HashMap<String,String>();
				//loginProgress.put("role",role);
				loginProgress.put("userName",userName);
				loginProgress.put("password",password);
				session.setAttribute("loginProgress", loginProgress);
				String stored_pass;
				try {
					stored_pass = Users.getUsers().get(userName).get("password");
				} catch (NullPointerException e) {
					System.out.println("SignIn: could not retrieve stored password");
					stored_pass = null;
				}
				if (stored_pass != null && stored_pass.equals(password)) {
					userInfo = Users.getUsers().get(userName);
					userInfo.put("userName",userName);
					session.setAttribute("userInfo", userInfo);
					System.out.println("set userInfo: "+userInfo);
					// Home servletObject = new Home();
					// servletObject.doPost(request, response);
					response.sendRedirect(response.encodeRedirectUrl(request.getContextPath()+"/Home"));
					session.removeAttribute("loginProgress");
				} else {
					response.sendRedirect(response.encodeRedirectUrl(request.getContextPath()+"/SignIn"));
				}
			}
		}	
	}
}
