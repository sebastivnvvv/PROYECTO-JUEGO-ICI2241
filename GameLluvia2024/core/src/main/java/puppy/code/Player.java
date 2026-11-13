package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Player {
	private static Player instanciaActual;

	private Rectangle area;
	private Texture texSeco;
	private Texture texMojado1;
	private Texture texMojado2;
	private Texture texPelosParados;
	private boolean pelosParados = false;
	private Sound sonidoHerido;
	private int vidas = 3;
	private int puntos = 0;
	private int velx = 400;
	private boolean herido = false;
	private int tiempoHeridoMax = 50;
	private int tiempoHerido;
	private int cargasViento = 0;

	public Player(Texture texSeco, Texture texMojado1, Texture texMojado2,
				  Texture texPelosParados, Sound ss) {
		this.texSeco = texSeco;
		this.texMojado1 = texMojado1;
		this.texMojado2 = texMojado2;
		this.texPelosParados = texPelosParados;
		this.sonidoHerido = ss;

		// Guardamos la instancia automáticamente al crearse el jugador
		instanciaActual = this;
	}

	public static Player getInstancia() {
		return instanciaActual;
	}

	public int getVidas() {
		return vidas;
	}

	public int getPuntos() {
		return puntos;
	}

	public int getCargasViento() {
		return cargasViento;
	}

	public Rectangle getArea() {
		return area;
	}

	public void sumarViento() {
		if(cargasViento < 3) {
			cargasViento++;
		}
		pelosParados = true;
		if(vidas < 3) {
			vidas++;
		}
	}

	public boolean usarBombaViento() {
		if(cargasViento >= 3) {
			cargasViento -= 3;
			return true;
		}
		return false;
	}

	public void modificarVidas(int cantidad) {
		vidas = MathUtils.clamp(vidas + cantidad, 0, 3);
	}

	public void agregarCargasViento(int cantidad) {
		cargasViento = MathUtils.clamp(cargasViento + cantidad, 0, 3);
	}

	public void activarHerido() {
		herido = true;
		tiempoHerido = tiempoHeridoMax;
	}

	public void setPelosParados(boolean pelosParados) {
		this.pelosParados = pelosParados;
	}

	public boolean isPelosParados() {
		return pelosParados;
	}

	public void reproducirSonidoHerido() {
		sonidoHerido.play();
	}

	public void crear() {
		area = new Rectangle();
		area.x = 800 / 2 - 64 / 2;
		area.y = 20;
		area.width = 64;
		area.height = 64;
	}

	public void dañar() {
		vidas--;
		herido = true;
		tiempoHerido = tiempoHeridoMax;
		sonidoHerido.play();
		pelosParados = false;
	}

	public void dibujar(SpriteBatch batch) {
		Texture texturaActual;

		if (pelosParados) {
			texturaActual = texPelosParados;
		} else {
			switch (vidas) {
				case 3:
					texturaActual = texSeco;
					break;
				case 2:
					texturaActual = texMojado1;
					break;
				case 1:
				default:
					texturaActual = texMojado2;
					break;
			}
		}

		if (!herido) {
			batch.draw(texturaActual, area.x, area.y, area.width, area.height);
		} else {
			batch.draw(texturaActual, area.x, area.y + MathUtils.random(-5, 5), area.width, area.height);
			tiempoHerido--;
			if (tiempoHerido <= 0) herido = false;
		}
	}

	public void actualizarMovimiento() {
		// Movimiento desde teclado con operadores y tiempos corregidos
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT))  area.x -= velx * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) area.x += velx * Gdx.graphics.getDeltaTime();

		// Límites de la pantalla
		if(area.x < 0) area.x = 0;
		if(area.x > 800 - 64) area.x = 800 - 64;
	}

	public void destruir() {
		texSeco.dispose();
		texMojado1.dispose();
		texMojado2.dispose();
		texPelosParados.dispose();
		sonidoHerido.dispose();
	}

	public boolean estaHerido() {
		return herido;
	}
}