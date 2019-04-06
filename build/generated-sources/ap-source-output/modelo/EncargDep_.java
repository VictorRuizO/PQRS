package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Dependencia;
import modelo.EncargDepPK;
import modelo.EncargadoDependencia;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-04-06T13:02:52")
@StaticMetamodel(EncargDep.class)
public class EncargDep_ { 

    public static volatile SingularAttribute<EncargDep, EncargadoDependencia> encargadoDependencia;
    public static volatile SingularAttribute<EncargDep, String> estado;
    public static volatile SingularAttribute<EncargDep, EncargDepPK> encargDepPK;
    public static volatile SingularAttribute<EncargDep, Dependencia> dependencia;

}