import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class Reseau {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);

		Graphe g = choixGraphe();
		choixAction(g);

	}

	public static Graphe choixGraphe() {
		Scanner sc = new Scanner(System.in);
		int choix = 0;
		Graphe g = null;
		String name;

		do {
			do {
				System.out.println("Choix d'une action : ");
				System.out.println("1/Creer un nouveau graphe");
				System.out.println("2/Charger un graphe");
				try {
					choix = Integer.parseInt(sc.next());
				} catch (Exception e) {
					choix = -1;
				}

			} while (choix < 1 || choix > 2);

			switch (choix) {
			case 1:
				System.out.println("Nom du nouveau graphe : ");
				name = sc.next();
				g = new Graphe(name);
				break;

			case 2:
				System.out.println("Nom du graphe a charger : ");
				name = sc.next();
				g = Graphe.loadGraphe(name);
				break;
			}

		} while (g == null);

		return g;
	}

	public static void choixAction(Graphe g) {
		int choix = -1, age = 0, idOrig = 0, idCible =0, idASup = 0, idAdmin = 0;
		String name;
		Scanner sc = new Scanner(System.in);

		do {
			System.out.println("Choix d'une action : ");
			System.out.println("1/Ajouter Utilisateur");
			System.out.println("2/Ajouter Page");
			System.out.println("3/Afficher le nombre de Sommets");
			System.out.println("4/Afficher le nombre d'arcs");
			System.out.println("5/Afficher l'ensemble des Sommets");
			System.out.println("6/Afficher l'ensemble des Sommets tries par nom");
			System.out.println("7/Afficher l'ensemble des Sommets tries par degre sortant");
			System.out.println("8/Afficher l'ensemble des Arcs");		
			System.out.println("9/Supprimer un Sommet");
			System.out.println("10/Supprimer un arc");
			System.out.println("11/Afficher le nombre d'Utilisateurs");
			System.out.println("12/Afficher le nombre de Pages");
			System.out.println("13/Afficher l'ensemble des Utilisateurs etant administrateur de page");
			System.out.println("14/Afficher les informations d'un Sommet");
			System.out.println("15/Ajouter relation");
			System.out.println("16/Afficher les Sommets les plus/moins influents");
			System.out.println("17/Afficher la moyenne d'age");
			System.out.println("18/Enregistrer le graphe");
			System.out.println("19/Quitter");
			try {
				choix = Integer.parseInt(sc.next());
			} catch (Exception e) {
				choix = -1;
			}
			
			sc.nextLine();
			
			switch (choix) {
			case 1:
				System.out.println("Nom du nouvel utilisateur : ");
				name = sc.nextLine();
				
				do {
					System.out.println("Age du nouvel utilisateur : ");
					try {
						age = Integer.parseInt(sc.next());
					} catch (Exception e) {
						age = -1;
					}
				} while (age < 0);
				User u = g.addUser(age, name);
				//choixActionUser(u);
				break;
				
			case 2:
				System.out.println("Nom de la nouvelle page : ");
				String namePage = sc.nextLine();
				idAdmin = -1;
				
				do {
					System.out.println("Id de l'Utilisateur administrant cette page : ");
					try {
						idAdmin = Integer.parseInt(sc.next());
					} catch (Exception e) {
						idAdmin = -1;
					}finally{
						if(g.getSommet(idAdmin) == null || !(g.getSommet(idAdmin) instanceof User)){
							System.out.println("Cet Utilisateur n'existe pas");
							idAdmin = -1;
						}
					}
				} while (idAdmin < 0);
				Page p = g.addPage(namePage, (User) g.getSommet(idAdmin));
				break;
				
			case 3:
				System.out.println("Nombre de Sommet total : " + g.getNbSommet());
				break;
			case 4:
				System.out.println("Nombre d'Arcs total : "+ g.getNbArc());
				break;
			case 5:
				System.out.println("Ensemble des sommets : ");
				for(Sommet s : g.getListeSommet()){
					System.out.println(s.getName() + " -> id : " + s.getNumero() + ", nom : " + s.getName() + ", de type : " + s.getClass());
				}
				break;
			case 6:
				System.out.println("Ensemble des sommets tries par nom : ");
				for(Sommet s : g.getSommetByName()){
					System.out.println(s.getName() + " -> id : " + s.getNumero() + ", nom : " + s.getName() + ", de type : " + s.getClass());
				}
				break;
				
			case 7:
				System.out.println("Ensemble des sommets pas degre sortant : ");
				for(Sommet s : g.getSommetByDegree()){
					System.out.println(s.getName() + " -> id : " + s.getNumero() + ", nom : " + s.getName() + ", de type : " + s.getClass() + "Nombre de relation : " + s.getV_sortant().size());
				}
				break;
				
			case 8:
				for (Map.Entry<Sommet, LinkedList<Sommet>> entry : g.getArc().entrySet()) {
					System.out.print(entry.getKey().getName());
					LinkedList<Sommet> sommets = entry.getValue();
					for (Sommet s : sommets) {
						System.out.print(" -> " + s.getName());
					}
					System.out.println();
				}
				break;
				
			case 9:
				idASup = -1;
				System.out.println("Id du sommet à supprimer : ");
				do {
					try {
						idASup = Integer.parseInt(sc.next());
					} catch (Exception e) {
						idASup = -1;
					}
				} while (idASup < 0);
				g.deleteSommet(idASup);
				break;
				
			case 10:
				idOrig = -1;
				System.out.println("Id du sommet à l'origine du lien : ");
				do {
					try {
						idOrig = Integer.parseInt(sc.next());
					} catch (Exception e) {
						idOrig = -1;
					}
				} while (idOrig < 0);
				
				idCible = -1;
				System.out.println("Id du sommet à supprimer des voisins sortants : ");
				do {
					try {
						idCible = Integer.parseInt(sc.next());
					} catch (Exception e) {
						idCible = -1;
					}
				} while (idCible < 0);
				
				if(g.getSommet(idOrig) instanceof User)
					g.deleteArc((User)g.getSommet(idOrig), g.getSommet(idCible));
				else
					System.out.println("Merci de choisir l'ID d'un utilisateur lors du premier choix");
				break;
				
			case 11:
				System.out.println("Nombre d'utilisateurs : " + g.getNbUser());
				break;
				
			case 12:
				System.out.println("Nombre d'utilisateurs : " + g.getNbPage());
				break;
			
			case 13:
				System.out.println("Ensemble des Utilisateurs etant administrateurs de Page : ");
				for(Sommet s : g.getAdminUsers()){
					System.out.println(s.getName() + " -> id : " + s.getNumero() + ", nom : " + s.getName() + ", de type : " + s.getClass());
				}
				break;
				
			case 14:
				System.out.println("Nom du Sommet : ");
				String nameSommet = sc.nextLine();
				if(g.getSommet(nameSommet) != null){
					Sommet tempS = g.getSommet(nameSommet);
					System.out.println("Nom du sommet : " + tempS.getName());
					System.out.println("Id : " + tempS.getNumero());
					if(tempS instanceof User){
						System.out.println("Age : " + ((User)tempS).getAge());
						System.out.println("Amis et page aimees de ce Sommet : ");
						for(Sommet s : ((User)tempS).getV_sortant()){
							System.out.print(s.getName() + ", ");
						}
						System.out.println();
						
						System.out.println("---Distances entre " + tempS.getName()+ " et tous les autres sommets---");
						for (Entry<Sommet, Integer> entry : g.getDistanceBetweenSommets(tempS).entrySet()) {
							System.out.println(entry.getKey().getName() + " -> "+ entry.getValue() + " liens");
						}
					}
					else if(tempS instanceof Page){
						System.out.println("Administrateur de ce Sommet : " + ((Page)tempS).getAdmin().getName() + " (Id : " + ((Page)tempS).getAdmin().getNumero() + ")");
					}
				}
				else{
					System.out.println("Ce sommet n'existe pas");
				}
				break;
				
			case 15:
				idOrig = -1;
				
				do {
					System.out.println("Id du sommet à l'origine du lien : ");
					try {
						idOrig = Integer.parseInt(sc.next());
					} catch (Exception e) {
						idOrig = -1;
					}
				} while (idOrig < 0);
				
				idCible = -1;
				
				do {
					System.out.println("Id du sommet Cible : ");
					try {
						idCible = Integer.parseInt(sc.next());
					} catch (Exception e) {
						idCible = -1;
					}
				} while (idCible < 0 || idCible == idOrig);
				
				if(g.getSommet(idOrig) instanceof User)
					g.addRelation((User) g.getSommet(idOrig), g.getSommet(idCible));
				else
					System.out.println("Merci de choisir l'ID d'un utilisateur lors du premier choix");
				break;
				
			case 16:
				System.out.println(g.PlusInfluent_User());
				System.out.println(g.MoinsInfluent_User());
				System.out.println(g.PlusInfluent_Page());
				System.out.println(g.MoinsInfluent_Page());
				System.out.println(g.PlusInfluent());
				System.out.println(g.MoinsInfluent());
				System.out.println("---Debug---");
				for(Sommet s : g.getListeSommet()){
					System.out.println(s.getName() + " => " + s.getPagerank());
				}
				break;
				
			case 17:
				System.out.println("La moyenne d'age est de : " + g.getAgeMoyen());
				break;
				
			case 18:
				try {
					g.users_pages();
					g.relationship();
				} catch (IOException e) {
					System.out.println("Une erreure est survenue lors de l'enregistrement des donnees");
				}			
				break;
			}
			System.out.println("----------------------------------");
		} while (choix != 19);
		
		System.out.println("FIN");
		

	}


}
