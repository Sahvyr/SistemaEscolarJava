package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Turma;

import java.io.IOException;

@WebServlet("/SvLocalizarTurma")
public class SvLocalizarTurma extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SvLocalizarTurma() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String nomeProcurado = request.getParameter("nomeProcurado");

		Turma turma = new Turma();

		if (turma.localizarTurma(nomeProcurado)) {
			request.getSession().setAttribute("turma", turma);
			request.setAttribute("msg", turma.getMsg());
			request.setAttribute("status", "turmaEncontrada");
		} else {
			request.setAttribute("msg", turma.getMsg());
			request.setAttribute("status", "semTurma");
		}

		request.getRequestDispatcher("visualizarTurma.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
