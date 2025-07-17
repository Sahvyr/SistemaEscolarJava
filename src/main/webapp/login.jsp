<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<title>Sistema Escolar</title>

<!-- Responsividade para mobile -->
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<!-- Fonte Montserrat do Google Fonts -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100..900;1,100..900&display=swap" rel="stylesheet">

<!-- Seu CSS -->
<link href="css/login.css" rel="stylesheet">
</head>

<%
String msg = (String) request.getAttribute("msg");
String status = (String) request.getAttribute("status");
%>


<body class="bg-login d-flex align-items-center justify-content-center vh-100 position-relative text-white">

	<!-- Alerta de logout -->
	<%
	if ("logout".equals(status)) {
	%>
	<div class="position-absolute top-0 w-100 text-center fw-bold mt-3 " aria-label="Close" style="z-index: 1050;">
		<div class="alert alert-success d-inline-block px-4 py-2" role="alert">Logout realizado com sucesso!</div>
	</div>
	<%
	}
	%>
	<!-- Logo e título -->
	<div class="text-center position-absolute top-0 start-50 translate-middle-x mt-4 logo-title">
		<img src="img/sistema_escolar_transparente.png" alt="Logo do sistema escolar" class="mb-2 img-fluid" style="max-width: 100px;">
		<h1 class="mb-4 text-center" >Sistema Escolar</h1>
	</div>

	<!-- Formulário -->
	<div class="card p-4 bg-midnight border-0 shadow position-relative mx-3" style="max-width: 400px; width: 100%; z-index: 1;">
		<h2 class="text-center text-purple">Login</h2>
		<form action="SvLogin" method="get">
			<div class="mb-3">
				<label for="icpf" class="form-label text-white">CPF</label>
				<input type="text" class="form-control bg-dark text-white border-0" id="icpf" name="cpf" required>
			</div>
			<div class="mb-3">
				<label for="isenha" class="form-label text-white">Senha</label>
				<input type="password" class="form-control bg-dark text-white border-0" id="isenha" name="senha" required>
			</div>

			<%
			if (msg != null) {
			%>
			<div class="alert alert-danger py-2 px-3 mb-3" role="alert">
				<%=msg%>
			</div>
			<%
			}
			%>




			<button type="submit" class="btn btn-purple w-100">Entrar</button>
		</form>
	</div>

</body>
</html>
