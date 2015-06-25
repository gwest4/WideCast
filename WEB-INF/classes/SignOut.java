import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.HashMap;

public class SignOut extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html");
    HttpSession session = request.getSession();
    PrintWriter out = response.getWriter();
	StringBuilder sb = new StringBuilder();
	
	session.removeAttribute("userInfo");
	session.removeAttribute("cart");
	session.removeAttribute("oldCart");
	
	//session.invalidate();
	//System.out.println("invalidated session");
	
	sb.append(LayoutProvider.getInstance().getLoggedOutHeader());
	sb.append("<div id=\"body\">");
	sb.append("<h2>Signed Out</h2>\n<p>You have been signed out. To sign back in, click the 'Sign In' button</p>");
	sb.append("</div>");
    out.println(sb.toString());
    out.close();
  }
}
