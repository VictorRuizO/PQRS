package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.EncargadoDependencia;
import modelo.Pqrs;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-04-12T17:37:22")
@StaticMetamodel(Respuesta.class)
public class Respuesta_ { 

    public static volatile SingularAttribute<Respuesta, String> descripcion;
    public static volatile SingularAttribute<Respuesta, Pqrs> codPqrs;
    public static volatile SingularAttribute<Respuesta, Date> fecha;
    public static volatile SingularAttribute<Respuesta, Integer> codigo;
    public static volatile SingularAttribute<Respuesta, Date> hora;
    public static volatile SingularAttribute<Respuesta, EncargadoDependencia> encargado;

}