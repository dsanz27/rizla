import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;



public class AnSintLL {
	
	private static String archivoEntrada = "src/tokens.txt";
	private static String tabla = "src/tabla.txt";
	private static String gramatica = "src/sintactico3.txt";
	private static String gram = "src/GramASint.txt";
	private static String linea;
	private static String cima;
	private static String siguiente;
	private static ArrayList<String> pila= new ArrayList<String>();
	private static ArrayList<String> entrada= new ArrayList<String>();
	
	
	
	public AnSintLL(String archivo){
	}
	
	/* Introduce los tokens del archivo de entrada en una lista */
	private static ArrayList<String> sacarTokens() throws IOException{
		ArrayList<String> tipos = new ArrayList<String>();
		FileReader f = new FileReader(archivoEntrada);
		BufferedReader b = new BufferedReader(f);
		while((linea = b.readLine())!=null) {
			String tipo="";
			for(int i=1;linea.charAt(i)!=',';i++){
				
				tipo+=linea.charAt(i);
			}
			if(tipo.equals("PalRes")){
				tipo="";
				for(int j=8;linea.charAt(j)!='>';j++){
					
					tipo+=linea.charAt(j);
				}
			}
			tipos.add(tipo);
		}
		b.close();
		return tipos;
	}
	
	/* Obtiene el siguiente token de la entrada */
	private static String sig(){
		String a = entrada.get(0);
		entrada.remove(0);
		return a;
	}
	
	/* Extrae la cima de la pila */
	private static String extraerCima(){
		String a = pila.get(pila.size()-1);
		pila.remove(pila.size()-1);
		return a;
	}
	
	/* Saca la poduccion correspondiente de la tabla de producciones */
	private static String buscarTabla(String cima, String entrada) throws IOException{
		String res=null;
		String line;
		String[] entradas;
		boolean encontrado=false;
		int pos=0;
		FileReader f = new FileReader(tabla);
		BufferedReader b = new BufferedReader(f);
		line=b.readLine();
		entradas=line.split("	");
		for(int i=3;i<entradas.length-1&&!encontrado;i+=2){
			if(entradas[i].equals(entrada)){
				encontrado=true;
				pos=i;
			}
		}
		b.readLine();
		line=b.readLine();
		while(!encontrado&&line.charAt(0)!='_'){
			entradas=line.split("	");
			if(entradas[1].equals(cima)){
				encontrado=false;
				if(!entradas[pos].equals("	"))
					res=entradas[pos];
			}
			else
				line=b.readLine();
		}
		b.close();
		return res;
	}
	
	/* Obtiene el n�mero de producci�n seg�n el orden del archivo GramASInt */
	private static int buscarProd(String prod) throws IOException{
		String line;
		boolean encontrado=false;
		int cont=1;
		FileReader f = new FileReader(tabla);
		BufferedReader b = new BufferedReader(f);
		line=b.readLine();
		while(line.charAt(0)!='P'){
			line=b.readLine();
		}
		while(!encontrado&&(line=b.readLine())!=null){
			if(line.contains(prod))
				encontrado=true;
			else
				cont++;
		}
		b.close();
		return cont;
	}
	

	
	/* PROGRAMA PRINCIPAL */
	public static void main(String[] args) throws Exception{
		TablaDecision tablaDecision;
		ListaProducciones producciones = null;
		HashMap<Celda,ArrayList> tabla;
		Produccion prod;
		String axioma=null;
		String[] temporal;
		String cadena;
		String[] terminales=null;
		String[] noTerminales=null;
		int contador=0;
		FileReader f = new FileReader(gramatica);
		BufferedReader b = new BufferedReader(f);
		while((cadena = b.readLine())!=null) {
			if(cadena.length()!=0 && !cadena.startsWith("//")){
				temporal = cadena.split(" ");
				if(cadena.startsWith("Terminales = {") && cadena.endsWith("}")){
					terminales = new String[temporal.length - 2];
					for(int i=3;i<temporal.length-1;i++)
						terminales[i-3]=temporal[i];
					terminales[terminales.length-2]="$";
				}
				else if(cadena.startsWith("NoTerminales = {") && cadena.endsWith("}")){				
					noTerminales = new String[temporal.length - 3];
					for(int i=3;i<temporal.length-1;i++)
						noTerminales[i-3]=temporal[i];
				}
				else if(cadena.startsWith("Axioma = ")){				
					producciones = new ListaProducciones(temporal[2]);
				}
				else if(temporal.length>2 && temporal[1].equals("->")){		
					ArrayList<String> consecuente = new ArrayList<String>();
					for(int i=2;i<temporal.length;i++)
						consecuente.add(temporal[i]);
					prod = new Produccion(temporal[0],consecuente);
					producciones.addProduccion(prod);
				}
					
				else if(cadena.startsWith("Axioma ="))
					axioma = temporal[2];
				
			}
		}
		
		//producciones.print();
		System.out.println("Símbolos Terminales\n"+producciones.getTerminales());
		System.out.println("\nSímbolos No Terminales\n"+producciones.getNoterminales());
		producciones.updateFirstandFollow();
		System.out.println("\n\n\n\n");
		producciones.compruebaGramatica();
		tablaDecision=producciones.buildTable();
		tablaDecision.print();
		
		//System.out.println(producciones.toString());
		//System.out.println(producciones.getAxioma());
		/*Iterator<String> it = ((List<String>) producciones).iterator();
		while (it.hasNext())
			System.out.println(it.next());
		tabla = new HashMap<Celda,ArrayList>();
		for(int i=0;i<terminales.length-1;i++)
			for(int j=0;j<noTerminales.length-1;j++){
				tabla.put(new Celda(terminales[i],noTerminales[j]), null);
			}
		b.close();*/
		
		
		/*entrada=sacarTokens();
		entrada.add("$");
		siguiente=sig();
		pila.add("$");
		cima=pila.get(0);
		FileWriter w = new FileWriter(gram);
		PrintWriter pw = new PrintWriter(w);
		pw.print("Descendente");
		boolean fallo=false;
		while(!cima.equals("$")&&!fallo){
			boolean cond=false;
			for(int i=0;i<terminales.length-1&&!cond;i++){
				if(cima.equals(terminales[i])){
					cond=true;
				}
			}
			if(cond){
				if(cima.equals(siguiente)){
					cima=extraerCima();
					siguiente=sig();
				}
				else{
					System.err.print("Error sint�ctico: Token "+siguiente+" inesperado");
					fallo=true;	
				}
			}
			else{
				String prod;
				if((prod=buscarTabla(cima,siguiente))!=null){
					int nprod=buscarProd(prod);
					pw.print(" "+nprod);
					String[] b = prod.split(" ");
					for(int i=b.length-1;i>0;i--){
						pila.add(b[i]);
					}
				}
				else{
					System.err.println("Analizador sint�ctico: error");
					fallo=true;
				}
			}
		}
		w.close();
		if(!fallo&&siguiente.equals("$")){
			System.out.println("ANALIZADOR SINT�CTICO CORRECTO");
		}
		else
			System.err.println("Analizador sint�ctico: error");*/
	}
			
}