package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Aluno;

import java.io.IOException;

@WebServlet("/SvExcluirAluno")
public class SvExcluirAluno extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SvExcluirAluno() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String cpf = request.getParameter("cpf");

		Aluno aluno = new Aluno();

		// Localiza o aluno
		if (aluno.localizarAluno(cpf)) {
			// Exclui o aluno, removendo-o da turma se necessário
			if (aluno.excluirAluno()) {
				request.setAttribute("status", "excluirEmCurso");
				request.setAttribute("msg", aluno.getMsg()); // Mensagem de sucesso ou erro
			} else {
				request.setAttribute("status", "erro");
				request.setAttribute("aluno", aluno); // Passa o aluno para a página de erro
			}
		} else {
			// Se o aluno não for encontrado, exibe a mensagem
			request.setAttribute("msg", aluno.getMsg());
		}

		// Redireciona de volta para a página de CRUD do aluno
		request.getRequestDispatcher("crudAluno.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
