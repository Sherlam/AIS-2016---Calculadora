/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadoraais;

/**
 *
 * @author sergio
 */
public class ExcepcionOperacionNoPermitida extends Exception {
    public ExcepcionOperacionNoPermitida(String msg) {
        System.Out.Println("Conflicto");
        System.Out.Println(msg);
    }
}
