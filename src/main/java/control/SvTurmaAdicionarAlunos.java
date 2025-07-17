package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Aluno;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/SvTurmaAdicionarAlunos")
public class SvTurmaAdicionarAlunos extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SvTurmaAdicionarAlunos() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String cpf = request.getParameter("cpfAluno");
		Aluno aluno = new Aluno();

		if (aluno.localizarAluno(cpf)) {

			// Recupera a lista de alunos da sessão (se não existir, cria uma nova lista)
			@SuppressWarnings("unchecked")
			List<Aluno> lista = (List<Aluno>) request.getSession().getAttribute("alunosAdicionados");

			if (lista == null) {
				lista = new ArrayList<>();
			}

			// Verifica se o aluno já foi adicionado à lista
			if (!lista.contains(aluno)) {
				lista.add(aluno); // Adiciona o aluno à lista
				request.getSession().setAttribute("alunosAdicionados", lista); // Atualiza a lista na sessão
				request.setAttribute("msg", "ALUNO ADICIONADO À LISTA.");
				request.setAttribute("status", "alunoAdicionado");
			} else {
				request.setAttribute("msg", "ALUNO JÁ FOI ADICIONADO.");
				request.setAttribute("status", "erro");
			}

		} else {
			// Caso não encontre o aluno, exibe a mensagem de erro
			request.setAttribute("msg", aluno.getMsg());
			request.setAttribute("status", "erro");
		}

		// Redireciona para a página de feedback de adição de aluno
		request.getRequestDispatcher("crudTurmaAdicionarAlunos.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
