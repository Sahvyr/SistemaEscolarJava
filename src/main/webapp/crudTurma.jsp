<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Turma"%>
<%@ page import="model.Professor"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin - Sistema Escolar</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Bootstrap -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<!-- Bootstrap Icons -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">

<!-- Google Font: Montserrat -->
<link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;600&display=swap" rel="stylesheet">

<!-- Meu CSS  -->
<link rel="stylesheet" href="css/indexADM.css">
<link rel="stylesheet" href="css/crud.css">
</head>


<%
Turma turma = (Turma) request.getAttribute("turma");
Professor professor = (Professor) request.getSession().getAttribute("professor");
%>

<body>
	<!-- Barra Lateral -->
	<nav class="bg-midnight p-4 vh-100 d-flex flex-column" style="width: 250px;">
		<div>
			<h2 class="text-purple mb-4">
				<i class="bi bi-person-gear me-1"></i> Admin
			</h2>

			<ul class="nav flex-column gap-2">
				<li class="nav-item"><a class="nav-link text-white" href="indexADM.jsp"> <i class="bi bi-house-door me-2"></i> Início
				</a></li>
				<li class="nav-item"><a class="nav-link text-white" href="crudProfessor.jsp"> <i class="bi bi-person-lines-fill me-2"></i> Professores
				</a></li>
				<li class="nav-item"><a class="nav-link text-white" href="crudAluno.jsp"> <i class="bi bi-person-badge me-2"></i> Alunos
				</a></li>
				<!-- Dropdown de Turmas -->
				<li class="nav-item dropdown"><a class="nav-link dropdown-toggle text-white" href="#" id="turmasDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false"> <i class="bi bi-house-door me-2"></i> Turmas
				</a>
					<ul class="dropdown-menu bg-dark" aria-labelledby="turmasDropdown">
						<li><a class="dropdown-item text-white" href="visualizarTurma.jsp">Visualizar Turmas</a></li>
						<li><a class="dropdown-item text-white" href="crudTurmaLocalizarProfessor.jsp">Cadastrar Turmas</a></li>
					</ul></li>
				<li class="nav-item mt-5"><a href="SvLogout" class="nav-link text-danger"> <i class="bi bi-door-closed me-2"></i> Sair
				</a></li>
			</ul>
		</div>

		<!-- Rodapé da barra lateral -->
		<div class="mt-auto text-center text-secondary small pt-4">&copy; 2025 @Sistema Escolar</div>
	</nav>

	<main>



		<!-- Formulário de Cadastro / Edição de Aluno -->
		<div class="card bg-midnight border-0 p-4 shadow">
			<h1 class="mb-4 text-center" >Sistema Escolar</h1>
			<h5>Cadastrar Turma</h5>

			<!-- Formulário para Cadastro ou Edição -->
			<form method="get">

				<!-- Campo oculto para o ID -->
				<input type="hidden" id="iid" name="id" value="<%=turma != null ? turma.getId() : ""%>">

				<!-- Campo de Nome -->
				<div class="mb-3">
					<label for="inome" class="form-label">Nome</label>
					<input type="text" class="form-control input-dark" id="inome" name="nome" maxlength="100" value="<%=turma != null ? turma.getNome() : ""%>" required>
				</div>

				<!-- Campo de Disciplina -->
				<div class="mb-3">
					<label for="idisciplina" class="form-label">Disciplina</label>
					<input type="text" class="form-control input-dark" id="idisciplina" name="disciplina" value="<%=turma != null ? turma.getDisciplina() : ""%>" required>
				</div>

				<!-- Campo de Turno -->
				<div class="mb-3">
					<label for="iturno" class="form-label">Turno</label>
					<select class="form-select input-dark" id="iturno" name="turno" required>
						<option value="manha" <%="manha".equals(request.getAttribute("turno")) ? "selected" : ""%>>Manhã</option>
						<option value="tarde" <%="tarde".equals(request.getAttribute("turno")) ? "selected" : ""%>>Tarde</option>
						<option value="noite" <%="noite".equals(request.getAttribute("turno")) ? "selected" : ""%>>Noite</option>
					</select>
				</div>

				<!-- Campo de Professor -->
				<div class="mb-3">
					<label for="iprofessor" class="form-label">Professor</label>
					<input type="text" class="form-control input-dark" id="iprofessor" name="professor" value="<%=professor != null ? professor.getNome() : ""%>" readonly required>
				</div>




				<%
				String status = (String) request.getAttribute("status");
				String msg = (String) request.getAttribute("msg");
				if ("erro".equals(status) && msg != null) {
				%>
				<div class="alert alert-danger fw-semibold" role="alert">
					<%=msg%>
				</div>
				<%
				}
				%>



				<div class="d-flex justify-content-center mt-4">


					<button type="submit" class="btn btn-purple w-25" name="action" formaction="SvCadastrarTurma" value="cadastrar">Finalizar Turma</button>


				</div>

			</form>
		</div>

	</main>



</body>
</html>