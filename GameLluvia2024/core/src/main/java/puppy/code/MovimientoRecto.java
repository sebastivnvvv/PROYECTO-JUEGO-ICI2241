package puppy.code;

import com.badlogic.gdx.math.Rectangle;

public class MovimientoRecto implements EstrategiaMovimiento {
    @Override
    public void mover(Rectangle area, float velocidad, float delta) {
        area.y -= velocidad * delta;
    }
}
