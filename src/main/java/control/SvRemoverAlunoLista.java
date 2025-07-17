package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Aluno;
import model.Turma;

import java.io.IOException;
import java.util.List;

@WebServlet("/SvRemoverAlunoLista")
public class SvRemoverAlunoLista extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SvRemoverAlunoLista() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Recupera o CPF do aluno
		String cpf = request.getParameter("cpfAluno");

		// Recupera a turma da sessão
		Turma turma = (Turma) request.getSession().getAttribute("turma");

		if (turma != null && cpf != null && !cpf.trim().isEmpty()) {
			Aluno alunoParaRemover = null;

			// Procura o aluno na lista da turma
			List<Aluno> alunos = turma.getAlunos();
			for (int i = 0; i < alunos.size(); i++) {
				if (alunos.get(i).getCpf().equals(cpf)) {
					alunoParaRemover = alunos.get(i);
					break;
				}
			}

			if (alunoParaRemover != null) {
				if (turma.removerAluno(alunoParaRemover)) {
					request.setAttribute("status", "turmaEncontrada");
					request.setAttribute("msg", "Aluno removido com sucesso!");
				} else {
					request.setAttribute("status", "erro");
					request.setAttribute("msg", "Erro ao tentar remover o aluno.");
				}
			} else {
				request.setAttribute("status", "erro");
				request.setAttribute("msg", "Aluno não encontrado na turma.");
			}
		} else {
			request.setAttribute("status", "erro");
			request.setAttribute("msg", "Turma ou CPF inválido.");
		}

		// Reenvia a turma atualizada para o JSP
		request.getSession().setAttribute("turma", turma);
		request.getRequestDispatcher("visualizarTurma.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
