package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Aluno;

import java.io.IOException;

@WebServlet("/SvEditarAluno")
public class SvEditarAluno extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SvEditarAluno() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Aluno aluno = new Aluno();

		aluno.setId(Long.parseLong(request.getParameter("id")));
		aluno.setNome(request.getParameter("nome"));
		aluno.setCpf(request.getParameter("cpf"));
		aluno.setRg(request.getParameter("rg"));
		aluno.setTelefone(request.getParameter("telefone"));
		aluno.setSenha(request.getParameter("senha"));
		aluno.setMatricula(request.getParameter("matricula"));

		if (aluno.editarAluno()) {
			request.setAttribute("status", "editarEmCurso");
			request.setAttribute("msg", aluno.getMsg());
			request.setAttribute("aluno", aluno);
		} else {
			request.setAttribute("status", "erro");
			request.setAttribute("msg", aluno.getMsg());
			request.setAttribute("aluno", aluno); 
		}

		request.getRequestDispatcher("crudAluno.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
