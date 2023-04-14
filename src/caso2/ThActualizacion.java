package caso2;

import java.util.ArrayList;

public class ThActualizacion extends Thread{
    
    private ArrayList<Integer> referencias;

    private MemoriaVirtual memoriaVirtual;

    private int numFallos;

    private TablaPaginacion tablaPaginacion;

    private ArrayList<Integer> referenciasRemplazo;

    boolean estoyVivo = true;

    public ThActualizacion(ArrayList<Integer> referencias, ArrayList<Integer> referenciasRemplazo ,MemoriaVirtual memoriaVirtual, TablaPaginacion tablaPaginacion)
    {
        this.numFallos = 0;
        this.memoriaVirtual = memoriaVirtual;
        this.tablaPaginacion = tablaPaginacion;
        this.referencias = referencias;
        this.referenciasRemplazo = referenciasRemplazo;
    }

    public void run(){
        actualizar();
    }

    public void actualizar(){
        for (Integer referencia : referencias) {
            synchronized(memoriaVirtual){memoriaVirtual.accederPagina(referencia);}
            if(tablaPaginacion.getPagina(referencia) == -1) {
                numFallos++;
                synchronized(referenciasRemplazo){
                referenciasRemplazo.add(referencia);}
            }

            
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        synchronized(referencias){referencias.clear();}
        System.out.println("Numero de fallos: " + numFallos);
    }
}
