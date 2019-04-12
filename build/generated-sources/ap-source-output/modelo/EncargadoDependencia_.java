package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.EncargDep;
import modelo.Respuesta;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-04-12T17:37:22")
@StaticMetamodel(EncargadoDependencia.class)
public class EncargadoDependencia_ { 

    public static volatile SingularAttribute<EncargadoDependencia, String> apellidos;
    public static volatile SingularAttribute<EncargadoDependencia, Date> fechaIngreso;
    public static volatile SingularAttribute<EncargadoDependencia, String> ipAcceso;
    public static volatile SingularAttribute<EncargadoDependencia, String> documentoLaboral;
    public static volatile SingularAttribute<EncargadoDependencia, String> contrasena;
    public static volatile ListAttribute<EncargadoDependencia, Respuesta> respuestaList;
    public static volatile ListAttribute<EncargadoDependencia, EncargDep> encargDepList;
    public static volatile SingularAttribute<EncargadoDependencia, String> nombres;

}