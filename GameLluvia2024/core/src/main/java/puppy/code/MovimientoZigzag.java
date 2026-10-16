package puppy.code;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class MovimientoZigzag implements EstrategiaMovimiento {
    private final float amplitud;
    private final float frecuencia;
    private final float factorVelocidad;
    private float xInicial;
    private float tiempo = 0f;
    private boolean iniciado = false;

    public MovimientoZigzag(float amplitud, float frecuencia, float factorVelocidad) {
        this.amplitud = amplitud;
        this.frecuencia = frecuencia;
        this.factorVelocidad = factorVelocidad;
    }

    @Override
    public void mover(Rectangle area, float velocidad, float delta) {
        if (!iniciado) {
            xInicial = area.x;
            iniciado = true;
        }
        tiempo += delta;
        area.y -= velocidad * factorVelocidad * delta;
        area.x = MathUtils.clamp(xInicial + MathUtils.sin(tiempo * frecuencia) * amplitud, 0, 800 - 64);
    }
}
