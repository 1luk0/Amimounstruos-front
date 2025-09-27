package com.app.amimounstruos.Models;

import com.google.gson.annotations.SerializedName;

public class Nivel {
  private Integer id;
  private Integer cursoId;

  @SerializedName("numero")
  private Integer numero;

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



