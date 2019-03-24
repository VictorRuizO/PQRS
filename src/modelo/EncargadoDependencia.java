/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Victor
 */
@Entity
@Table(name = "encargadodependencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EncargadoDependencia.findAll", query = "SELECT e FROM EncargadoDependencia e")
    , @NamedQuery(name = "EncargadoDependencia.findByDocumentoLaboral", query = "SELECT e FROM EncargadoDependencia e WHERE e.documentoLaboral = :documentoLaboral")
    , @NamedQuery(name = "EncargadoDependencia.findByContrasena", query = "SELECT e FROM EncargadoDependencia e WHERE e.contrasena = :contrasena")
    , @NamedQuery(name = "EncargadoDependencia.findByNombres", query = "SELECT e FROM EncargadoDependencia e WHERE e.nombres = :nombres")
    , @NamedQuery(name = "EncargadoDependencia.findByApellidos", query = "SELECT e FROM EncargadoDependencia e WHERE e.apellidos = :apellidos")
    , @NamedQuery(name = "EncargadoDependencia.findByFechaIngreso", query = "SELECT e FROM EncargadoDependencia e WHERE e.fechaIngreso = :fechaIngreso")
    , @NamedQuery(name = "EncargadoDependencia.findByIpAcceso", query = "SELECT e FROM EncargadoDependencia e WHERE e.ipAcceso = :ipAcceso")})
public class EncargadoDependencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "documento_laboral")
    private String documentoLaboral;
    @Basic(optional = false)
    @Column(name = "contrasena")
    private String contrasena;
    @Column(name = "nombres")
    private String nombres;
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "fecha_ingreso")
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;
    @Column(name = "ip_acceso")
    private String ipAcceso;
    @OneToMany(mappedBy = "encargado")
    private List<Respuesta> respuestaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "encargadoDependencia")
    private List<EncargDep> encargDepList;

    public EncargadoDependencia() {
    }

    public EncargadoDependencia(String documentoLaboral) {
        this.documentoLaboral = documentoLaboral;
    }

    public EncargadoDependencia(String documentoLaboral, String contrasena) {
        this.documentoLaboral = documentoLaboral;
        this.contrasena = contrasena;
    }

    public String getDocumentoLaboral() {
        return documentoLaboral;
    }

    public void setDocumentoLaboral(String documentoLaboral) {
        this.documentoLaboral = documentoLaboral;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getIpAcceso() {
        return ipAcceso;
    }

    public void setIpAcceso(String ipAcceso) {
        this.ipAcceso = ipAcceso;
    }

    @XmlTransient
    public List<Respuesta> getRespuestaList() {
        return respuestaList;
    }

    public void setRespuestaList(List<Respuesta> respuestaList) {
        this.respuestaList = respuestaList;
    }

    @XmlTransient
    public List<EncargDep> getEncargDepList() {
        return encargDepList;
    }

    public void setEncargDepList(List<EncargDep> encargDepList) {
        this.encargDepList = encargDepList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (documentoLaboral != null ? documentoLaboral.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EncargadoDependencia)) {
            return false;
        }
        EncargadoDependencia other = (EncargadoDependencia) object;
        if ((this.documentoLaboral == null && other.documentoLaboral != null) || (this.documentoLaboral != null && !this.documentoLaboral.equals(other.documentoLaboral))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.EncargadoDependencia[ documentoLaboral=" + documentoLaboral + " ]";
    }
    
}
