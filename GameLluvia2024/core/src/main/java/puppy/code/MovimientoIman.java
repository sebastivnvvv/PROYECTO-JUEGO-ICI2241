package puppy.code;

import com.badlogic.gdx.math.Rectangle;

public class MovimientoIman implements ComportamientoGota {
    private final Player player;
    private final float fuerzaIman;


    public MovimientoIman() {
        this.player = Player.getInstancia();
        this.fuerzaIman = 80f;
    }

    @Override
    public void mover(Rectangle area, float velocidad, float delta) {
        area.y -= velocidad * delta;

        if (player != null) {
            float playerX = player.getArea().x;

            if (area.x < playerX) {
                area.x += fuerzaIman * delta;
                if (area.x > playerX) area.x = playerX;
            }
            else if (area.x > playerX) {
                area.x -= fuerzaIman * delta;
                if (area.x < playerX) area.x = playerX;
            }
        }
    }
}