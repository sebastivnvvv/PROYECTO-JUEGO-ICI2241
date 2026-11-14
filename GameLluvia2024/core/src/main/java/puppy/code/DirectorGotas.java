package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

/**
 * Patrón Builder - Director.
 *
 * Conoce las "recetas" para armar cada variante de gota usando los builders.
 * Quien lo usa (Lluvia) decide CUÁL variante quiere, pero no necesita saber
 * cómo se configura ni qué builder participa.
 */
public class DirectorGotas {
    private final Texture texGota;
    private final Texture texViento;
    private final Sound sonidoViento;

    public DirectorGotas(Texture texGota, Texture texViento, Sound sonidoViento) {
        this.texGota = texGota;
        this.texViento = texViento;
        this.sonidoViento = sonidoViento;
    }

    /** Gota de agua común: quita 1 vida. */
    public GotaAgua aguaComun(float x, float y, ComportamientoGota mov) {
        return new GotaAguaBuilder()
                .posicion(x, y)
                .textura(texGota)
                .movimiento(mov)
                .build();
    }

    /** Gota de agua pesada: quita 2 vidas y cae en zigzag. */
    public GotaAgua aguaPesada(float x, float y) {
        return new GotaAguaBuilder()
                .posicion(x, y)
                .textura(texGota)
                .movimiento(new MovimientoZigzag(80f, 5f, 1f))
                .danio(2)
                .build();
    }

    /** Gota de viento común: cura 1 vida y da 1 carga. */
    public GotaViento vientoComun(float x, float y) {
        return new GotaVientoBuilder()
                .posicion(x, y)
                .textura(texViento)
                .sonido(sonidoViento)
                .build();
    }

    /** Gota de viento potente: cura 1 vida y da 2 cargas. */
    public GotaViento vientoPotente(float x, float y) {
        return new GotaVientoBuilder()
                .posicion(x, y)
                .textura(texViento)
                .sonido(sonidoViento)
                .cargasOtorgadas(2)
                .build();
    }
}
