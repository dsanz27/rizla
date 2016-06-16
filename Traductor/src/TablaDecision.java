import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TablaDecision {
	ArrayList<Celda> tabla;
	private ArrayListMod<String> terminales;
	private ArrayListMod<String> noterminales;

	public TablaDecision() {
		super();
		this.tabla = new ArrayList<Celda>();
		this.terminales = new ArrayListMod<String>();
		this.noterminales = new ArrayListMod<String>();
	}
	
	public void addCelda(Celda celda) throws Exception{
		
		if(this.tabla.contains(celda))
			throw new Exception("La celda ya existe. No se creará la tabla");
		else{
			this.tabla.add(celda);
			this.terminales.add(celda.getTerminal());
			this.noterminales.add(celda.getNoTerminal());
		}
	}
	
	public String toString(){
		String cadena="";
		Set listaCeldas;
		Iterator<Celda> itCeldas;
		itCeldas = this.tabla.iterator();
		while(itCeldas.hasNext())
			cadena=cadena+itCeldas.next().toString()+"\n\n";
		return cadena;
	}
	
	public void print(){
		System.out.println(this.toString());
	}
	
	

}
