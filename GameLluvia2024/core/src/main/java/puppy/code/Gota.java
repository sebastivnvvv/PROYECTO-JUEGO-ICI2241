package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public abstract class Gota {
    private final Rectangle area;
    private final Texture textura;
    private final ComportamientoGota movimiento;

    protected Gota(float x, float y, Texture textura, ComportamientoGota movimiento) {
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

    /**
     * Template Method: define el esqueleto fijo de lo que ocurre cuando
     * una gota colisiona con el jugador. Es final para que las subclases
     * no puedan alterar el orden de los pasos, solo su contenido.
     */
    public final void procesarColision(Player player) {
        afectarVidas(player);
        aplicarEfectoSecundario(player);
        actualizarApariencia(player);
        reproducirSonido(player);
    }

    // Paso 1 (abstracto): daña o cura al jugador.
    protected abstract void afectarVidas(Player player);

    // Paso 2 (abstracto): efecto adicional sobre el jugador.
    protected abstract void aplicarEfectoSecundario(Player player);

    // Paso 3 (hook): apariencia del jugador.
    protected void actualizarApariencia(Player player) {
        player.setPelosParados(false);
    }

    // Paso 4 (hook): sonido del impacto, por defecto, sin sonido.
    protected void reproducirSonido(Player player) {
        // sin sonido por defecto
    }

    public Rectangle getArea() {
        return area;
    }

    public Texture getTextura() {
        return textura;
    }

    public ComportamientoGota getMovimiento() {
        return movimiento;
    }
}