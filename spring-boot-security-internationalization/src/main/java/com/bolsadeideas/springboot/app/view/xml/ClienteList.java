/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bolsadeideas.springboot.app.view.xml;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author pj
 */
@XmlRootElement(name = "clientesList")
public class ClienteList {

    @XmlElement(name = "cliente")
    public List<Cliente> cliente;

    public ClienteList() {
    }

    public ClienteList(List<Cliente> cliente) {
        this.cliente = cliente;
    }

    public List<Cliente> getCliente() {
        return cliente;
    }

}
