package com.example.ProcessaAlunosAprovRepro.Entities;

import javax.persistence.*;

@Entity
public class boletimAlunosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String classe;
    private String materia;
    private int    anoCorrente;
    private int    numeroChamada;
    private double notaBimestre1;
    private double notaBimestre2;
    private double notaBimestre3;
    private double notaBimestre4;
    private double mediaFinal;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public int getAnoCorrente() {
        return anoCorrente;
    }

    public void setAnoCorrente(int anoCorrente) {
        this.anoCorrente = anoCorrente;
    }

    public int getNumeroChamada() {
        return numeroChamada;
    }

    public void setNumeroChamada(int numeroChamada) {
        this.numeroChamada = numeroChamada;
    }

    public double getNotaBimestre1() {
        return notaBimestre1;
    }

    public void setNotaBimestre1(double notaBimestre1) {
        this.notaBimestre1 = notaBimestre1;
    }

    public double getNotaBimestre2() {
        return notaBimestre2;
    }

    public void setNotaBimestre2(double notaBimestre2) {
        this.notaBimestre2 = notaBimestre2;
    }

    public double getNotaBimestre3() {
        return notaBimestre3;
    }

    public void setNotaBimestre3(double notaBimestre3) {
        this.notaBimestre3 = notaBimestre3;
    }

    public double getNotaBimestre4() {
        return notaBimestre4;
    }

    public void setNotaBimestre4(double notaBimestre4) {
        this.notaBimestre4 = notaBimestre4;
    }

    public double getMediaFinal() {
        return mediaFinal;
    }

    public void setMediaFinal(double mediaFinal) {
        this.mediaFinal = mediaFinal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boletimAlunosEntity(Long id, String nome, String classe, String materia, int anoCorrente, int numeroChamada, double notaBimestre1, double notaBimestre2, double notaBimestre3, double notaBimestre4, double mediaFinal) {
        this.id = id;
        this.nome = nome;
        this.classe = classe;
        this.materia = materia;
        this.anoCorrente = anoCorrente;
        this.numeroChamada = numeroChamada;
        this.notaBimestre1 = notaBimestre1;
        this.notaBimestre2 = notaBimestre2;
        this.notaBimestre3 = notaBimestre3;
        this.notaBimestre4 = notaBimestre4;
        this.mediaFinal = mediaFinal;
        this.status = status;
    }

    public boletimAlunosEntity() {
    }
}
