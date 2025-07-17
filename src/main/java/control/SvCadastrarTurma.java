package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Aluno;
import model.Professor;
import model.Turma;

import java.io.IOException;
import java.util.List;

@WebServlet("/SvCadastrarTurma")
public class SvCadastrarTurma extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SvCadastrarTurma() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String nome = request.getParameter("nome").toLowerCase();
		String disciplina = request.getParameter("disciplina");
		String turno = request.getParameter("turno");

		// Recuperando o professor e alunos da sessão
		Professor professor = (Professor) request.getSession().getAttribute("professor");
		@SuppressWarnings("unchecked")
		List<Aluno> alunos = (List<Aluno>) request.getSession().getAttribute("alunosAdicionados");

		// Criando a turma
		Turma turma = new Turma(nome, disciplina, turno, professor, alunos);

		// Associando a turma aos alunos
		for (int i = 0; i < alunos.size(); i++) {
			Aluno aluno = alunos.get(i);
			aluno.setTurma(turma); // Associa a turma ao aluno
		}

		// Salvando a turma no banco de dados
		if (turma.salvar()) {
			// Se a turma foi salva com sucesso
			request.getSession().setAttribute("status", "cadastrado");
			request.getSession().setAttribute("msg", turma.getMsg());

			// Salvando os alunos com a associação de turma
			for (int i = 0; i < alunos.size(); i++) {
				Aluno aluno = alunos.get(i);
				aluno.editarAluno(); // Atualizando o aluno com a turma associada
			}

			// Limpando os dados da sessão após o cadastro
			request.getSession().removeAttribute("professor");
			request.getSession().removeAttribute("alunosAdicionados");

			request.getRequestDispatcher("crudTurmaLocalizarProfessor.jsp").forward(request, response);

		} else {
			// Caso haja erro ao salvar a turma
			request.setAttribute("turma", turma);
			request.setAttribute("status", "erro");
			request.setAttribute("msg", turma.getMsg());

			request.getRequestDispatcher("crudTurma.jsp").forward(request, response);
		}

		// Redireciona para a página de resposta após o cadastro

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
