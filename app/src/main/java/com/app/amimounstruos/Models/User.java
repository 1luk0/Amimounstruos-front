package com.app.amimounstruos.Models;

public class User {
  private int id;
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

  public int getId() {
    return id;
  }


}
