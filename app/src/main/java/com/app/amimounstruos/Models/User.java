package com.app.amimounstruos.Models;

public class User {
  private int id;
  private String nombre;
  private int amimounstruo;



  public User(String nombre, int amimounstruo) {
    this.nombre = nombre;
    this.amimounstruo = amimounstruo;
  }

  public String getNombre() {
    return nombre;
  }

  public int getAmimounstruo() {
    return amimounstruo;
  }

  public int getId() {
    return id;
  }


}
