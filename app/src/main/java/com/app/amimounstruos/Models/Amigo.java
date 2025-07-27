package com.app.amimounstruos.Models;

public class Amigo {
  private Number id;
  private Number usuarioId;
  private Number amigoId;

  public Amigo(Number id, Number usuarioId, Number amigoId) {
    this.id = id;
    this.usuarioId = usuarioId;
    this.amigoId = amigoId;
  }

  public Number getId() {
    return id;
  }

  public Number getAmigoId() {
    return amigoId;
  }

  public Number getUsuarioId() {
    return usuarioId;
  }
}
