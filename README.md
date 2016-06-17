# rizla

COSAS POR HACER

ANALIZADOR LÉXICO
	-Cambiar formato de los tokens para reconocer el siguiente formato de los tokens:
	<Reservada,var>
	<Reservada,function>
	<Reservada,return>
	<Reservada,if> 
	<Reservada,for>
	<opIO,write>
	<opIO,read>
	<tipo,int>
	<tipo,bool>
	<tipo,chars>
	<id,[lexema]>
	<cons,int>
	<cons,bool>
	<cons,chars>
	<br,null>
	<=,null>
	<-=,null>
	<(,null>
	<),null>
	<+,null> 
	<==,null> 
	<&&,null> 
	<,,null>
	<{,null>
	<},null>
	<;,null>

ANALIZADOR SINTÁCTICO
	-Comprobar si son correctos los símbolos terminales y no terminales del principio del archivo
	-Corregir Paso 3 del Follow
	-Implementar análisis como tal 
ANALIZADOR SEMÁNTICO
	-Todo
