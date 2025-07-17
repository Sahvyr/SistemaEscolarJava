package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Aluno;

import java.io.IOException;

@WebServlet("/SvTurmaLocalizarAluno")
public class SvTurmaLocalizarAluno extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SvTurmaLocalizarAluno() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String cpfProcurado = request.getParameter("cpfProcurado");

		Aluno aluno = new Aluno();

		if (aluno.localizarAluno(cpfProcurado)) {
			request.setAttribute("aluno", aluno);
			request.setAttribute("msg", aluno.getMsg());
			request.setAttribute("status", "alunoLocalizado");
		} else {

			request.setAttribute("msg", aluno.getMsg());
			request.setAttribute("status", "semAluno");
		}

		request.getRequestDispatcher("crudTurmaAdicionarAlunos.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
