package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Turma;

import java.io.IOException;

/**
 * Servlet implementation class SvEditarTurma
 */
@WebServlet("/SvEditarTurma")
public class SvEditarTurma extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SvEditarTurma() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			long id = Long.parseLong(request.getParameter("id"));
			String nome = request.getParameter("nome");
			String disciplina = request.getParameter("disciplina");
			String turno = request.getParameter("turno");

			Turma turma = new Turma();

			// Localiza a turma original pelo ID
			if (turma.localizarTurma(id)) {

				// Atualiza os dados com os valores do formulário
				turma.setNome(nome);
				turma.setDisciplina(disciplina);
				turma.setTurno(turno);

				if (turma.editarTurma()) {
					request.getSession().setAttribute("status", "editadoComSucesso");
					request.getSession().setAttribute("msg", turma.getMsg());
					request.getSession().removeAttribute("turma");
					response.sendRedirect("visualizarTurma.jsp");
				} else {
					// Erro ao editar
					request.getSession().setAttribute("turma", turma);
					request.setAttribute("status", "erro");
					request.setAttribute("msg", turma.getMsg());
					request.getRequestDispatcher("editarTurma.jsp").forward(request, response);
				}
			} else {
				// Erro ao localizar a turma
				request.setAttribute("status", "erro");
				request.setAttribute("msg", turma.getMsg());
				request.getRequestDispatcher("editarTurma.jsp").forward(request, response);
			}

		} catch (Exception e) {
			request.setAttribute("status", "erro");
			request.setAttribute("msg", "Erro interno ao editar a turma.");
			request.getRequestDispatcher("editarTurma.jsp").forward(request, response);
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
