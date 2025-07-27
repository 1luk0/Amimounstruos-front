package com.app.amimounstruos.Models;

public class User {
  private String nombre;
  private Number amimounstruo;

  public User(String nombre, Number amimounstruo) {
    this.nombre = nombre;
    this.amimounstruo = amimounstruo;
  }

  public String getNombre() {
    return nombre;
  }

  public Number getAmimounstruo() {
    return amimounstruo;
  }

}
