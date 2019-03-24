/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Victor
 */
@Entity
@Table(name = "encarg_dep")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EncargDep.findAll", query = "SELECT e FROM EncargDep e")
    , @NamedQuery(name = "EncargDep.findByCodigoDep", query = "SELECT e FROM EncargDep e WHERE e.encargDepPK.codigoDep = :codigoDep")
    , @NamedQuery(name = "EncargDep.findByDlEncargado", query = "SELECT e FROM EncargDep e WHERE e.encargDepPK.dlEncargado = :dlEncargado")
    , @NamedQuery(name = "EncargDep.findByEstado", query = "SELECT e FROM EncargDep e WHERE e.estado = :estado")})
public class EncargDep implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EncargDepPK encargDepPK;
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "dl_encargado", referencedColumnName = "documento_laboral", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private EncargadoDependencia encargadoDependencia;
    @JoinColumn(name = "codigo_dep", referencedColumnName = "codigo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Dependencia dependencia;

    public EncargDep() {
    }

    public EncargDep(EncargDepPK encargDepPK) {
        this.encargDepPK = encargDepPK;
    }

    public EncargDep(EncargDepPK encargDepPK, String estado) {
        this.encargDepPK = encargDepPK;
        this.estado = estado;
    }

    public EncargDep(String codigoDep, String dlEncargado) {
        this.encargDepPK = new EncargDepPK(codigoDep, dlEncargado);
    }

    public EncargDepPK getEncargDepPK() {
        return encargDepPK;
    }

    public void setEncargDepPK(EncargDepPK encargDepPK) {
        this.encargDepPK = encargDepPK;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public EncargadoDependencia getEncargadoDependencia() {
        return encargadoDependencia;
    }

    public void setEncargadoDependencia(EncargadoDependencia encargadoDependencia) {
        this.encargadoDependencia = encargadoDependencia;
    }

    public Dependencia getDependencia() {
        return dependencia;
    }

    public void setDependencia(Dependencia dependencia) {
        this.dependencia = dependencia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (encargDepPK != null ? encargDepPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EncargDep)) {
            return false;
        }
        EncargDep other = (EncargDep) object;
        if ((this.encargDepPK == null && other.encargDepPK != null) || (this.encargDepPK != null && !this.encargDepPK.equals(other.encargDepPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.EncargDep[ encargDepPK=" + encargDepPK + " ]";
    }
    
}
