import java.util.ArrayList;

public class Graphe {
	protected ArrayList<Sommet> listeSommet;
	protected int lastId;
	protected String name;

	public int getNbSommet() {
		return this.listeSommet.size();
	}

	public Graphe(String name){
		listeSommet = new ArrayList<Sommet>();
		this.lastId = 0;
		this.name = name;
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

	public boolean addUser(int id, int age, String name){
		boolean exist = false;
		for(Sommet s : this.listeSommet){
			if(s.getNumero() == id){
				exist = true;
			}
		}
		if(!exist){
			listeSommet.add(new User(id, age, name));
			if(id>this.lastId)
				this.lastId = id;
			return true;
		}
		return false;
	}
	
	public void addPage(String name, User admin){
		if(!listeSommet.contains(new Page(0,name, admin))){
			listeSommet.add(new Page(this.lastId, name, admin));
			lastId++;
		}
		else
			System.out.println("La page '"+name+"' existe déja");
	}
	
	public void addPage(int id, String name, User admin){
		boolean exist = false;
		for(Sommet s : this.listeSommet){
			if(s.getNumero() == id){
				exist = true;
			}
		}
		if(!exist){
			listeSommet.add(new Page(id, name, admin));
			if(id>this.lastId)
				this.lastId = id;
		}
	}
	
	public String getName(){
		return this.name;
	}
	
	
}