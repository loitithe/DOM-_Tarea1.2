/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.teis.model;

/**
 *
 * @author rodri
 */
public class Version {

    public double num;
    public String nombre;
    public int api;

    public Version() {
    }

    public Version(double num, String nombre, int api) {
        this.num = num;
        this.nombre = nombre;
        this.api = api;
    }

    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getApi() {
        return api;
    }

    public void setApi(int api) {
        this.api = api;
    }

    @Override
    public String toString() {
        return "Version{" + "numero= " + num + ", nombre= " + nombre + ", api= " + api + "}";
    }

}
