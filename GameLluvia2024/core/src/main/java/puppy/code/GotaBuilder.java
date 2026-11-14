package puppy.code;

import com.badlogic.gdx.graphics.Texture;

/**
 * Patrón Builder - Builder abstracto.
 *
 * Reúne los pasos de construcción comunes a todas las gotas (posición, textura
 * y movimiento). Cada tipo de gota tiene su propio builder concreto, que agrega
 * sus parámetros específicos y sabe qué producto crear. Así no se necesita
 * ningún switch/if sobre el tipo: elegir el builder ya es elegir el producto.
 *
 * @param <B> tipo del builder concreto (permite encadenar métodos sin perder el tipo)
 * @param <G> tipo de gota que produce
 */
public abstract class GotaBuilder<B extends GotaBuilder<B, G>, G extends Gota> {
    protected float x = 0f;
    protected float y = 480f;
    protected Texture textura;
    protected ComportamientoGota movimiento;

    /** Cada builder concreto devuelve this con su propio tipo. */
    protected abstract B self();

    public B posicion(float x, float y) {
        this.x = x;
        this.y = y;
        return self();
    }

    public B textura(Texture textura) {
        this.textura = textura;
        return self();
    }

    public B movimiento(ComportamientoGota movimiento) {
        this.movimiento = movimiento;
        return self();
    }

    /** Construye el producto final. Lo implementa cada builder concreto. */
    public abstract G build();

    /** Validación común a todas las gotas. */
    protected void validar() {
        if (textura == null) {
            throw new IllegalStateException("La gota necesita una textura");
        }
    }

    /** Si no se indicó movimiento, la gota cae en línea recta. */
    protected ComportamientoGota movimientoFinal() {
        return (movimiento != null) ? movimiento : new MovimientoRecto();
    }
}
