package persistencia;

import modelos.Agrupacion;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class AgrupacionDAO extends AbstractDAO {

    /**
     * Persiste el objeto en la base de datos sin confirmar transaccion
     * @param agrupacion
     */
    public void persistSinTransaccion(Agrupacion agrupacion) {
        try {
            session.persist(agrupacion);
        } catch (Exception e) {
            rollback();
        }
    }

    /**
     * Devuelve todos las agrupaciones persistidas en la base de datos
     * @return
     */
    public List<Agrupacion> buscarTodos() {
        return null;
    }

}
