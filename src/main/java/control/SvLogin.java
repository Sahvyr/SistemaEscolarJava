package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/SvLogin")
public class SvLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SvLogin() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String cpf = request.getParameter("cpf");
		String senha = request.getParameter("senha");

		if (cpf.equalsIgnoreCase("admin") && senha.equalsIgnoreCase("admin")) {
			request.setAttribute("status", "admin");
			request.getRequestDispatcher("indexADM.jsp").forward(request, response);
		} else {
			request.setAttribute("msg", "CPF ou senha incorretos. Tente novamente!");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
