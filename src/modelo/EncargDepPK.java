/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Victor
 */
@Embeddable
public class EncargDepPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "codigo_dep")
    private String codigoDep;
    @Basic(optional = false)
    @Column(name = "dl_encargado")
    private String dlEncargado;

    public EncargDepPK() {
    }

    public EncargDepPK(String codigoDep, String dlEncargado) {
        this.codigoDep = codigoDep;
        this.dlEncargado = dlEncargado;
    }

    public String getCodigoDep() {
        return codigoDep;
    }

    public void setCodigoDep(String codigoDep) {
        this.codigoDep = codigoDep;
    }

    public String getDlEncargado() {
        return dlEncargado;
    }

    public void setDlEncargado(String dlEncargado) {
        this.dlEncargado = dlEncargado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoDep != null ? codigoDep.hashCode() : 0);
        hash += (dlEncargado != null ? dlEncargado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EncargDepPK)) {
            return false;
        }
        EncargDepPK other = (EncargDepPK) object;
        if ((this.codigoDep == null && other.codigoDep != null) || (this.codigoDep != null && !this.codigoDep.equals(other.codigoDep))) {
            return false;
        }
        if ((this.dlEncargado == null && other.dlEncargado != null) || (this.dlEncargado != null && !this.dlEncargado.equals(other.dlEncargado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.EncargDepPK[ codigoDep=" + codigoDep + ", dlEncargado=" + dlEncargado + " ]";
    }
    
}
