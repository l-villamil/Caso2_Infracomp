package caso2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Principal {
	private int NF;
	private int NC;
	private int TE;
	private int TP;
	private int MP;
	
	public void cargarParametro(String parametro) {
		String[] tokens = parametro.split("=");
		if(tokens[0].equals("NF"))
			NF = Integer.parseInt(tokens[1]);
		else if(tokens[0].equals("NC"))
			NC = Integer.parseInt(tokens[1]);
		else if(tokens[0].equals("TE"))
			TE = Integer.parseInt(tokens[1]);
		else if(tokens[0].equals("TP"))
			TP = Integer.parseInt(tokens[1]);
		else if(tokens[0].equals("MP"))
			MP = Integer.parseInt(tokens[1]);
	}
	
	public int getNF() {
		return NF;
	}

	public int getNC() {
		return NC;
	}

	public int getTE() {
		return TE;
	}

	public int getTP() {
		return TP;
	}

	public int getMP() {
		return MP;
	}

	public static void modo1(Principal principal, Scanner scanner) {
		System.out.println("Ingrese el nombre del archivo con los parametros: ");
		String nombreArchivo = scanner.next(); 
		File file = new File(nombreArchivo);
	    try {
			Scanner fileScanner = new Scanner(file);
			while(fileScanner.hasNextLine())
				principal.cargarParametro(fileScanner.nextLine());
			fileScanner.close();
		} catch (FileNotFoundException e1) {
			System.out.println("No se pudo leer el archivo de entrada");
			e1.printStackTrace();
			return;
		}
	    
	    int NF = principal.getNF();
	    int NC = principal.getNC();
	    int TE = principal.getTE();
	    int TP = principal.getTP();
	    int MP = principal.getMP();
	    
	    System.out.println();
		System.out.println("Numero de filas (NF): " + NF);
		System.out.println("Numero de columnas (NC): " + NC);
		System.out.println("Tamano del entero (TE): " + TE);
		System.out.println("Tamano de pagina (TP): " + TP);
		System.out.println("Numero de marcos de pagina (MP): " + MP);
		
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter("salida.txt");
			PrintWriter printWriter = new PrintWriter(fileWriter);
		    printWriter.printf("TP=%d", TP);
		    printWriter.println();
		    printWriter.printf("NF=%d", NF);
		    printWriter.println();
		    printWriter.printf("NC=%d", NC);
		    printWriter.println();
		    printWriter.printf("NR=%d", NF*NC*3);
		    printWriter.println();
		    
		    ArrayList<Integer> referencias = new ArrayList<>();
		    
		    int pos = 0;
		    for(int i = 0; i < NF; i++) {
		    	for(int j = 0; j < NC; j++) {
		    		pos = (j + i*NC)*TE;
		    		printWriter.printf("[A-%d-%d],%d,%d", i, j, pos/TP, pos%TP);
		    		referencias.add(pos/TP);
		    		printWriter.println();
		    		pos += NC*NF*TE;
		    		printWriter.printf("[B-%d-%d],%d,%d", i, j, pos/TP, pos%TP);
		    		referencias.add(pos/TP);
		    		printWriter.println();
		    		pos += NC*NF*TE;
		    		printWriter.printf("[C-%d-%d],%d,%d", i, j, pos/TP, pos%TP);
		    		referencias.add(pos/TP);
		    		printWriter.println();
		    	}
		    }
		    
		    printWriter.close(); 
		} catch (IOException e) {
			System.out.println("Ocurrio un error al generar el archivo");
			e.printStackTrace();
		}
	}
	
	public static void modo2(Principal principal) {
		File file = new File("salida.txt");
		ArrayList<Integer> referencias = new ArrayList<>();
		
	    try {
			Scanner fileScanner = new Scanner(file);
			for(int i = 0; i < 4; i++)
				principal.cargarParametro(fileScanner.nextLine());
			
			while(fileScanner.hasNextLine()) {
				String[] tokens = fileScanner.nextLine().split(",");
				referencias.add(Integer.parseInt(tokens[1]));
			}
			fileScanner.close();
		} catch (FileNotFoundException e1) {
			System.out.println("No se pudo leer el archivo de entrada");
			e1.printStackTrace();
			return;
		}
	    
	    int NF = principal.getNF();
	    int NC = principal.getNC();
	    int TE = principal.getTE();
	    int TP = principal.getTP();
	    int MP = principal.getMP();
	    
	    int numFallos = 0;
	    int numPaginas = (int)Math.ceil((double)NF*NC*TE*3/(double)TP);
	    TablaPaginacion tablaPaginacion = new TablaPaginacion(numPaginas);
	    MemoriaVirtual memoriaVirtual = new MemoriaVirtual(numPaginas);
	    MemoriaReal memoriaReal = new MemoriaReal(MP);
	    
	    for (Integer referencia : referencias) {
	    	memoriaVirtual.accederPagina(referencia);
			if(tablaPaginacion.getPagina(referencia) == -1) {
				numFallos++;
				int indexTabla = memoriaReal.reemplazarPagina(memoriaVirtual.getPagina(referencia));
				tablaPaginacion.setPagina(referencia, indexTabla);
			}
		}
	    
	    System.out.println("Numero de fallos: " + numFallos);
	}
	
	public static void main(String[] args) {
		Principal principal = new Principal();
		Scanner scanner = new Scanner(System.in);
		System.out.println("CASO 2");
		
		
		int seleccion = 0;
		
		while (seleccion != 3) {
			System.out.println("");
			System.out.println("Seleccione el modo de ejecucion:");
			System.out.println("1. Comportamiento del proceso");
			System.out.println("2. Comportamiento del sistema de paginacion");
			System.out.println("3. Salir");
			seleccion = scanner.nextInt();
			
			if(seleccion == 1) {
				modo1(principal, scanner);
			} else if(seleccion == 2) {
				modo2(principal);
			}
		}
	}
}
