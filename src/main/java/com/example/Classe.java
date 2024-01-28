package com.example;

import java.util.ArrayList;

public class Classe {
    ArrayList<Studente> studenti;
    String classe;
    String sezione;
    String aula;

    public Classe(){
        studenti = new ArrayList<>();
        classe = "";
    }

    public Classe(String classe, String sezione, String aula){
        studenti = new ArrayList<>();
        this.classe = classe;
        this.sezione = sezione;
        this.aula = aula;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public void setStudenti(ArrayList<Studente> studenti) {
        this.studenti = studenti;
    }

    public String getClasse() {
        return classe;
    }

    public ArrayList<Studente> getStudenti() {
        return studenti;
    }

    public void setSezione(String sezione) {
        this.sezione = sezione;
    }

    public String getSezione() {
        return sezione;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

}