package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class GotaViento extends Gota {
    private final Sound sonido;

    public GotaViento(float x, float y, Texture textura, EstrategiaMovimiento movimiento, Sound sonido) {
        super(x, y, textura, movimiento);
        this.sonido = sonido;
    }

    @Override
    public void aplicarEfecto(Player player) {
        player.sumarViento();
        sonido.play();
    }

    public Sound getSonido() { 
    	return sonido; 
    }
}