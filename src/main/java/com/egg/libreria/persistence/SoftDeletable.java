package com.egg.libreria.persistence;
public interface SoftDeletable {
    public void setActivo(Boolean status);
    Boolean getActivo();
}
