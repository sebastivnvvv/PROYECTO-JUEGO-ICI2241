package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Lluvia {
	private Array<Rectangle> rainDropsPos;
	private Array<Integer> rainDropsType;
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
		rainDropsPos.clear();
		rainDropsType.clear();
	}

	public void crear() {
		rainDropsPos = new Array<Rectangle>();
		rainDropsType = new Array<Integer>();
		crearGotaDeLluvia();
	      // start the playback of the background music immediately
	      rainMusic.setLooping(true);
	      rainMusic.play();
	}
	
	private void crearGotaDeLluvia() {
	      Rectangle raindrop = new Rectangle();
	      raindrop.x = MathUtils.random(0, 800-64);
	      raindrop.y = 480;
	      raindrop.width = 64;
	      raindrop.height = 64;
	      rainDropsPos.add(raindrop);
	      // ver el tipo de gota
	      if (MathUtils.random(1, 10)< 2)
	         rainDropsType.add(1);
	      else 
	    	 rainDropsType.add(2);
	      lastDropTime = TimeUtils.nanoTime();
	   }
	
   public void actualizarMovimiento(Player player, float tiempoJuego) {
	   // La frecuencia de aparición también se vuelve un poco más rápida con el tiempo
	   long intervalo = Math.max(200000000L, 700000000L - (long)(tiempoJuego * 10000000L));

	   if(TimeUtils.nanoTime() - lastDropTime > intervalo) {
		   crearGotaDeLluvia();
	   }

	   for (int i = 0; i < rainDropsPos.size; i++) {
		   Rectangle raindrop = rainDropsPos.get(i);

		   // VELOCIDAD PROGRESIVA: Base de 250 + el tiempo jugado multiplica la velocidad
		   float velocidad = 250 + (tiempoJuego * 10);
		   raindrop.y -= velocidad * Gdx.graphics.getDeltaTime();

		   if(raindrop.y + 64 < 0) {
			   rainDropsPos.removeIndex(i);
			   rainDropsType.removeIndex(i);
		   }

		   if(raindrop.overlaps(player.getArea())) {
			   if(rainDropsType.get(i) == 1) { // Es el Viento
				   player.sumarViento();
				   dropSound.play();
				   rainDropsPos.removeIndex(i);
				   rainDropsType.removeIndex(i);
			   } else { // Es la Gota
				   player.dañar();
				   rainDropsPos.removeIndex(i);
				   rainDropsType.removeIndex(i);
			   }
		   }
	   }
   }
   
   public void actualizarDibujoLluvia(SpriteBatch batch) { 
	   
	  for (int i=0; i < rainDropsPos.size; i++ ) {
		  Rectangle raindrop = rainDropsPos.get(i);
		  if(rainDropsType.get(i)==1) // gota dañina
	         batch.draw(texViento, raindrop.x, raindrop.y, raindrop.width, raindrop.height);
		  else
			 batch.draw(texGota, raindrop.x, raindrop.y, raindrop.width, raindrop.height);
	   }
   }
   public void destruir() {
	      dropSound.dispose();
	      rainMusic.dispose();
   }
   
}
