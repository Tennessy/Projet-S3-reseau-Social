import java.util.ArrayList;

public class Graphe {
	protected ArrayList<Sommet> listeSommet;
	protected int lastId;
	
	public int getNbSommet() {
		return this.listeSommet.size();
	}

	public Graphe(){
		listeSommet = new ArrayList<Sommet>();
		this.lastId = 0;
	}
	
	public ArrayList<Sommet> getListeSommet(){
		return this.listeSommet;
	}
	
	public int getNbPage(){
		int nbPage = 0;
		
		for(Sommet s : this.listeSommet){
			if(s instanceof Page){
				nbPage++;
			}
		}
		
		return nbPage;
	}
	
	public int getNbUser(){
		int nbUser = 0;
		
		for(Sommet s : this.listeSommet){
			if(s instanceof User){
				nbUser++;
			}
		}
		
		return nbUser;
	}
	
	public void addUser(int age, String name){
		listeSommet.add(new User(this.lastId, age, name));
		lastId++;
	}
	
	public void addPage(String name){
		listeSommet.add(new Page(this.lastId, name));
		lastId++;
	}
	
}
