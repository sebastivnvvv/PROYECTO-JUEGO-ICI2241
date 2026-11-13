package puppy.code;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class MovimientoMixto implements ComportamientoGota {
    private final ComportamientoGota recto;
    private final ComportamientoGota zigzag;
    private float tiempoVida = 0;
    private final float tiempoCambio; // segundos antes de cambiar a zigzag
    private boolean zigzagIniciado = false;

    public MovimientoMixto() {
        this.recto = new MovimientoRecto();
        this.zigzag = new MovimientoZigzag(80f, 5f, 1f);
        this.tiempoCambio = MathUtils.random(0.2f, 0.8f);
    }

    @Override
    public void mover(Rectangle area, float velocidad, float delta) {
        tiempoVida += delta;

        if (tiempoVida < tiempoCambio) {
            recto.mover(area, velocidad, delta);
        } else {
            if (!zigzagIniciado) {
                zigzagIniciado = true;
            }
            zigzag.mover(area, velocidad, delta);
        }
    }
}
