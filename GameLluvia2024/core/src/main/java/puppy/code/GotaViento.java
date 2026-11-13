package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class GotaViento extends Gota {
    private final Sound sonido;
    private final int curacion;
    private final int cargasOtorgadas;

    public GotaViento(float x, float y, Texture textura, ComportamientoGota movimiento, Sound sonido) {
        this(x, y, textura, movimiento, sonido, 1, 1);
    }

    public GotaViento(float x, float y, Texture textura, ComportamientoGota movimiento,
                      Sound sonido, int curacion, int cargasOtorgadas) {
        super(x, y, textura, movimiento);
        this.sonido = sonido;
        this.curacion = curacion;
        this.cargasOtorgadas = cargasOtorgadas;
    }

    @Override
    protected void afectarVidas(Player player) {
        player.modificarVidas(curacion);
    }

    @Override
    protected void aplicarEfectoSecundario(Player player) {
        player.agregarCargasViento(cargasOtorgadas);
    }

    @Override
    protected void actualizarApariencia(Player player) {
        player.setPelosParados(true);
    }

    @Override
    protected void reproducirSonido(Player player) {
        sonido.play();
    }

    public Sound getSonido() {
        return sonido;
    }

    public int getCuracion() {
        return curacion;
    }

    public int getCargasOtorgadas() {
        return cargasOtorgadas;
    }
}