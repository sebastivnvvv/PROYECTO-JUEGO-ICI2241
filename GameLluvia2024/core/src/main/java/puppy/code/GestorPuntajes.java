package puppy.code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Singleton que centraliza el estado "global" de las partidas:
 * historial de mejores tiempos, estadísticas acumuladas y su persistencia.
 */
public class GestorPuntajes {
    private static final String ARCHIVO = "gotas-puntajes";
    private static final int MAX_HISTORIAL = 5;

    // 1. Instancia única privada y estática (asociación de la clase consigo misma en el UML)
    private static GestorPuntajes instancia;

    private final Preferences prefs;
    private final List<Float> mejoresTiempos = new ArrayList<>();
    private int partidasJugadas;
    private int aguasRecibidas;
    private int vientosRecogidos;
    private int bombasUsadas;

    // 2. Constructor privado: nadie fuera de la clase puede hacer 'new'
    private GestorPuntajes() {
        prefs = Gdx.app.getPreferences(ARCHIVO);
        cargar();
    }

    // 3. Acceso global controlado
    public static synchronized GestorPuntajes getInstancia() {
        if (instancia == null) {
            instancia = new GestorPuntajes();
        }
        return instancia;
    }

    // ---------- Registro de eventos ----------

    /** Registra el fin de una partida. Retorna true si fue nuevo récord. */
    public boolean registrarPartida(float tiempo) {
        partidasJugadas++;
        boolean nuevoRecord = mejoresTiempos.isEmpty() || tiempo > mejoresTiempos.get(0);

        mejoresTiempos.add(tiempo);
        Collections.sort(mejoresTiempos, Collections.reverseOrder());
        while (mejoresTiempos.size() > MAX_HISTORIAL) {
            mejoresTiempos.remove(mejoresTiempos.size() - 1);
        }
        guardar();
        return nuevoRecord;
    }

    public void registrarAguaRecibida()   { aguasRecibidas++; }
    public void registrarVientoRecogido() { vientosRecogidos++; }
    public void registrarBombaUsada()     { bombasUsadas++; }

    // ---------- Consultas (solo lectura) ----------

    public float getMejorTiempo() {
        return mejoresTiempos.isEmpty() ? 0f : mejoresTiempos.get(0);
    }

    public List<Float> getMejoresTiempos() {
        return Collections.unmodifiableList(mejoresTiempos);
    }

    public int getPartidasJugadas()  { return partidasJugadas; }
    public int getAguasRecibidas()   { return aguasRecibidas; }
    public int getVientosRecogidos() { return vientosRecogidos; }
    public int getBombasUsadas()     { return bombasUsadas; }

    // ---------- Persistencia ----------

    private void cargar() {
        partidasJugadas  = prefs.getInteger("partidas", 0);
        aguasRecibidas   = prefs.getInteger("aguas", 0);
        vientosRecogidos = prefs.getInteger("vientos", 0);
        bombasUsadas     = prefs.getInteger("bombas", 0);

        String guardado = prefs.getString("top", "");
        if (!guardado.isEmpty()) {
            for (String valor : guardado.split(",")) {
                mejoresTiempos.add(Float.parseFloat(valor));
            }
            Collections.sort(mejoresTiempos, Collections.reverseOrder());
        }
    }

    /** Guarda todo en disco. Se llama al terminar cada partida. */
    public void guardar() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mejoresTiempos.size(); i++) {
            if (i > 0) sb.append(',');
            sb.append(mejoresTiempos.get(i));
        }
        prefs.putString("top", sb.toString());
        prefs.putInteger("partidas", partidasJugadas);
        prefs.putInteger("aguas", aguasRecibidas);
        prefs.putInteger("vientos", vientosRecogidos);
        prefs.putInteger("bombas", bombasUsadas);
        prefs.flush();
    }
}