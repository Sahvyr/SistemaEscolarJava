package model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.OneToOne;
import org.hibernate.query.Query;

import org.hibernate.Session;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

@Entity(name = "professor")
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
@DynamicInsert(value = true)
public class Professor extends Pessoa {
	// ATRIBUTOS
	@Column(length = 100)
	private String especialidade;

	@Column()
	private double salario;

	@OneToOne(mappedBy = "professor", cascade = CascadeType.ALL)
	private Turma turma;
	// CONSTRUTORES

	public Professor() {
		super();
	}

	public Professor(String nome, String cpf, String rg, String telefone, String senha, String especialidade, double salario, Turma turma) {
		super(nome, cpf, rg, telefone, senha);
		this.especialidade = especialidade;
		this.salario = salario;
		this.turma = turma;
	}

	public Professor(long id, String nome, String cpf, String rg, String telefone, String senha, String especialidade, double salario, Turma turma) {
		super(id, nome, cpf, rg, telefone, senha);
		this.especialidade = especialidade;
		this.salario = salario;
		this.turma = turma;
	}

	public Professor(String nome, String cpf, String rg, String telefone, String senha, String especialidade, double salario) {
		super(nome, cpf, rg, telefone, senha);
		this.especialidade = especialidade;
		this.salario = salario;
	}

	public Professor(long id, String nome, String cpf, String rg, String telefone, String senha, String especialidade, double salario) {
		super(id, nome, cpf, rg, telefone, senha);
		this.especialidade = especialidade;
		this.salario = salario;
	}
	// METODOS

	private boolean validarCampos() {
		if (getNome() == null || getNome().isEmpty()) {
			this.setMsg("NOME INVÁLIDO: CAMPO VAZIO.");
			return false;
		}
		if (!verificarCaracteresInvalidosNome(getNome())) {
			this.setMsg("NOME INVÁLIDO: CONTÉM CARACTERES INVÁLIDOS.");
			return false;
		}
		if (getCpf() == null || getCpf().isEmpty()) {
			this.setMsg("CPF INVÁLIDO: CAMPO VAZIO.");
			return false;
		}
		if (getCpf().length() != 11) {
			this.setMsg("CPF INVÁLIDO: DEVE TER 11 CARACTERES.");
			return false;
		}
		if (!verificarCaracteresInvalidosCpfRg(getCpf())) {
			this.setMsg("CPF INVÁLIDO: FORMATO INCORRETO.");
			return false;
		}
		if (getRg() == null || getRg().isEmpty()) {
			this.setMsg("RG INVÁLIDO: CAMPO VAZIO.");
			return false;
		}
		if (!verificarCaracteresInvalidosCpfRg(getRg())) {
			this.setMsg("RG INVÁLIDO: FORMATO INCORRETO.");
			return false;
		}
		if (getTelefone() == null || getTelefone().isEmpty()) {
			this.setTelefone("----");
		}
		if (getSenha() == null || getSenha().isEmpty()) {
			this.setMsg("SENHA INVÁLIDA: CAMPO VAZIO.");
			return false;
		}
		if (getEspecialidade() == null || getEspecialidade().isEmpty()) {
			this.setMsg("ESPECIALIDADE INVÁLIDA: CAMPO VAZIO.");
			return false;
		}
		if (!verificarCaracteresInvalidosNome(getEspecialidade())) {
			this.setMsg("ESPECIALIDADE INVÁLIDA: CARACTERES INVÁLIDOS.");
			return false;
		}
		if (this.salario < 0) {
			this.setMsg("SALÁRIO INVÁLIDO: NÃO PODE SER NEGATIVO.");
			return false;
		}

		return true;
	}

	public boolean salvar() {
		try {
			if (!validarCampos())
				return false;

			setNome(tratarNome(getNome()));
			setEspecialidade(tratarNome(getEspecialidade()));

			Session session = HibernateUtil.abrirSession();
			if (session == null) {
				this.setMsg("ERRO AO ABRIR SESSÃO:" + HibernateUtil.erro);
				return false;
			}

			try {
				session.beginTransaction();
				session.save(this);
				session.getTransaction().commit();
				HibernateUtil.fecharSession();
				this.setMsg("CADASTRO REALIZADO COM SUCESSO!");
				return true;
			} catch (Exception e) {
				HibernateUtil.fecharSession();
				this.setMsg("ERRO AO TENTAR SALVAR:" + e.getMessage());
				return false;
			}

		} catch (Exception e) {
			this.setMsg("ERRO INESPERADO:" + e.getMessage());
		}

		return false;
	}

	// public boolean localizarProfessor(Long idProfessor) {

	// try {

	// Session session = HibernateUtil.abrirSession();
	// if (session == null) {
	// this.setMsg("ERRO AO ABRIR SESSÃO:" + HibernateUtil.erro);
	// return false;
	// }

	// try {
	// session.beginTransaction();
	// Professor x = session.find(Professor.class, idProfessor);
	// HibernateUtil.fecharSession();

	// if (x != null) {
	// this.setMsg("PROFESSOR LOCALIZADO COM SUCESSO!");
	// this.setId(x.getId());
	// this.setNome(x.getNome());
	// this.setCpf(x.getCpf());
	// this.setRg(x.getRg());
	// this.setTelefone(x.getTelefone());
	// this.setEspecialidade(x.getEspecialidade());
	// this.setSenha(x.getSenha());
	// this.setSalario(x.getSalario());

	// return true;
	// } else {
	// this.setMsg("NÃO HÁ PROFESSOR COM ESTE ID NO SISTEMA");
	// return false;
	// }
	// } catch (Exception e) {
	// HibernateUtil.fecharSession();
	// this.setMsg("ERRO AO TENTAR LOCALIZAR:" + e.getMessage());
	// return false;
	// }

	// } catch (Exception e) {
	// this.setMsg("ERRO INESPERADO:" + e.getMessage());
	// return false;
	// }

	// }

	public boolean localizarProfessor(String cpfLocalizado) {

		try {
			if (cpfLocalizado == null || cpfLocalizado.isEmpty()) {
				this.setMsg("NÃO FOI POSSIVEL LOCALIZAR: SEU CPF ESTÁ NULO OU VAZIO");
				return false;
			} else if (cpfLocalizado.length() != 11) {
				this.setMsg("NÃO FOI POSSIVEL LOCALIZAR:SEU CPF NÃO CONTÉM 11 CARACTERES");
				return false;
			} else if (!verificarCaracteresInvalidosCpfRg(cpfLocalizado)) {
				this.setMsg("NÃO FOI POSSIVEL LOCALIZAR: SEU CPF CONTÉM CARACTERES INVÁLIDOS. (FORMATO CORRETO: 00000000000)");
				return false;
			} else {

				Session session = HibernateUtil.abrirSession();
				if (session == null) {
					this.setMsg("ERRO AO ABRIR SESSÃO: " + HibernateUtil.erro);
					return false;
				}

				String sql = "SELECT * FROM professor WHERE cpf = :cpf";
				Query<Professor> query = session.createNativeQuery(sql, Professor.class);
				query.setParameter("cpf", cpfLocalizado);

				query.setMaxResults(1);
				Professor prof = (Professor) query.uniqueResult();

				if (prof != null) {
					this.setId(prof.getId());
					this.setNome(prof.getNome());
					this.setCpf(prof.getCpf());
					this.setRg(prof.getRg());
					this.setTelefone(prof.getTelefone());
					this.setEspecialidade(prof.getEspecialidade());
					this.setSenha(prof.getSenha());
					this.setSalario(prof.getSalario());

					this.setMsg("PROFESSOR LOCALIZADO COM SUCESSO!");
					return true;
				} else {
					this.setMsg("NENHUM PROFESSOR LOCALIZADO COM ESTE CPF.");
					return false;
				}
			}

		} catch (Exception e) {

			this.setMsg("ERRO AO LOCALIZAR PROFESSOR: " + e.getMessage());
			return false;
		}

	}

	public boolean excluirProfessor() {
		Session session = HibernateUtil.abrirSession();
		if (session == null) {
			this.setMsg("ERRO AO ABRIR SESSÃO: " + HibernateUtil.erro);
			return false;
		}

		try {
			// Verifica se o professor está vinculado a alguma turma
			String hql = "SELECT count(t) FROM turma t WHERE t.professor.id = :profId";
			Long turmasVinculadas = (Long) session.createQuery(hql).setParameter("profId", this.getId()).uniqueResult();

			if (turmasVinculadas != null && turmasVinculadas > 0) {
				this.setMsg("PROFESSOR NÃO PODE SER EXCLUÍDO: VINCULADO A UMA TURMA.");
				HibernateUtil.fecharSession();
				return false;
			}

			// Nenhuma turma vinculada, então pode excluir
			session.beginTransaction();
			session.delete(this);
			session.getTransaction().commit();
			this.setMsg("PROFESSOR EXCLUÍDO COM SUCESSO!");
			return true;

		} catch (Exception e) {
			this.setMsg("ERRO AO TENTAR EXCLUIR PROFESSOR: " + e.getMessage());
			return false;
		} finally {
			HibernateUtil.fecharSession();
		}
	}

	public boolean editarProfessor() {
		try {
			if (!validarCampos())
				return false;

			setNome(tratarNome(getNome()));
			setEspecialidade(tratarNome(getEspecialidade()));

			Session session = HibernateUtil.abrirSession();
			if (session == null) {
				this.setMsg("ERRO AO ABRIR SESSÃO: " + HibernateUtil.erro);
				return false;
			}

			try {
				session.beginTransaction();
				session.update(this); // Atualiza o professor com base no ID
				session.getTransaction().commit();
				HibernateUtil.fecharSession();
				this.setMsg("DADOS ATUALIZADOS COM SUCESSO!");
				return true;
			} catch (Exception e) {
				HibernateUtil.fecharSession();
				this.setMsg("ERRO AO ATUALIZAR DADOS: " + e.getMessage());
				return false;
			}

		} catch (Exception e) {
			this.setMsg("ERRO INESPERADO: " + e.getMessage());
			return false;
		}
	}

	// GETSET
	public String getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

}
