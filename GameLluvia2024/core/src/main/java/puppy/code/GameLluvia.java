package puppy.code;



import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;



public class GameLluvia extends ApplicationAdapter {
       private OrthographicCamera camera;
	   private SpriteBatch batch;	   
	   private BitmapFont font;
	   
	   private Player player;
	   private Lluvia lluvia;
	   private float tiempoSobrevivido = 0f;
	   private  boolean gameOver = false;
	@Override
	public void create () {
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
	      // creacion del tarro
	      player.crear();
	      
	      // creacion de la lluvia
	      lluvia.crear();
	}
	


	@Override
	public void render () {
		//limpia la pantalla con color azul obscuro.
		ScreenUtils.clear(0, 0, 0.2f, 1);
		//actualizar matrices de la cámara
		camera.update();
		//actualizar 
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		if (player.getVidas() <= 0){
			gameOver = true;
			GestorPuntajes.getInstancia().guardarTiempo(tiempoSobrevivido);
		}
		if (!gameOver ){
			tiempoSobrevivido += Gdx.graphics.getDeltaTime();

			// Dibujar textos en pantalla (Tiempo y Vidas y Cargas de Viento)
			font.draw(batch, "Tiempo: " + (int)tiempoSobrevivido + "s", 5, 475);
			font.draw(batch, "Récord: " + (int)GestorPuntajes.getInstancia().getMejorTiempo() + "s", 5, 450);
			font.draw(batch, "Vidas: " + player.getVidas(), 350, 475);
			font.draw(batch, "Vientos (ESPACIO): " + player.getCargasViento() + "/3", 600, 475);

			// Activar bomba de viento con la barra espaciadora si tiene 3 cargas
			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				if (player.usarBombaViento()) {
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
		} else{
			font.draw(batch, "¡GAME OVER!", 350, 280);
			font.draw(batch, "Sobreviviste: " + (int)tiempoSobrevivido + " segundos", 310, 240);
			font.draw(batch, "Mejor Récord: " + (int)GestorPuntajes.getInstancia().getMejorTiempo() + "s", 310, 1900);
			font.draw(batch, "Presiona ENTER para reiniciar", 290, 200);

			// Opcional: Reiniciar al presionar ENTER
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				reiniciarJuego();
			}
		}

		batch.end();	
		
	}
	private void reiniciarJuego() {
		tiempoSobrevivido = 0f;
		gameOver = false;
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
	public void dispose () {
	      player.destruir();
          lluvia.destruir();
	      batch.dispose();
	      font.dispose();
	}
}

