import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;


public class Admin implements GestionGraphe{
	Graphe graphe;

	public Admin(){
		this.graphe = new Graphe();
	}
	
	@Override
	public int getNbSommet() {
		return graphe.getNbSommet();
	}

	@Override
	public int getNbArc() {
		int nbArc = 0;
		for(Sommet s : graphe.getListeSommet()){
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

	//Renvois les arcs sous forme de LinkedList<Sommet>. Le premier de la liste est l'user à l'origine des liens
	@Override
	public ArrayList<LinkedList<Sommet>> getArc() {
		ArrayList<LinkedList<Sommet>> list = new ArrayList<LinkedList<Sommet>>(); 
		
		for(Sommet s : graphe.getListeSommet()){
			LinkedList<Sommet> temp = (s.getV_sortant());
			temp.addFirst(s);
			list.add(temp);
		}
		
		return list;
	}

	@Override
	public Sommet getSommet(String name) {
		
		for(Sommet s : graphe.getListeSommet()){
			if(s.getName().equals(name)){
				return s;
			}
		}
		return null;
	}

	@Override
	public int getNbUser() {
		return 0;
	}

	@Override
	public int getNbPage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getAgeMoyen() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public HashSet<User> getAdminUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addUser(int age, String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPage(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteSommet(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeGraphe(Graphe g, String fileName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Graphe loadGraphe(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

}
