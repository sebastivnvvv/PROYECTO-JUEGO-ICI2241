package puppy.code;

import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameLluvia extends ApplicationAdapter {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private BitmapFont font;

	private Player player;
	private Lluvia lluvia;
	private float tiempoSobrevivido = 0f;
	private boolean gameOver = false;

	// --- Elementos de la pantalla de Game Over ---
	private Texture pixel; // 1x1 blanco para dibujar rectángulos
	private final GlyphLayout layout = new GlyphLayout();
	private boolean nuevoRecord = false;
	private static final Color FONDO  = new Color(0f, 0f, 0f, 0.55f);
	private static final Color PANEL  = new Color(0.05f, 0.08f, 0.25f, 0.95f);
	private static final Color BORDE  = new Color(0.4f, 0.7f, 1f, 1f);
	private static final Color BRONCE = new Color(0.8f, 0.5f, 0.2f, 1f);

	@Override
	public void create() {
		font = new BitmapFont(); // use libGDX's default Arial font

		// load the images for the droplet and the bucket, 64x64 pixels each
		Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
		Texture texSeco = new Texture(Gdx.files.internal("joven_seco.png"));
		Texture texMojado1 = new Texture(Gdx.files.internal("joven_mojado1.png"));
		Texture texMojado2 = new Texture(Gdx.files.internal("joven_mojado2.png"));
		Texture texPelos = new Texture(Gdx.files.internal("joven_pelos_parados.png"));
		player = new Player(texSeco, texMojado1, texMojado2, texPelos, hurtSound);

		// load the drop sound effect and the rain background "music"
		Texture texViento = new Texture(Gdx.files.internal("viento.png"));
		Texture texGota = new Texture(Gdx.files.internal("drop.png"));

		Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));

		Music rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
		lluvia = new Lluvia(texViento, texGota, dropSound, rainMusic);

		// camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();

		// textura de 1x1 pixel blanco, usada para dibujar el panel del Game Over
		Pixmap pm = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		pm.setColor(Color.WHITE);
		pm.fill();
		pixel = new Texture(pm);
		pm.dispose();

		// creacion del tarro
		player.crear();

		// creacion de la lluvia
		lluvia.crear();
	}

	@Override
	public void render() {
		// limpia la pantalla con color azul obscuro.
		ScreenUtils.clear(0, 0, 0.2f, 1);
		// actualizar matrices de la cámara
		camera.update();
		// actualizar
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		if (!gameOver && player.getVidas() <= 0) {
			gameOver = true;
			nuevoRecord = GestorPuntajes.getInstancia().registrarPartida(tiempoSobrevivido);
		}
		if (!gameOver) {
			tiempoSobrevivido += Gdx.graphics.getDeltaTime();

			// Dibujar textos en pantalla (Tiempo y Vidas y Cargas de Viento)
			font.draw(batch, "Tiempo: " + (int) tiempoSobrevivido + "s", 5, 475);
			font.draw(batch, "Récord: " + (int) GestorPuntajes.getInstancia().getMejorTiempo() + "s", 5, 450);
			font.draw(batch, "Vidas: " + player.getVidas(), 350, 475);
			font.draw(batch, "Vientos (ESPACIO): " + player.getCargasViento() + "/3", 600, 475);

			// Activar bomba de viento con la barra espaciadora si tiene 3 cargas
			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				if (player.usarBombaViento()) {
					GestorPuntajes.getInstancia().registrarBombaUsada();
					lluvia.limpiarPantalla(); // ¡Elimina todas las gotas cercanas/en pantalla!
				}
			}
			if (!player.estaHerido()) {
				// movimiento del tarro desde teclado
				player.actualizarMovimiento();
				// caida de la lluvia
				lluvia.actualizarMovimiento(player, tiempoSobrevivido);
			}
			player.dibujar(batch);
			lluvia.actualizarDibujoLluvia(batch);
		} else {
			dibujarGameOver();
		}

		batch.end();
	}

	// ------------------------------------------------------------------
	// Pantalla de Game Over
	// ------------------------------------------------------------------

	private void dibujarGameOver() {
		GestorPuntajes g = GestorPuntajes.getInstancia();

		// fondo oscurecido + panel con borde
		rect(0, 0, 800, 480, FONDO);
		rect(196, 26, 408, 428, BORDE);
		rect(200, 30, 400, 420, PANEL);

		texto("¡GAME OVER!", 2.2f, Color.RED, 425);
		texto("Sobreviviste " + (int) tiempoSobrevivido + " segundos", 1.3f, Color.WHITE, 370);

		if (nuevoRecord) {
			texto("¡NUEVO RÉCORD!", 1.5f, Color.GOLD, 338);
		} else {
			texto("Mejor récord: " + (int) g.getMejorTiempo() + "s", 1.2f, Color.LIGHT_GRAY, 338);
		}

		rect(260, 315, 280, 2, BORDE);

		texto("TOP 5", 1.3f, Color.CYAN, 295);
		List<Float> top = g.getMejoresTiempos();
		Color[] colores = { Color.GOLD, Color.LIGHT_GRAY, BRONCE, Color.WHITE, Color.WHITE };
		float y = 265;
		for (int i = 0; i < top.size() && i < colores.length; i++) {
			texto((i + 1) + ".  " + (int) (float) top.get(i) + "s", 1.1f, colores[i], y);
			y -= 25;
		}

		rect(260, 135, 280, 2, BORDE);

		texto("Partidas jugadas: " + g.getPartidasJugadas(), 1f, Color.LIGHT_GRAY, 112);
		texto("Bombas de viento usadas: " + g.getBombasUsadas(), 1f, Color.LIGHT_GRAY, 88);

		// texto que parpadea suavemente
		float alfa = 0.65f + 0.35f * MathUtils.sin(System.currentTimeMillis() / 250f);
		texto("Presiona ENTER para reiniciar", 1.1f, new Color(1f, 1f, 1f, alfa), 55);

		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			reiniciarJuego();
		}
	}

	/** Dibuja texto centrado horizontalmente en la pantalla. */
	private void texto(String s, float escala, Color color, float y) {
		font.getData().setScale(escala);
		font.setColor(color);
		layout.setText(font, s);
		font.draw(batch, layout, 400 - layout.width / 2f, y);
		font.getData().setScale(1f);
		font.setColor(Color.WHITE);
	}

	/** Dibuja un rectángulo de color usando la textura de 1x1. */
	private void rect(float x, float y, float w, float h, Color color) {
		batch.setColor(color);
		batch.draw(pixel, x, y, w, h);
		batch.setColor(Color.WHITE);
	}

	// ------------------------------------------------------------------

	private void reiniciarJuego() {
		player.destruir();
		tiempoSobrevivido = 0f;
		gameOver = false;
		nuevoRecord = false;
		Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
		Texture texSeco = new Texture(Gdx.files.internal("joven_seco.png"));
		Texture texMojado1 = new Texture(Gdx.files.internal("joven_mojado1.png"));
		Texture texMojado2 = new Texture(Gdx.files.internal("joven_mojado2.png"));
		Texture texPelos = new Texture(Gdx.files.internal("joven_pelos_parados.png"));
		player = new Player(texSeco, texMojado1, texMojado2, texPelos, hurtSound);
		player.crear();
		Texture texViento = new Texture(Gdx.files.internal("viento.png"));
		Texture texGota = new Texture(Gdx.files.internal("drop.png"));
		Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		Music rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
		lluvia.destruir();
		lluvia = new Lluvia(texViento, texGota, dropSound, rainMusic);
		lluvia.crear();
	}

	@Override
	public void dispose() {
		player.destruir();
		lluvia.destruir();
		batch.dispose();
		font.dispose();
		pixel.dispose();
	}
}