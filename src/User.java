import java.util.*;


public class User extends Sommet{
	private TreeMap<String,Integer> individu;
	// Cr�ation d'un user ayant un numero un nom & un age
	//le nom et l'age sont stock�s dans une treemap
	public User(int num,int age,String nom) {
		super(num, nom);
		individu.put(nom, age);
		// TODO Auto-generated constructor stub
	}
	// un individu suit un autre
	public void ajouter_ami(User u){
		if(v_sortant.contains(u))
			System.out.println("Utilisateur faisant d�ja parti des voisins sortants");
		else{
			v_sortant.add(u);
			System.out.println("Utilisateur ajout�");
		}
	}
	// un individu aime une page
	public void like_page(Page p){
		if(v_sortant.contains(p))
			System.out.println("Page faisant deja parti des voisins sortants");
		else{
			v_sortant.add(p);
			System.out.println("Page ajoutee");
		}
	}
	

}
