$output.java($WebUtil, "WelcomeServlet")##

$output.require("java.io.IOException")##

$output.require("javax.servlet.ServletException")##
$output.require("javax.servlet.annotation.WebServlet")##
$output.require("javax.servlet.http.HttpServlet")##
$output.require("javax.servlet.http.HttpServletRequest")##
$output.require("javax.servlet.http.HttpServletResponse")##

@WebServlet(name="WelcomeServlet", urlPatterns={"/"})
public class $output.currentClass extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2480615332341524747L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		super.doGet(req, resp);
		resp.sendRedirect("home.faces");
	}

}
