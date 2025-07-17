package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Professor;
import model.Turma;

import java.io.IOException;

@WebServlet("/SvTurmaLocalizarProfessor")
public class SvTurmaLocalizarProfessor extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SvTurmaLocalizarProfessor() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Remove qualquer professor antigo da sessão
		request.getSession().removeAttribute("professor");

		String cpfProcurado = request.getParameter("cpfProcurado");

		Professor professor = new Professor();

		if (professor.localizarProfessor(cpfProcurado)) {

			Turma turma = new Turma();
			boolean professorTemTurma = turma.verificarSeProfessorJaTemTurma(professor.getId());

			if (professorTemTurma) {
				// Não salva o professor na sessão, apenas exibe mensagem
				request.setAttribute("msg", "Este professor já está associado a uma turma.");
				request.setAttribute("status", "professorComTurma");
			} else {
				// Salva o professor na sessão para exibição
				request.getSession().setAttribute("professor", professor);
				request.setAttribute("status", "professorLocalizado");
			}
		} else {
			request.setAttribute("msg", professor.getMsg());
			request.setAttribute("status", "semProfessor");
		}

		System.out.println("Status: " + request.getAttribute("status"));
		System.out.println("Mensagem: " + request.getAttribute("msg"));

		request.getRequestDispatcher("crudTurmaLocalizarProfessor.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
