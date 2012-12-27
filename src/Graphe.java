import java.util.ArrayList;

public class Graphe {
	protected ArrayList<Sommet> listeSommet;
	protected int lastId;
	protected int nbSommet;
	protected int nbUser;
	protected int nbPage;
	
	public int getNbSommet() {
		return nbSommet;
	}

	public Graphe(){
		listeSommet = new ArrayList<Sommet>();
		this.lastId = 0;
		this.nbSommet = 0;
		this.nbUser = 0;
		this.nbPage = 0;
	}
	
	public ArrayList<Sommet> getListeSommet(){
		return this.listeSommet;
	}
	
	public int getNbPage(){
		return this.nbPage;
	}
	
	public int getNbUser(){
		return this.nbUser;
	}
	
	public void addUser(int age, String name){
		listeSommet.add(new User(this.lastId, age, name));
		lastId++;
		this.nbSommet++;
	}
	
	public void addPage(String name){
		listeSommet.add(new Page(this.lastId, name));
		lastId++;
		this.nbSommet++;
	}
	
	public void deleteSommet(int id){
		for(Sommet s : listeSommet){
			if(s.getNumero() == id){
				listeSommet.remove(s);
			}
			else if(s.getV_sortant().contains(id)){
				s.getV_sortant().remove(id);
			}
		}
	}
	
}
