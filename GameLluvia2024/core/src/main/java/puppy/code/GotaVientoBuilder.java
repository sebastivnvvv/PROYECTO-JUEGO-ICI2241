package puppy.code;

import com.badlogic.gdx.audio.Sound;

/** Patrón Builder - Builder concreto de {@link GotaViento}. */
public class GotaVientoBuilder extends GotaBuilder<GotaVientoBuilder, GotaViento> {
    private Sound sonido;
    private int curacion = 1;
    private int cargasOtorgadas = 1;

    public GotaVientoBuilder sonido(Sound sonido) {
        this.sonido = sonido;
        return this;
    }

    public GotaVientoBuilder curacion(int curacion) {
        this.curacion = curacion;
        return this;
    }

    public GotaVientoBuilder cargasOtorgadas(int cargas) {
        this.cargasOtorgadas = cargas;
        return this;
    }

    @Override
    protected GotaVientoBuilder self() {
        return this;
    }

    @Override
    public GotaViento build() {
        validar();
        if (sonido == null) {
            throw new IllegalStateException("La gota de viento necesita un sonido");
        }
        return new GotaViento(x, y, textura, movimientoFinal(), sonido, curacion, cargasOtorgadas);
    }
}
