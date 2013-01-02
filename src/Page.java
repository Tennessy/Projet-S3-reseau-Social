
public class Page extends Sommet {
// Cr�ation d'une page ayant un numero et un nom
	
	public Page(int num,String s) {
		super(num, s);
	}
	public boolean equals(Object obj){
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Page)) {
			return false;
		}
		Page other = (Page) obj;
		if (this.numero != other.getNumero()) {
			return false;
		}
		if(!name.equals(other.getName()))
			return false;
		return true;
	}

}
