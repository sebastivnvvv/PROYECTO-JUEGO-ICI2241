package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class GotaAgua extends Gota {
    public GotaAgua(float x, float y, Texture textura, EstrategiaMovimiento movimiento) {
        super(x, y, textura, movimiento);
    }

    @Override
    public void aplicarEfecto(Player player) {
        player.dañar();
    }
}