package caso2;

import java.util.ArrayList;

public class MemoriaVirtual {
	private ArrayList<Pagina> paginas;
	
	public MemoriaVirtual(int numPaginas) {
		paginas = new ArrayList<Pagina>(numPaginas);
        for (int i = 0; i < numPaginas; i++) {
            paginas.add(new Pagina(i));
        }
	}
	
	public Pagina getPagina(int id) {
		return paginas.get(id);
	}
	
	public void accederPagina(int id) {
		for(int i = 0; i < paginas.size(); i++) {
			Pagina pagina = paginas.get(i);
			pagina.setBits(pagina.getBits() >> 1);
		}
		Pagina pagina = paginas.get(id);
    	pagina.setBits( pagina.getBits() | 128 );
	}
}
