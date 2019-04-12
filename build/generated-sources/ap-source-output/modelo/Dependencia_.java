package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.EncargDep;
import modelo.Pqrs;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-04-12T17:37:22")
@StaticMetamodel(Dependencia.class)
public class Dependencia_ { 

    public static volatile SingularAttribute<Dependencia, String> codigo;
    public static volatile ListAttribute<Dependencia, Pqrs> pqrsList;
    public static volatile ListAttribute<Dependencia, EncargDep> encargDepList;
    public static volatile SingularAttribute<Dependencia, String> nombre;

}