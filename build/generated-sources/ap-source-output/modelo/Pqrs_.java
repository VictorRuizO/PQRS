package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Dependencia;
import modelo.Respuesta;
import modelo.Usuario;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-04-12T17:37:22")
@StaticMetamodel(Pqrs.class)
public class Pqrs_ { 

    public static volatile SingularAttribute<Pqrs, Date> fecha;
    public static volatile SingularAttribute<Pqrs, Integer> codigo;
    public static volatile SingularAttribute<Pqrs, String> tipo;
    public static volatile SingularAttribute<Pqrs, String> titulo;
    public static volatile ListAttribute<Pqrs, Respuesta> respuestaList;
    public static volatile SingularAttribute<Pqrs, String> decripcion;
    public static volatile SingularAttribute<Pqrs, Dependencia> codigoDependencia;
    public static volatile SingularAttribute<Pqrs, Usuario> dniUsuario;

}