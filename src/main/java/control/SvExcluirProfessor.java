package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Professor;

import java.io.IOException;

@WebServlet("/SvExcluirProfessor")
public class SvExcluirProfessor extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SvExcluirProfessor() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String cpf = request.getParameter("cpf");

		Professor prof = new Professor();

		if (prof.localizarProfessor(cpf)) {
			if (prof.excluirProfessor()) {
				request.setAttribute("status", "excluirEmCurso");
				request.setAttribute("msg", prof.getMsg());
			} else {
				request.setAttribute("status", "erro");
				request.setAttribute("professor", prof);
				request.setAttribute("msg", prof.getMsg()); 
			}
		} else {
			request.setAttribute("msg", prof.getMsg());
		}

		request.getRequestDispatcher("crudProfessor.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
