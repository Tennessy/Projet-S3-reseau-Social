import java.util.*;

public class User extends Sommet {

	@Override
	public String toString() {
		return numero + "|" + name + "|" + age;
	}

	private int age;

	// Cr�ation d'un user ayant un numero un nom & un age
	// le nom et l'age sont stock�s dans une treemap
	public User(int num, int age, String nom) {
		super(num, nom);
		this.age = age;
		// individu.put(nom, age);
	}


	// un individu n'est plus suivi ,il est supprimé la methode prend en param un user

	public void supprimer_ami(User u) {
		boolean f = false;
		Iterator<Sommet> it = v_sortant.listIterator();
		while (it.hasNext()) {
			if (it.next().getNumero() == u.getNumero()) {
				it.remove();
				System.out.println("Individu " + u.getNumero() + " supprimé");
				f = true;
			}
		}
		if (f != true)
			System.out.println("Individu " + u.getNumero()
					+ " inexistant de votre liste d'amis");
	}

	// un individu n'est plus suivi ,il est supprimé la methode prend en param un String

	public void supprimer_ami(String s) {
		boolean f = false;
		Iterator<Sommet> it = v_sortant.listIterator();
		while (it.hasNext()) {
			if (it.next().getName().equals(s)) {
				it.remove();
				System.out.println("Individu " + s + " supprimé");
				f = true;
			}
		}
		if (f != true)
			System.out.println("L'individu " + s
					+ " est inexistant de votre liste d'amis");
	}
	
	// Une page que l'individu n'aime plus la methode prend en param une page

	public void unlike_page(Page p) {
		boolean f = false;
		Iterator<Sommet> it = v_sortant.listIterator();
		while (it.hasNext()) {
			if (it.next().getNumero() == p.getNumero()) {
				it.remove();
				System.out.println("Page " + p.getNumero() + " supprimé");
				f = true;
			}
		}
		if (f != true)

			System.out.println("La page " + p.getNumero()
					+ " est inexistante de votre liste de pages");
	}

	public void unlike_page(String s) {
		boolean f = false;
		Iterator<Sommet> it = v_sortant.listIterator();
		while (it.hasNext()) {
			if (it.next().getName().equals(s)) {
				it.remove();
				System.out.println("Page " + s + " supprimé");
				f = true;
			}
		}
		if (f != true)
			System.out.println("La page " + s
					+ " est inexistante de votre liste de pages");

	}

	// un individu suit un autre
	public void ajouter_ami(User u) {
		if (v_sortant.contains(u))
			System.out.println("L'utilisateur  "+u.getName() +" fait d�ja parti des voisins sortants");
		else {
			v_sortant.add(u);
			System.out.println("Utilisateur ajout�");
		}
	}

	// un individu aime une page
	public void like_page(Page p) {
		if (v_sortant.contains(p))
			System.out.println("La page  " + p.getName() + " fait deja parti des voisins sortants");

		else {
			v_sortant.add(p);
			System.out.println("Page ajoutee");
		}
	}

	public int getAge() {
		return this.age;
	}
	
	public boolean equals(Object obj){
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		if (numero != other.getNumero()) {
			return false;
		}
		return true;
	}

}
