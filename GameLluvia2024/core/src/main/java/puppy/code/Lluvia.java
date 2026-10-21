package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Lluvia {
    private Array<Gota> gotas;
    private long lastDropTime;
    private Texture texViento;
    private Texture texGota;
    private Sound dropSound;
    private Music rainMusic;

    public Lluvia(Texture texViento, Texture texGota, Sound ss, Music mm) {
        rainMusic = mm;
        dropSound = ss;
        this.texViento = texViento;
        this.texGota = texGota;
    }

    public void limpiarPantalla() {
        gotas.clear();
    }

    public void crear() {
        gotas = new Array<Gota>();
        crearGotaDeLluvia();
        rainMusic.setLooping(true);
        rainMusic.play();
    }

    private void crearGotaDeLluvia() {
        float x = MathUtils.random(0, 800 - 64);
        GotaBuilder builder = new GotaBuilder().posicion(x, 480);
        if (MathUtils.random(1, 10) < 2) {
            builder.tipo(TipoGota.VIENTO)
                   .textura(texViento)
                   .sonido(dropSound)
                   .movimiento(new MovimientoRecto());
        } else {
            EstrategiaMovimiento movimiento;
            if (MathUtils.randomBoolean(0.5f)) {
                movimiento = new MovimientoRecto();
            } else {
                movimiento = new MovimientoZigzag(40f, 5f, 1f);
            }
            builder.tipo(TipoGota.AGUA)
                   .textura(texGota)
                   .movimiento(movimiento);
        }
        gotas.add(builder.build());
        lastDropTime = TimeUtils.nanoTime();
    }

    public void actualizarMovimiento(Player player, float tiempoJuego) {
        long intervalo = Math.max(200000000L, 700000000L - (long) (tiempoJuego * 10000000L));

        if (TimeUtils.nanoTime() - lastDropTime > intervalo) {
            crearGotaDeLluvia();
        }

        float velocidad = 250 + (tiempoJuego * 10);
        float delta = Gdx.graphics.getDeltaTime();

        for (int i = gotas.size - 1; i >= 0; i--) {
            Gota gota = gotas.get(i);
            gota.mover(velocidad, delta);

            if (gota.salioDePantalla()) {
                gotas.removeIndex(i);
            } else if (gota.getArea().overlaps(player.getArea())) {
            	gota.procesarColision(player);
            	gotas.removeIndex(i);
            }
        }
    }

    public void actualizarDibujoLluvia(SpriteBatch batch) {
        for (Gota gota : gotas) {
            batch.draw(gota.getTextura(), gota.getArea().x, gota.getArea().y,
                    gota.getArea().width, gota.getArea().height);
        }
    }

    public void destruir() {
        dropSound.dispose();
        rainMusic.dispose();
    }
}
