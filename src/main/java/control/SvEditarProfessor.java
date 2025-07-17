package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Professor;

import java.io.IOException;

@WebServlet("/SvEditarProfessor")
public class SvEditarProfessor extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SvEditarProfessor() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Professor professor = new Professor();

		professor.setId(Long.parseLong(request.getParameter("id")));
		professor.setNome(request.getParameter("nome"));
		professor.setCpf(request.getParameter("cpf"));
		professor.setRg(request.getParameter("rg"));
		professor.setTelefone(request.getParameter("telefone"));
		professor.setSenha(request.getParameter("senha"));
		professor.setEspecialidade(request.getParameter("especialidade"));
		professor.setSalario(Double.parseDouble(request.getParameter("salario")));

		if (professor.editarProfessor()) {
			request.setAttribute("status", "editarEmCurso");
			request.setAttribute("msg", professor.getMsg());
			request.setAttribute("professor", professor);
		} else {
			request.setAttribute("status", "erro");
			request.setAttribute("msg", professor.getMsg());
			request.setAttribute("professor", professor); //
		}

		request.getRequestDispatcher("crudProfessor.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
