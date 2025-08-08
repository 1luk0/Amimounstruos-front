package com.app.amimounstruos.Models;

public class Nivel {
  private Integer id;
  private Integer numero;
  private Integer cursoId;

  public Nivel(Integer id, Integer numero, Integer cursoId) {
    this.id = id;
    this.cursoId = cursoId;
    this.numero = numero;
  }

  public Number getId() {
    return id;
  }

  public Number getCursoId() {
    return cursoId;
  }

  public Number getNumero() {
    return numero;
  }

  @Override
  public String toString() {
    return "Nivel{" +
      "id=" + id +
      ", numero=" + numero +
      ", cursoId=" + cursoId +
      '}';
  }
}



