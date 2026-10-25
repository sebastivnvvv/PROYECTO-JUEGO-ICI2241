# GameLluvia2024

Juego 2D desarrollado en **Java** con el framework **libGDX** como proyecto de escritorio. El jugador debe sobrevivir a una lluvia de gotas, esquivando las gotas de agua y recogiendo objetos de viento para recuperar vidas y obtener una habilidad especial.

## Contenido

- [Características](#características)
- [Controles](#controles)
- [Cómo funciona el juego](#cómo-funciona-el-juego)
- [Estructura del proyecto](#estructura-del-proyecto)
- [Requisitos](#requisitos)
- [Instalación y ejecución](#instalación-y-ejecución)
- [Generar un ejecutable JAR](#generar-un-ejecutable-jar)
- [Tecnologías y patrones de diseño](#tecnologías-y-patrones-de-diseño)

## Características

- Movimiento horizontal del personaje mediante el teclado.
- Gotas de agua que hacen perder vidas al colisionar con el jugador.
- Gotas de viento que permiten recuperar una vida (hasta un máximo de 3) y acumular cargas.
- Habilidad especial que elimina las gotas que están en pantalla.
- Gotas con movimiento recto o en zigzag.
- La velocidad y frecuencia de aparición de las gotas aumentan a medida que pasa el tiempo.
- Indicadores de tiempo sobrevivido, vidas, cargas de viento y récord.
- Pantalla de *Game Over* y opción para reiniciar la partida.
- Efectos de sonido y música de lluvia.

## Controles

| Tecla | Acción |
|---|---|
| `←` Flecha izquierda | Mover al personaje hacia la izquierda |
| `→` Flecha derecha | Mover al personaje hacia la derecha |
| `ESPACIO` | Usar la bomba de viento cuando se tienen 3 cargas |
| `ENTER` | Reiniciar después de perder |

El personaje tiene 3 vidas al comenzar. Las gotas de agua reducen las vidas; las gotas de viento pueden recuperar una vida y suman cargas hasta un máximo de 3. Al usar la habilidad con `ESPACIO`, se consumen las 3 cargas acumuladas y se eliminan las gotas presentes en pantalla.

## Cómo funciona el juego

La clase `GameLluvia` inicializa los recursos, el jugador y la lluvia, y administra el ciclo principal de renderizado. `Player` gestiona el movimiento, las vidas, las cargas de viento y la apariencia del personaje. `Lluvia` crea y actualiza las gotas, detecta colisiones y dibuja los objetos. La partida termina cuando las vidas llegan a cero.

El récord se gestiona con `GestorPuntajes`. El récord se mantiene durante la ejecución actual del juego; no se guarda en un archivo ni persiste al cerrar la aplicación.

## Estructura del proyecto

```text
GameLluvia2024/
├── assets/                 # Imágenes, sonidos y música
├── core/
│   └── src/main/java/puppy/code/
│       ├── GameLluvia.java # Ciclo principal y estados del juego
│       ├── Player.java     # Personaje, movimiento y vidas
│       ├── Lluvia.java     # Aparición, actualización y dibujo de gotas
│       ├── Gota.java       # Clase base abstracta de las gotas
│       ├── GotaAgua.java   # Gota que daña al jugador
│       ├── GotaViento.java # Gota que otorga viento y recupera vida
│       ├── GotaBuilder.java# Construcción de gotas
│       ├── TipoGota.java   # Tipos de gota
│       ├── EstrategiaMovimiento.java
│       ├── MovimientoRecto.java
│       ├── MovimientoZigzag.java
│       └── GestorPuntajes.java
├── lwjgl3/
│   └── src/main/java/      # Lanzador de escritorio
├── build.gradle
├── settings.gradle
├── gradle.properties
├── gradlew                 # Wrapper para Linux/macOS
└── gradlew.bat             # Wrapper para Windows
```

## Requisitos

- **JDK 11** o compatible con la configuración Gradle del proyecto.
- Conexión a Internet en la primera ejecución para descargar Gradle y las dependencias, salvo que ya estén en caché.
- Windows, Linux o macOS para ejecutar la versión de escritorio.

## Instalación y ejecución

1. Descarga o clona el repositorio y descomprime el proyecto.
2. Abre una terminal dentro de la carpeta `GameLluvia2024/` (donde se encuentra `gradlew`).
3. Ejecuta el comando correspondiente a tu sistema operativo.

**Windows (PowerShell o CMD):**

```bat
gradlew.bat lwjgl3:run
```

**Linux o macOS:**

```bash
chmod +x gradlew
./gradlew lwjgl3:run
```

Gradle descargará las dependencias necesarias y abrirá el juego. También puedes importar la carpeta `GameLluvia2024/` como proyecto Gradle en IntelliJ IDEA.

## Generar un ejecutable JAR

Desde la carpeta `GameLluvia2024/`, ejecuta:

**Windows:**

```bat
gradlew.bat lwjgl3:jar
```

**Linux o macOS:**

```bash
./gradlew lwjgl3:jar
```

El JAR se genera en `lwjgl3/build/libs/`. Para ejecutarlo, utiliza una instalación compatible de Java:

```bash
java -jar lwjgl3/build/libs/GameLluvia2024-1.0.0.jar
```

El nombre exacto del archivo puede variar según la configuración de versión del proyecto. Si el JAR no incluye o no encuentra los recursos requeridos, ejecuta el proyecto mediante Gradle (`lwjgl3:run`).

## Tecnologías y patrones de diseño

- **Java 11:** lenguaje de programación.
- **libGDX 1.12.1:** framework para gráficos, entrada, audio y ciclo de juego.
- **Gradle 8.10.1:** automatización de compilación y gestión de dependencias.
- **Strategy:** `EstrategiaMovimiento` permite intercambiar el movimiento recto y el movimiento en zigzag.
- **Builder:** `GotaBuilder` configura y construye gotas según su tipo, textura, sonido y estrategia de movimiento.
- **Singleton:** `GestorPuntajes` ofrece una instancia compartida para administrar el récord.

## Solución de problemas

- **No se reconoce Java:** instala JDK 11 y verifica que `java -version` funcione en la terminal.
- **Error al descargar dependencias:** comprueba tu conexión a Internet y vuelve a ejecutar el comando Gradle.
- **No aparecen imágenes o sonidos:** ejecuta el proyecto desde Gradle y verifica que la carpeta `assets/` esté presente y contenga los recursos del juego.
