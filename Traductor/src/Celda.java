import java.util.Iterator;
import java.util.Set;

public class Celda {
	private String terminal;
	private String noTerminal;
	private Produccion prod;
	
	public Celda(String noTerminal,String terminal, Produccion prod) {
		super();
		this.terminal = terminal;
		this.noTerminal = noTerminal;
		this.prod = prod;
	}

	public String getTerminal() {
		return terminal;
	}

	public String getNoTerminal() {
		return noTerminal;
	}
	
	public boolean equals(Object obj){
		if(obj instanceof Celda)
			return this.terminal == ((Celda)obj).getTerminal() && this.noTerminal == ((Celda)obj).getNoTerminal(); 
		return false;
	}
	
	public String toString(){
		return this.noTerminal+"\t-\t"+this.terminal+"\n     "+this.prod.getAntecedente()+" -> "+this.prod.getConsecuente().toString();
	}
	

}
