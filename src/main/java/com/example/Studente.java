package com.example;

import java.util.Date;

public class Studente {
    String nome;
    String cognome;
    Date dataDiNascita;

    public Studente() {
        this.nome = "";
        this.cognome = "";
    }

    public Studente(String nome, String cognome, Date dataDiNascita) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataDiNascita = dataDiNascita;
    }

    public String getCognome() {
        return cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDataDiNascita(Date dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public Date getDataDiNascita() {
        return dataDiNascita;
    }
}