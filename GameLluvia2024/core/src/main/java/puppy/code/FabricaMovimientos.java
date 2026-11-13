package puppy.code;

import com.badlogic.gdx.math.MathUtils;

public class FabricaMovimientos {

    public static ComportamientoGota crearMovimientoAleatorio() {
        float rand = MathUtils.random();

        if (rand < 0.3f) {
            return new MovimientoRecto();
        } else if (rand < 0.6f) {
            return new MovimientoZigzag(80f, 5f, 1f);
        } else if (rand < 0.8f) {
            return new MovimientoMixto();
        } else {
            return new MovimientoIman();
        }
    }
}