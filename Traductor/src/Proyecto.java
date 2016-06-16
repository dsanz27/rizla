import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class Proyecto {
	private static final String [] PalRes={"for","var","int","chars","bool","function","write","prompt","break","return","true","false"};
	private static int tokens = 0;
	private static String linea;
	private static String archivoEntrada = "src/prueba.txt";
	private static String archivoSalida = "src/tokens.txt";
	
	public Proyecto(String archivo){
	}
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException{
		leerArchivo();
	}
	
	
	public static void leerArchivo() throws FileNotFoundException, IOException {

		FileReader f = new FileReader(archivoEntrada);
		BufferedReader b = new BufferedReader(f);
		int numLin=0;
		while((linea = b.readLine())!=null) {
			numLin++;
			for(int i=0;i<linea.length();i++){
				char c=linea.charAt(i);
				if(Character.isDigit(c)){
					String token=Character.toString(c);

					while(i<linea.length()-1 && Character.compare(linea.charAt(i+1), ' ')!=0
							&& Character.compare(linea.charAt(i+1), '=')!=0 && Character.compare(linea.charAt(i+1), '	')!=0 
							&& Character.compare(linea.charAt(i+1), ')')!=0 && Character.compare(linea.charAt(i+1), '}')!=0){
						i++;

						c=linea.charAt(i);
						token+=c;
					}
					try{
					if(Integer.parseInt(token)<32768){
						genToken("Entero",token);
//					System.out.println("linea "+numLin+" caracter "+token);
					}else
						System.err.println("El entero "+token+" en la linea "+numLin+" es demasiado grande");
					}catch(NumberFormatException e){
						System.err.println("Error l�xico, el entero o identificador "+token+" en la linea "+numLin+" no es correcto");
					}
					
				}
				else if(Character.isLetter(c)){
					String token=Character.toString(c);

					while(i<linea.length()-1 && (Character.isLetter(linea.charAt(i+1)) 
							|| Character.isDigit(linea.charAt(i+1)) || Character.compare(linea.charAt(i+1), '_')==0)){
						i++;

						c=linea.charAt(i);
						token+=c;
					}
					boolean reservada=false;
					for(int j=0;!reservada && j<PalRes.length;j++){
						if(token.equals(PalRes[j])){
							reservada=true;
							genToken("PalRes",token);
						}
					}
					if(!reservada){
						boolean p=false;
						if(Character.compare(c, ':')==0 || Character.compare(c, ',')==0){
							token=(String) token.subSequence(0, token.length()-1);
							p=true;
						}
						genToken("Id",token);
						if(p){
							genToken("Puntuacion",Character.toString(c));
						}
					}
//					System.out.println("linea "+numLin+" palabra "+token);
				}
				else if(Character.compare(c, '/')==0){
					i++;
					c=linea.charAt(i);
					if(Character.compare(c, '/')==0){
						String token="";

						while(i<linea.length()-1){
							i++;

							c=linea.charAt(i);
							token+=c;
						}
						//System.out.println("linea "+numLin+" comentario "+token);
					}
					else{
						System.err.println("Comentario mal formado en la linea: "+numLin);
						i=linea.length();
					}
				}
				else if(Character.compare(c, '"')==0){
					String token=Character.toString(c);

					while(i<linea.length()-1 && Character.compare(linea.charAt(i+1), '"')!=0){
						i++;

						c=linea.charAt(i);
						token+=c;
					}
					if(i<linea.length()-1){
						i++;
						c=linea.charAt(i);
						token+=c;
						genToken("Cad",token);
//						System.out.println("linea "+numLin+" cadena "+token);
					}else
						System.err.println("Error l�xico, faltan o sobran comillas dobles en la linea: "+numLin);
				}
				else if(Character.compare(c, ' ')==0 || Character.compare(c, '	')==0){//Ignorar
					System.out.println("linea "+numLin+" caracter "+(i+1)+" espacio");
				}
				else if(Character.compare(c, '+')==0){
					String token=Character.toString(c);

					if(i<linea.length()-1 && Character.compare(linea.charAt(i+1), ' ')==0){
						genToken("Op",token);
					}else if(i<linea.length()-1 && Character.compare(linea.charAt(i+1), '+')==0){
						i++;
						c=linea.charAt(i);
						token+=c;
						genToken("PreInc",token);
					}else
						System.err.println("Error l�xico, signo "+token+" mal usado en la linea: "+numLin);
				}
				else if(Character.compare(c, '-')==0){
					String token=Character.toString(c);

					if(i<linea.length()-1 && Character.compare(linea.charAt(i+1), ' ')==0){
						genToken("Op",token);
					}
					else if(i<linea.length()-1 && Character.compare(linea.charAt(i+1), '=')==0){
						i++;
						c=linea.charAt(i);
						token+=c;
						genToken("OpAsign",token);
					}
					else
						System.err.println("Error l�xico, signo "+token+" mal usado en la linea: "+numLin);
				}
				else if(Character.compare(c, '=')==0){
					String token=Character.toString(c);

					if(i<linea.length()-1 && Character.compare(linea.charAt(i+1), '=')==0){
						i++;
						c=linea.charAt(i);
						token+=c;
						genToken("Comparador",token);
					}else
						genToken("Asignacion",token);
				}
				else if(Character.compare(c, '{')==0||Character.compare(c, '}')==0||Character.compare(c, '(')==0||Character.compare(c, ')')==0){
					String token=Character.toString(c);

//					if(i<linea.length()-1 && Character.compare(c, ' ')==0){
						genToken(token,token);
//					}else
//						System.err.println("Error l�xico, signo "+token+" mal usado en la linea: "+numLin);
				}
				
				else{
					String token=Character.toString(c);

					while(i<linea.length()-1 && Character.compare(c, ' ')!=0){
						i++;

						c=linea.charAt(i);
						token+=c;
					}
					System.err.println("linea "+numLin+" token "+token+" no v�lido ");
					//								System.out.println(linea);
					//								System.err.println("Lo que sea");
				}
			}
			genToken("BR","");
		}
		b.close();
	}
	
	/* Genera un nuevo token */
	private static void genToken(String codigo,String atributo){
		
		FileWriter fichero = null;
		PrintWriter pw = null;
		try {
			fichero = (tokens==0) ? new FileWriter(archivoSalida) : new FileWriter(archivoSalida,true);
			pw = new PrintWriter(fichero);

			pw.println("<"+codigo+","+atributo+">");
			tokens++;

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (null != fichero)
					fichero.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
