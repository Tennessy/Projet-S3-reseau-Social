import java.util.Comparator;

//Class implémentant l'interface Comparator, permettant ainsi d'utiliser la méthode sort de Collection pour trier les Sommets par leur degré sortant
public class IdComparator implements Comparator<Sommet>{

	@Override
	public int compare(Sommet o1, Sommet o2) {
		return o1.getV_sortant().size() - o2.getV_sortant().size();
	}


}
