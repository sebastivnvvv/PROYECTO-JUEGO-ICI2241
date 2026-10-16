package puppy.code;

import com.badlogic.gdx.math.Rectangle;

public class Gota {
    public static final int VIENTO = 1;
    public static final int AGUA = 2;

    private final Rectangle area;
    private final int tipo;
    private final EstrategiaMovimiento movimiento;

    public Gota(float x, float y, int tipo, EstrategiaMovimiento movimiento) {
        this.area = new Rectangle(x, y, 64, 64);
        this.tipo = tipo;
        this.movimiento = movimiento;
    }

    public void mover(float velocidad, float delta) {
        movimiento.mover(area, velocidad, delta);
    }

    public boolean salioDePantalla() {
        return area.y + 64 < 0;
    }

    public Rectangle getArea() {
        return area;
    }

    public int getTipo() {
        return tipo;
    }
}
