<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
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
</head>

<body class="bg-dark-blue text-white">

	<%
	String status = (String) session.getAttribute("status");
	String msg = (String) session.getAttribute("msg");

	if (status != null && msg != null) {
	%>
	<div class="alert <%="cadastrado".equals(status) ? "alert-success" : "alert-danger"%> alert-dismissible fade show mx-4 mt-3" role="alert">
		<%=msg%>
		<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
	</div>
	<%
	// Limpa após mostrar
	session.removeAttribute("status");
	session.removeAttribute("msg");
	}
	%>

	<div class="d-flex">

		<!-- Barra Lateral -->
		<nav class="bg-midnight p-4 vh-100 d-flex flex-column" style="width: 250px;">
			<div>
				<h2 class="text-purple mb-4">
					<i class="bi bi-person-gear me-1"></i> Admin
				</h2>

				<ul class="nav flex-column gap-2">
					<!-- Link para voltar ao índice -->
					<li class="nav-item"><a class="nav-link text-white" href="indexADM.jsp"> <i class="bi bi-house-door me-2"></i> Início
					</a></li>

					<!-- Link para a página de Professores -->
					<li class="nav-item"><a class="nav-link text-white" href="crudProfessor.jsp"> <i class="bi bi-person-lines-fill me-2"></i> Professores
					</a></li>

					<!-- Link para a página de Alunos -->
					<li class="nav-item"><a class="nav-link text-white" href="crudAluno.jsp"> <i class="bi bi-person-badge me-2"></i> Alunos
					</a></li>

					<!-- Dropdown de Turmas -->
					<li class="nav-item dropdown"><a class="nav-link dropdown-toggle text-white" href="#" id="turmasDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false"> <i class="bi bi-house-door me-2"></i> Turmas
					</a>
						<ul class="dropdown-menu bg-dark" aria-labelledby="turmasDropdown">
							<li><a class="dropdown-item text-white" href="visualizarTurma.jsp">Visualizar Turmas</a></li>
							<li><a class="dropdown-item text-white" href="crudTurmaLocalizarProfessor.jsp">Cadastrar Turmas</a></li>
							<!-- Você pode adicionar mais opções aqui futuramente, como Editar, Cadastrar, etc -->
						</ul></li>


					<li class="nav-item mt-5"><a href="SvLogout" class="nav-link text-danger"> <i class="bi bi-door-closed me-2"></i> Sair
					</a></li>
				</ul>
			</div>

			<!-- Rodapé da barra lateral -->
			<div class="mt-auto text-center text-secondary small pt-4">&copy; 2025 @Sistema Escolar</div>
		</nav>

		<!-- Área principal -->
		<main class="flex-grow-1 p-5">
		<h1 class="mb-4 text-center" >Sistema Escolar</h1>
			<h2 class="mb-4">Painel Administrativo</h2>
			<div class="card bg-midnight border-0 p-4 shadow">
				<h5>Bem-vindo administrador!</h5>
				<p>Utilize o menu à esquerda para gerenciar o sistema.</p>
				<ul>
					<li>Cadastrar professores e alunos</li>
					<li>Montar e gerenciar turmas</li>
					<li>Visualizar as turmas existentes</li>
				</ul>
			</div>
		</main>

	</div>
</body>
</html>
