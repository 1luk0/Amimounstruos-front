package com.app.amimounstruos.Models;

import com.google.gson.annotations.SerializedName;

public class Progreso {

  private Integer id;
  private Integer usuarioId;
  private Integer cursoId;
  private String estado;

  // Mapeo de la relación anidada que viene del backend: "nivel": { ... }
  @SerializedName("nivel")
  private Nivel nivel;

  // Usamos el campo nivelId porque el PUT lo necesita
  @SerializedName("nivelId")
  private Integer nivelId;

  public Progreso(Integer usuarioId, Integer cursoId, Integer numero, String estado) {
    this.usuarioId = usuarioId;
    this.cursoId = cursoId;
    // IMPORTANTE: Para el PUT, necesitamos enviar 'numero' en el cuerpo.
    // Usaremos un campo para serializar el 'numero' que se envía al backend.
    this.numero = numero;
    this.estado = estado;
  }

  // Campo temporal para el POST/PUT que envía el número de nivel
  @SerializedName("numero")
  private Integer numero;


  // --- GETTERS CLAVE PARA LA LECTURA ---

  /**
   * Obtiene el número de nivel (1, 2, 3...) de la relación anidada.
   * Si la relación no existe, retorna el número que se envió en la petición PUT/POST.
   */
  public Integer getNumero() {
    // Si la relación anidada 'nivel' se cargó (viene del GET de la API)
    if (nivel != null && nivel.getNumero() != null) {
      return (Integer) nivel.getNumero();
    }
    // Si no es un GET (ej. es un POST/PUT), usamos el campo 'numero' para serializar.
    return numero != null ? numero : 1;
  }

  // --- OTROS GETTERS ---

  public Integer getId() {
    return id;
  }

  public Integer getUsuarioId() {
    return usuarioId;
  }

  public Integer getCursoId() {
    return cursoId;
  }

  public String getEstado() {
    return estado;
  }

  public Integer getNivelId() {
    return nivelId;
  }
}
