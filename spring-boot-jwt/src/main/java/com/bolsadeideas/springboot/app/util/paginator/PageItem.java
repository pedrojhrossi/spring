/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bolsadeideas.springboot.app.util.paginator;

/**
 *
 * @author pj
 */
public class PageItem {

    private int numeroPaginas;
    private boolean actual;

    public PageItem(int numeroPaginas, boolean actual) {
        this.numeroPaginas = numeroPaginas;
        this.actual = actual;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public boolean isActual() {
        return actual;
    }

}
