package com.app.amimounstruos.Models;

public class Curso {
  private String nombre;
  private Number id;

  public Curso(String nombre, Number id) {
    this.nombre = nombre;
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public Number getId() {
    return id;
  }
}


