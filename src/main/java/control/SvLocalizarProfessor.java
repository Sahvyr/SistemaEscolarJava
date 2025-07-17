package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Professor;

import java.io.IOException;

@WebServlet("/SvLocalizarProfessor")
public class SvLocalizarProfessor extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SvLocalizarProfessor() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String cpfProcurado = request.getParameter("cpfProcurado");

		Professor x = new Professor();

		if (x.localizarProfessor(cpfProcurado)) {
			request.setAttribute("professor", x);

			request.setAttribute("status", "professorLocalizado");
		} else {

			request.setAttribute("msg", x.getMsg());
			request.setAttribute("status", "semProfessor");
		}

		String especialidade = x.getEspecialidade();
		request.setAttribute("especialidade", especialidade);

		request.getRequestDispatcher("crudProfessor.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
