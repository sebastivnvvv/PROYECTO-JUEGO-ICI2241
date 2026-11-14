package puppy.code;

/** Patrón Builder - Builder concreto de {@link GotaAgua}. */
public class GotaAguaBuilder extends GotaBuilder<GotaAguaBuilder, GotaAgua> {
    private int danio = 1;

    public GotaAguaBuilder danio(int danio) {
        if (danio < 1) {
            throw new IllegalArgumentException("El daño debe ser al menos 1");
        }
        this.danio = danio;
        return this;
    }

    @Override
    protected GotaAguaBuilder self() {
        return this;
    }

    @Override
    public GotaAgua build() {
        validar();
        return new GotaAgua(x, y, textura, movimientoFinal(), danio);
    }
}
