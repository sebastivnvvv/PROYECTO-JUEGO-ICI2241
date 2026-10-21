package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class GotaBuilder {
    private float x = 0f;
    private float y = 480f;
    private TipoGota tipo = TipoGota.AGUA;
    private Texture textura;
    private Sound sonido;
    private EstrategiaMovimiento movimiento;

    public GotaBuilder posicion(float x, float y) { this.x = x; this.y = y; return this; }
    public GotaBuilder tipo(TipoGota tipo) { this.tipo = tipo; return this; }
    public GotaBuilder textura(Texture textura) { this.textura = textura; return this; }
    public GotaBuilder sonido(Sound sonido) { this.sonido = sonido; return this; }
    public GotaBuilder movimiento(EstrategiaMovimiento movimiento) { this.movimiento = movimiento; return this; }

    public Gota build() {
        if (textura == null) {
            throw new IllegalStateException("La gota necesita una textura");
        }
        EstrategiaMovimiento mov = (movimiento != null) ? movimiento : new MovimientoRecto();

        switch (tipo) {
            case VIENTO:
                if (sonido == null) {
                    throw new IllegalStateException("La gota de viento necesita un sonido");
                }
                return new GotaViento(x, y, textura, mov, sonido);
            case AGUA:
            default:
                return new GotaAgua(x, y, textura, mov);
        }
    }
}