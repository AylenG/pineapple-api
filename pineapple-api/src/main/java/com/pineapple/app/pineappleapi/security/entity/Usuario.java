package com.pineapple.app.pineappleapi.security.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@Column(nullable = false)
	private String nombre;
	
	@NotNull
	@Column(nullable = false)
	private String apellido;
	
	@NotNull
	@Column(nullable = false, unique = true)
	private String nombreUsuario;
	
	@NotNull
	@Column(nullable = false)
	private String email;
	
	@NotNull
	@Column(nullable = false)
	private String password;
	
	@NotNull
	@Column(nullable = false)
	private Date fechaCreacion;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "USUARIO_ROL", joinColumns = @JoinColumn(name = "USUARIO_ID"), 
				inverseJoinColumns = @JoinColumn(name = "ROL_ID"))
	private Set<Rol> roles = new HashSet<>();

	public Usuario() {
		super();
	}

	public Usuario(@NotNull String nombre, @NotNull String apellido, @NotNull String nombreUsuario,
			@NotNull String email, @NotNull String password) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.nombreUsuario = nombreUsuario;
		this.email = email;
		this.password = password;
		this.fechaCreacion = new Date();
	}



	public Usuario(@NotNull String nombre, @NotNull String apellido, @NotNull String nombreUsuario,
			@NotNull String email, @NotNull String password, Set<Rol> roles) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.nombreUsuario = nombreUsuario;
		this.email = email;
		this.password = password;
		this.roles = roles;
		this.fechaCreacion = new Date();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Rol> getRoles() {
		return roles;
	}

	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	
	
}
