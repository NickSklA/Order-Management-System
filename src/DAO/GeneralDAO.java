package DAO;

import java.util.List;

public interface GeneralDAO<G> {
    List<G> getAll();
    int insert(G g);
    boolean update(G g);
    boolean delete(G g);
    boolean confirmDelete();
	String getFXMLPath(String type);
}
