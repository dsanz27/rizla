import java.util.ArrayList;

public class Produccion {
	private String antecedente;
	private ArrayList consecuente;
	private ArrayListMod first;
	
	public ArrayListMod getFirst() {
		return first;
	}

	public void setFirst(ArrayListMod first) {
		this.first = first;
	}

	public Produccion(String antecedente, ArrayList consecuente) {
		super();
		this.antecedente = antecedente;
		this.consecuente = consecuente;
	}
	
	public String getAntecedente() {
		return antecedente;
	}
	public int getNumberOfConsElem(){
		return this.getConsecuente().size();
	}
	public ArrayList getConsecuente() {
		return consecuente;
	}
	
	public String toString(){
		return " "+this.antecedente+"-> "+this.consecuente+" FIRST: "+this.getFirst();
	}
	
	public void printProduccion(){
		System.out.println("Antecedente: "+this.antecedente);
		System.out.println("Consecuente: "+this.consecuente);
		
	}
	
	
}
