import java.io.*;
import java.util.*;


public class Reseau {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Admin ad = new Admin();
		ad.creerGraphe("test2");
		
		
		//Test ajout d'utilisateur + affichage info + recuperation User par son nom
		ad.addUser(20, "hjkl");
		ad.addUser(30, "ghjk");
		User ten = (User) ad.getSommet("hjkl");
		System.out.println("Nom : " + ten.getName() + ", Age : " + ten.getAge() +" Index "+ten.getNumero());
		
		User u = new User(2, 15, "azerty");
		ad.addUser(u);
		
		// Test ajout amis
		ten.ajouter_ami(u);
		ten.ajouter_ami(u);
		System.out.println();
		
		//Test ajout page + like page
		ad.addPage("Page1", ten);
		ad.addPage("facebook", ten);
		Page p = (Page) ad.getSommet("Page1");
		ten.like_page(p);
		
		
		System.out.println("\n---------Triage par nom---------");
		for(Sommet s : ad.getSommetByName()){
			System.out.println(s.getName() + "| id : " + s.getNumero());
		}
		ad.addPage("Page2", ten);
		ad.addPage("algo", u);
		System.out.println();
		affichageArcs(ad);
		
		
		System.out.println("Nombre d'arcs : " + ad.getNbArc());
		
		
			ad.relationship();
			ad.users_pages();
			
		System.out.println("----------Fin Save---------");
		ad.loadGraphe("test2");
		affichageArcs(ad);
		System.out.println("\n---------Triage par nom---------");
		for(Sommet s : ad.getSommetByName()){
			System.out.println(s.getName() + "| id : " + s.getNumero() + " User? : " + (s instanceof User));
		}
		System.out.println("-----");
		for(Sommet s : ad.getSommetByDegree()){
			System.out.println(s.getName() + "| id : " + s.getNumero() + " User? : " + (s instanceof User));
		}
		System.out.println("-----");
		
		affichageArcs(ad);
		ad.deleteArc(ten, u);
		ad.deleteArc(ten, ad.getSommet("Page1"));
		affichageArcs(ad);
		System.out.println("--Liste admins de page---");
		for(User ua : ad.getAdminUsers()){
			System.out.println(ua.getName());
		}
	}
	
	// recuperation des arcs et affichage
	public static void affichageArcs(Admin ad){
		for (Map.Entry<Sommet, LinkedList<Sommet>> entry : ad.getArc().entrySet()){
		     System.out.print(entry.getKey().getName()); 
		     LinkedList<Sommet> sommets = entry.getValue();
			 for(Sommet s : sommets){
				 System.out.print(" -> " + s.getName());
			 }
			 System.out.println();
		}
	}
	
	public static void affichageArcs(User u){
		for( Sommet s : u.getV_sortant()){
			System.out.println(s.getName());
		}
	}

}
