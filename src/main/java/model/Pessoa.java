package model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@MappedSuperclass
public class Pessoa {

	// ATRIBUTOS
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false, length = 100)
	private String nome;
	@Column(nullable = false, unique = true, length = 14)
	private String cpf;
	@Column(nullable = false, unique = true, length = 12)
	private String rg;
	@Column(nullable = true, length = 15)
	private String telefone;
	@Column(nullable = true)
	private String senha;
	@Transient
	private String msg;

	// CONSTRUTORES
	public Pessoa(long id, String nome, String cpf, String rg, String telefone, String senha) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.rg = rg;
		this.telefone = telefone;
		this.senha = senha;
	}

	public Pessoa(String nome, String cpf, String rg, String telefone, String senha) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.rg = rg;
		this.telefone = telefone;
		this.senha = senha;
	}

	public Pessoa() {
		super();
	}

	// MÉTODOS

	public Session abrirSessao() {

		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

		Session session = sessionFactory.openSession();

		session.beginTransaction();

		return session;

	}

	public boolean verificarCaracteresInvalidosCpfRg(String nome) {

		nome = nome.toLowerCase().trim();

		String caracteresInvalidos = "abcdefghijklmnopqrstuvwyz,./?°!@#$%¨&*()_-+=`´{}[]  ";

		for (int i = 0; i < nome.length(); i++) {
			char c = nome.charAt(i);

			if (caracteresInvalidos.indexOf(c) != -1) {
				return false;
			}

		}
		return true;
	}

	public boolean verificarCaracteresInvalidosNome(String nome) {

		String caracteresInvalidos = "0123456789,./?°!@#$%¨&*()_-+=`´{}[]";

		nome = nome.trim();

		for (int i = 0; i < nome.length(); i++) {
			char c = nome.charAt(i);

			if (caracteresInvalidos.indexOf(c) != -1) {
				return false;
			}

		}
		return true;
	}

	public String tratarNome(String nome) {
		try {
			if (!verificarCaracteresInvalidosNome(nome)) {
				return msg = "O NOME CONTÉM CARACTERES INVÁLIDOS";
			} else {

				while (nome.contains("  ")) {
					nome = nome.replace("  ", " ");
				}

				return nome = (nome.substring(0, 1).toUpperCase() + nome.substring(1).toLowerCase()).trim();
			}
		} catch (Exception e) {

			return msg = e.getMessage();
		}

	}

	// GETSET
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
