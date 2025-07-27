package com.app.amimounstruos.Models;

public class Progreso {

  private Number id;
  private Number usuarioId;
  private Number cursoId;
  private Number nivelId;
  private String estado;

  public Progreso(Number id, Number usuarioId, Number cursoId, Number nivelId, String estado) {
    this.id = id;
    this.usuarioId = usuarioId;
    this.cursoId = cursoId;
    this.nivelId = nivelId;
    this.estado = estado;
  }

  public Number getId() {
    return id;
  }

  public Number getUsuarioId() {
    return usuarioId;
  }

  public Number getCursoId() {
    return cursoId;
  }

  public Number getNivelId() {
    return nivelId;
  }

  public String getEstado() {
    return estado;
  }
}
