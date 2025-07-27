package com.app.amimounstruos.Models;

public class Nivel {
  private Number id;
  private Number Numero;
  private Number CursoId;

  public Nivel(Number id, Number Numero, Number CursoId) {
    this.id = id;
    this.CursoId = CursoId;
    this.Numero = Numero;
  }

  public Number getId() {
    return id;
  }

  public Number getCursoId() {
    return CursoId;
  }

  public Number getNumero() {
    return Numero;
  }
}
