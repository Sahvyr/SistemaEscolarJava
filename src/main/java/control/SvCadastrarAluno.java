package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Aluno;

import java.io.IOException;

@WebServlet("/SvCadastrarAluno")
public class SvCadastrarAluno extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SvCadastrarAluno() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String nome = request.getParameter("nome");
		String cpf = request.getParameter("cpf");
		String rg = request.getParameter("rg");
		String telefone = request.getParameter("telefone");
		String senha = request.getParameter("senha");
		String matricula = request.getParameter("matricula");

		Aluno aluno = new Aluno(nome, cpf, rg, telefone, senha, matricula, null);

		if (aluno.salvar()) {
			request.setAttribute("status", "cadastrado");
			request.setAttribute("msg", aluno.getMsg());
		} else {
			request.setAttribute("status", "erro");
			request.setAttribute("aluno", aluno);
			request.setAttribute("msg", aluno.getMsg());

		}

		request.getRequestDispatcher("crudAluno.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
