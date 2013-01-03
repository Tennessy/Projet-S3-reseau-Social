import java.util.Comparator;


public class IdComparator implements Comparator<Sommet>{

	@Override
	public int compare(Sommet o1, Sommet o2) {
		return o1.getNumero() - o2.getNumero();
	}


}
