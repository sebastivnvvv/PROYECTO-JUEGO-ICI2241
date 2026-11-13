package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class GotaAgua extends Gota {
    private final int danio;

    public GotaAgua(float x, float y, Texture textura, ComportamientoGota movimiento) {
        this(x, y, textura, movimiento, 1);
    }

    public GotaAgua(float x, float y, Texture textura, ComportamientoGota movimiento, int danio) {
        super(x, y, textura, movimiento);
        this.danio = danio;
    }

    @Override
    protected void afectarVidas(Player player) {
        player.modificarVidas(-danio);
    }

    @Override
    protected void aplicarEfectoSecundario(Player player) {
        player.activarHerido();
    }

    // actualizarApariencia: usa el hook por defecto.

    @Override
    protected void reproducirSonido(Player player) {
        player.reproducirSonidoHerido();
    }

    public int getDanio() {
        return danio;
    }
}