import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public interface GestionGraphe {
	
	public int getNbSommet();
	public int getNbArc();
	public ArrayList<Sommet> getSommet();
	public ArrayList<Sommet> getSommetByName();
	public ArrayList<Sommet> getSommetByDegre();
	public ArrayList<LinkedList<Sommet>> getArc();
	public Sommet getSommet(String name);
	public int getNbUser();
	public int getNbPage();
	public float getAgeMoyen();
	public HashSet<User> getAdminUsers();
	
	public void addUser(int age, String name);
	public void addPage(String name);
	public void deleteSommet(int id);
	
	public void writeGraphe(Graphe g, String fileName);
	public Graphe loadGraphe(String fileName);
}
