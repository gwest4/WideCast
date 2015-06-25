import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map.Entry;

public class Products extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		StringBuilder sb = new StringBuilder();
		String department = request.getParameter("department");
		String customer = request.getParameter("customer");

		HashMap<String,String> userInfo = (HashMap<String,String>)session.getAttribute("userInfo");
		HashMap<String,HashMap<String,String>> products = Catalog.getProducts(department);
		//System.out.println("products: "+products);
		if (userInfo != null) {
			sb.append(LayoutProvider.getInstance().getLoggedInHeader(userInfo.get("name")));
		} else {
			sb.append(LayoutProvider.getInstance().getLoggedOutHeader());
		}
		
		sb.append("<div id=\"body\">");
		sb.append("<h2>Products</h2>\n<p>Please browse our fine selection of products and services. Sort by...</p>");
		sb.append("<table>");
		sb.append("<tr>"+
			"<td><a href=\"Products?department=all\">All</a></td>"+
			"<td><a href=\"Products?department=tv_plans\">TV Plans</a></td>"+
			"<td><a href=\"Products?department=internet_plans\">Internet Plans</a></td>"+
			"<td><a href=\"Products?department=games\">Games</a></td>"+
			"<td><a href=\"Products?department=sport_events\">PPV Sports</a></td>"+
			"<td><a href=\"Products?department=movies\">PPV Movies</a></td>"+
			"</tr>");
		sb.append("</table><br>");
		sb.append("<div id=\"products\">");
		sb.append("<table>");
		sb.append("<tr>"+
			"<th>Name</th>"+
			"<th>Type</th>"+
			"<th>Info</th>"+
			"<th>Price</th>"+
			"<th></th>"+
			"</tr>");
		for (Entry<String,HashMap<String,String>> entry : products.entrySet()) {
			HashMap<String,String> data = (HashMap<String,String>) entry.getValue();
			sb.append("<tr>"+
			"<td>"+data.get("title")+"</td>"+
			"<td>"+data.get("type")+"</td>"+
			"<td>"+data.get("info")+"</td>"+
			"<td>$"+data.get("price")+"</td>"+
			"<td><form action=\"ShoppingCart\">"+
			"<input type=\"hidden\" name=\"add_id\" value=\""+entry.getKey()+"\"></input>");
			if (customer!=null)
				sb.append("<input type=\"hidden\" name=\"customer\" value=\""+customer+"\"></input>");
			sb.append("<input id=\"submit\" type=\"submit\" value=\"Add\"></input></form>"+
			"</td>"+
			"</tr>");
		}
		sb.append("</table>");
		sb.append("</div>");
		sb.append("</div>");
		out.println(sb.toString());
		out.close();
	}
}
