package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Aluno;

import java.io.IOException;
import java.util.List;

@WebServlet("/SvTurmaRemoverAluno")
public class SvTurmaRemoverAluno extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SvTurmaRemoverAluno() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Recupera o CPF do aluno que será removido
		String cpfAluno = request.getParameter("cpfAluno");

		// Recupera a lista de alunos da sessão
		@SuppressWarnings("unchecked")
		List<Aluno> alunosAdicionados = (List<Aluno>) request.getSession().getAttribute("alunosAdicionados");

		if (alunosAdicionados != null) {

			for (int i = 0; i < alunosAdicionados.size(); i++) {
				Aluno a = alunosAdicionados.get(i);
				if (a.getCpf().equals(cpfAluno)) {
					alunosAdicionados.remove(i);
					break;
				}
			}

			// Atualiza a lista de alunos na sessão
			request.getSession().setAttribute("alunosAdicionados", alunosAdicionados);

			request.getRequestDispatcher("crudTurmaAdicionarAlunos.jsp").forward(request, response);

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
