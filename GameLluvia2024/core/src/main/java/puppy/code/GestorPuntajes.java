package puppy.code;

public class GestorPuntajes {
    // 1. Instancia única privada y estática
    private static GestorPuntajes instancia;
    private float mejorTiempo;

    // 2. Constructor privado para evitar el uso de 'new' desde fuera
    private GestorPuntajes() {
        mejorTiempo = 0f;
    }

    // 3. Método estático público de acceso global
    public static GestorPuntajes getInstancia() {
        if (instancia == null) {
            instancia = new GestorPuntajes();
        }
        return instancia;
    }

    // Lógica para evaluar y actualizar el récord
    public boolean guardarTiempo(float tiempo) {
        if (tiempo > mejorTiempo) {
            mejorTiempo = tiempo;
            return true; // Retorna true si se superó el récord anterior
        }
        return false;
    }

    public float getMejorTiempo() {
        return mejorTiempo;
    }
}