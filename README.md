# GameLluvia2024

Juego 2D desarrollado en **Java** con el framework **libGDX**. El objetivo del juego es controlar un tarro para atrapar las gotas de lluvia que aparecen en pantalla.

## Descripción

`GameLluvia2024` es un proyecto de videojuego con una estructura multiplataforma basada en Gradle y libGDX. La lógica principal se encuentra en el módulo `core`, mientras que el módulo `lwjgl3` permite ejecutar el juego como una aplicación de escritorio.

El proyecto incluye recursos gráficos y de audio para representar el tarro, las gotas y los efectos de sonido del juego.

## Características

- Juego 2D de lluvia y recolección de gotas.
- Personajes/objetos gráficos para el tarro y las gotas.
- Recursos de audio para acompañar los eventos del juego.
- Proyecto Java organizado en módulos con Gradle.
- Ejecución de escritorio mediante LWJGL3.

## Tecnologías utilizadas

- **Java**
- **libGDX**
- **Gradle**
- **LWJGL3**

## Estructura del proyecto

```text
GameLluvia2024/
├── assets/                  # Imágenes y sonidos del juego
├── core/
│   └── src/main/java/
│       └── puppy/code/
│           ├── GameLluvia.java
│           ├── Lluvia.java
│           └── Tarro.java
├── lwjgl3/
│   └── src/main/java/       # Lanzador para escritorio
├── build.gradle
├── settings.gradle
├── gradlew
└── gradlew.bat
```

### Clases principales

- **`GameLluvia.java`**: clase principal del juego; coordina la ejecución y los elementos del juego.
- **`Lluvia.java`**: representa y gestiona las gotas de lluvia.
- **`Tarro.java`**: representa el tarro que el jugador utiliza para atrapar las gotas.
- **`Lwjgl3Launcher.java`**: inicia la aplicación en escritorio.

## Requisitos

- JDK compatible con la versión de Gradle configurada en el proyecto.
- Una terminal o IDE compatible con proyectos Gradle, por ejemplo IntelliJ IDEA o Eclipse.

## Cómo ejecutar el juego

### Windows

Desde la carpeta raíz del proyecto, abre una terminal y ejecuta:

```bat
gradlew.bat lwjgl3:run
```

### Linux o macOS

Desde la carpeta raíz del proyecto, ejecuta:

```bash
./gradlew lwjgl3:run
```

Si Linux o macOS no permite ejecutar el archivo, concede permisos y vuelve a intentarlo:

```bash
chmod +x gradlew
./gradlew lwjgl3:run
```

También puedes importar la carpeta del proyecto en un IDE como un proyecto Gradle y ejecutar el lanzador de escritorio del módulo `lwjgl3`.

## Controles

Los controles exactos dependen de la implementación del proyecto. Revisa la lógica de entrada en `GameLluvia.java` para confirmar cómo se mueve el tarro (por ejemplo, mediante el mouse o el teclado).

## Recursos

Los archivos de imágenes y sonidos se encuentran en `assets/`. Entre los recursos incluidos hay imágenes del tarro y de las gotas, además de archivos de audio para los efectos y la música.

## Autoría y licencia

Completa esta sección con el nombre de los autores y la licencia correspondiente, si aplica.
