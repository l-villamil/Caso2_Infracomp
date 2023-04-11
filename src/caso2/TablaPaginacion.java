package caso2;

import java.util.ArrayList;

public class TablaPaginacion {
    private ArrayList<Integer> paginas;

    public TablaPaginacion(int numPaginas) {
        paginas = new ArrayList<Integer>(numPaginas);
        for (int i = 0; i < numPaginas; i++) {
            paginas.add(-1);
        }
    }

    public Integer getPagina(int id) {
        return paginas.get(id);
    }
    
    public void setPagina(int id, int numPagina) {
    	for (int i = 0; i < paginas.size(); i++) {
            if(paginas.get(i) == numPagina)
            	paginas.set(i, -1);
        }
        paginas.set(id, numPagina);
    }
}
