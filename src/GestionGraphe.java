import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;

public interface GestionGraphe {
	
	public int getNbSommet();
	public int getNbArc();
	public ArrayList<Sommet> getSommet();
	public ArrayList<Sommet> getSommetByName();
	public ArrayList<Sommet> getSommetByDegree();
	public Hashtable<Sommet, LinkedList<Sommet>> getArc();
	public Sommet getSommet(String name);
	public int getNbUser();
	public int getNbPage();
	public float getAgeMoyen();
	public HashSet<User> getAdminUsers();
	public Sommet getSommet(int id);
	
	public void addUser(int age, String name);
	public void addUser(User u);
	public void addPage(String name, User admin);
	public void deleteSommet(int id);
	public void deleteArc(User orig, Sommet cible);
	
	public void relationship() throws IOException;
	public void users_pages() throws IOException;
	public void loadGraphe(String fileName);
}
