package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public abstract class Gota {
    private final Rectangle area;
    private final Texture textura;
    private final EstrategiaMovimiento movimiento;

    protected Gota(float x, float y, Texture textura, EstrategiaMovimiento movimiento) {
        this.area = new Rectangle(x, y, 64, 64);
        this.textura = textura;
        this.movimiento = movimiento;
    }

    public void mover(float velocidad, float delta) {
        movimiento.mover(area, velocidad, delta);
    }

    public boolean salioDePantalla() {
        return area.y + 64 < 0;
    }
    
    public final void procesarColision(Player player) {
        aplicarEfecto(player);
        reproducirSonido();
    }

    protected void reproducirSonido() {
        // por defecto: sin sonido propio
    }

    /** Efecto que produce la gota sobre el jugador al colisionar. */
    protected abstract void aplicarEfecto(Player player);

    public Rectangle getArea() {
    	return area; 
    }
    public Texture getTextura() { 
    	return textura; 
    }
    public EstrategiaMovimiento getMovimiento() { 
    	return movimiento; 
	}
}