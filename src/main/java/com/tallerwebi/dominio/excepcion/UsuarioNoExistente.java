package com.tallerwebi.dominio.excepcion;

public class UsuarioNoExistente extends RuntimeException {
    public UsuarioNoExistente(String message) {
        super(message);
    }
}
