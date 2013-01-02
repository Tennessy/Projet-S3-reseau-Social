import java.util.*;
import java.io.*;

public class Admin implements GestionGraphe {
	Graphe graphe;

	public Admin() {
		this.graphe = new Graphe();
	}

	@Override
	public int getNbSommet() {
		return graphe.getNbSommet();
	}

	@Override
	public int getNbArc() {
		int nbArc = 0;
		for (Sommet s : graphe.getListeSommet()) {
			nbArc += s.getV_sortant().size();
		}
		return nbArc;
	}

	@Override
	public ArrayList<Sommet> getSommet() {
		return graphe.getListeSommet();
	}

	@Override
	public ArrayList<Sommet> getSommetByName() {
		ArrayList<Sommet> ordered = graphe.getListeSommet();
		Collections.sort(ordered, new NameComparator());
		return ordered;
	}

	@Override
	public ArrayList<Sommet> getSommetByDegre() {
		// TODO Auto-generated method stub
		return null;
	}

	// Renvois les arcs sous forme de LinkedList<Sommet>. Le premier de la liste
	// est l'user � l'origine des liens
	@Override
	public Hashtable<Sommet, LinkedList<Sommet>> getArc() {
		Hashtable<Sommet, LinkedList<Sommet>> list = new Hashtable<Sommet, LinkedList<Sommet>>();

		for (Sommet s : graphe.getListeSommet()) {
			if (s instanceof User) {
				LinkedList<Sommet> temp = s.getV_sortant();
				list.put(s, temp);
			}

		}

		return list;
	}

	@Override
	public Sommet getSommet(String name) {

		for (Sommet s : graphe.getListeSommet()) {
			if (s.getName().equals(name)) {
				return s;
			}
		}
		return null;
	}

	@Override
	public int getNbUser() {
		return graphe.getNbUser();
	}

	@Override
	public int getNbPage() {
		return graphe.getNbPage();
	}

	@Override
	public float getAgeMoyen() {
		int ageM = 0;
		for (Sommet s : graphe.getListeSommet()) {
			if (s instanceof User) {
				ageM += ((User) s).getAge();
			}
		}
		return ageM / graphe.getNbUser();
	}
	
	public Sommet getSommet(int id){
		for(Sommet s : graphe.getListeSommet()){
			if(s.getNumero() == id){
				return s;
			}
		}
		return null;
	}

	@Override
	public HashSet<User> getAdminUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addUser(int age, String name) {
		graphe.addUser(age, name);

	}

	public void addUser(User u) {
		graphe.addUser(u.getAge(), u.getName());
	}

	@Override
	public void addPage(String name) {
		graphe.addPage(name);

	}

	@Override
	public void deleteSommet(int id) {
		System.out.println("\n --- Suppression de " + graphe.getListeSommet().get(id).getName() + "\n");
		Sommet sToDelete = this.getSommet(id);
		
		for (Sommet s : graphe.getListeSommet()) {
			s.getV_sortant().remove(sToDelete);
		}
		
		graphe.getListeSommet().remove(sToDelete);

	}

	@Override
	public void writeGraphe(Graphe g, String fileName) {
		
	}

	@Override
	public Graphe loadGraphe(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteArc(Sommet orig, Sommet cible) {

	}
// Stocke toutes les relations des utilisateurs dans le fichier nommé relation_user_page.txt
	public void relationship() throws IOException {

		BufferedWriter b = new BufferedWriter(new FileWriter(
				"relation_user_page.txt"));
		b.write("Relations entre individus et pages  \n \n");

			for (Map.Entry<Sommet, LinkedList<Sommet>> entry : this.getArc()
					.entrySet()) {
				b.write("==> " + entry.getKey().getName().toUpperCase()
						+ " d'index " + entry.getKey().getNumero() + " \n");
				LinkedList<Sommet> sommets = entry.getValue();
				for (Sommet s : sommets) {
					if(s instanceof User)
					b.write("	* " + s.getName() + " [" + s.getNumero()
							+ "]  (Utilisateur)\n");
					if (s instanceof Page)
						b.write("	* " + s.getName() + " [" + s.getNumero()
								+ "]  (Page)\n");
				}
				b.write("\n");
			}
			b.close();
		}

}
