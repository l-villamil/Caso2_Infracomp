package caso2;

import java.util.ArrayList;

public class MemoriaReal {
	private ArrayList<Pagina> paginas;
	
	public MemoriaReal(int numMarcos) {
		paginas = new ArrayList<Pagina>(numMarcos);
		for(int i = 0; i < numMarcos; i++) {
			paginas.add(null);
		}
	}
	
	private int marcoLibre() {
		int index = -1;
		for(int i = 0; i < paginas.size(); i++) {
			if(paginas.get(i) == null) {
				index = i;
				break;
			}
		}
		return index;
	}
	
	public int reemplazarPagina(Pagina pagina) {
		int index = marcoLibre();
		if(index >= 0) {
			paginas.set(index, pagina);
		} else {
			Pagina paginaMenosUsada = null;
			int frecMenosUsada = Integer.MAX_VALUE;
			for (int i = 0; i<paginas.size(); i++) {
				Pagina pag = paginas.get(i);
				if(pag.getBits() < frecMenosUsada) {
					frecMenosUsada = pag.getBits();
        			paginaMenosUsada = pag;
        			index = i;
				}
	        }
			
			paginaMenosUsada.setEnMemoriaReal(false);
			pagina.setEnMemoriaReal(true);
			paginas.set(index, pagina);
		}		
		
		return index;
	}
}
