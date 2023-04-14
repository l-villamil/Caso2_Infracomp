package caso2;

import java.util.ArrayList;

public class ThEnvejecimiento extends Thread{

    private ArrayList<Integer> referencias;

    private MemoriaVirtual memoriaVirtual;

    private MemoriaReal memoriaReal;

    private TablaPaginacion tablaPaginacion;

    private ArrayList<Integer> referenciasRemplazo;

    public ThEnvejecimiento(ArrayList<Integer> referencias, ArrayList<Integer> referenciasRemplazo ,MemoriaVirtual memoriaVirtual, TablaPaginacion tablaPaginacion, MemoriaReal memoriaReal)
    {
        this.memoriaVirtual = memoriaVirtual;
        this.tablaPaginacion = tablaPaginacion;
        this.referencias = referencias;
        this.referenciasRemplazo = referenciasRemplazo;
        this.memoriaReal = memoriaReal;
    }

    public void run(){
        envejecimiento();
    }

    public void envejecimiento(){
        boolean noTermina = true;
        while(noTermina){
            if (!referenciasRemplazo.isEmpty()) {
                Integer referencia = referenciasRemplazo.get(0);
                synchronized(memoriaVirtual){
                    int indexTabla = memoriaReal.reemplazarPagina(memoriaVirtual.getPagina(referencia));
                    tablaPaginacion.setPagina(referencia, indexTabla);
                }

                synchronized(referenciasRemplazo){
                referenciasRemplazo.remove(0);}
            }
            if(referencias.isEmpty() && referenciasRemplazo.isEmpty()){
                noTermina = false;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
