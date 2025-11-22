# ProyectoBingoTrome

Proyecto web Spring Boot (Bingo) — instrucciónes para compilar y ejecutar en Windows (PowerShell).

## Requisitos

- JDK 17 instalado (la aplicación usa `java.version=17` en `pom.xml`).
- (Opcional) No hace falta instalar Maven global si usas el wrapper incluido (`mvnw.cmd`).

## Resumen rápido

- Compilar: usa el wrapper Maven incluido.
- compilar el mysql 

## Comandos (PowerShell)

1) Verificar Java:

```powershell
java -version
```

2) (Temporal en la sesión) configurar `JAVA_HOME` si `java` no está en PATH (ajusta la ruta si tu JDK está en otro lado):

```powershell
$env:JAVA_HOME = 'C:\Program Files\Java\jdk-17'
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"
java -version
```

3) Compilar el proyecto (desde la raíz del repositorio):

```powershell
cd C:\Users\W11\Documents\GitHub\ProyectoTromeBingo
.\mvnw.cmd -DskipTests clean package
```

4) Ejecutar en modo desarrollo (usa H2 en memoria):

```powershell
# Ejecuta con el perfil 'dev' para usar H2 (no tocará la MySQL remota)
.\mvnw.cmd "-Dspring-boot.run.profiles=dev" spring-boot:run
```

5) Ejecutar el JAR empaquetado con el perfil `dev`:

```powershell
cd target
java -jar ProyectoBingoTrome-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```

6) Ejecutar contra MySQL (si tienes una BD local y quieres usarla): establece variables de entorno antes de arrancar:

```powershell
$env:SPRING_DATASOURCE_URL = 'jdbc:mysql://localhost:3306/tu_db'
$env:SPRING_DATASOURCE_USERNAME = 'tu_usuario'
$env:SPRING_DATASOURCE_PASSWORD = 'tu_pass'
.\mvnw.cmd spring-boot:run
```

## H2 Console (modo dev)

- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:devdb`
- User: `SA` (contraseña vacía por defecto)

## Notas de seguridad

- `src/main/resources/application.properties` contiene una URL y credenciales para una MySQL remota. Eso es sensible. Para producción, mueve credenciales fuera del repositorio (variables de entorno o un vault) y usa `application-prod.properties` o perfiles.
- No subas credenciales reales al repositorio público.

## IDE / Lombok

- El proyecto usa Lombok. Si tu IDE muestra errores en las entidades, instala el plugin Lombok y activa `Annotation Processing`.

## Solución de problemas comunes

- `JAVA_HOME not found` o `java` no reconocido: asegúrate de que `JAVA_HOME` apunte a un JDK 17 y que `java` esté en `PATH`.
- Error de conexión a MySQL al ejecutar sin perfil `dev`: la configuración por defecto intenta conectarse a la URL de `application.properties`; usa el perfil `dev` para evitarlo o configura las variables de entorno para tu BD.
- Para compilar más rápido sin descargar dependencias repetidas, ejecuta el comando de build una vez (.\mvnw.cmd clean package) y luego usa el JAR.

## Cómo parar la aplicación

- Si la ejecutaste con `spring-boot:run` o con `java -jar`, pulsa `Ctrl+C` en la terminal donde corre.

## Estado actual del repositorio (nota rápida)

- Se añadió una dependencia H2 y `src/main/resources/application-dev.properties` para facilitar ejecución local.

## Extracción de números desde una imagen (OCR)

El proyecto incluye un endpoint que permite subir una imagen con números y guardarlos automáticamente en el CRUD de números.

- Endpoint: `POST /upload-numbers`
- Parámetros multipart:
	- `file`: el archivo de imagen (jpg/png/...)
	- `diaSemana` (opcional): id del día asociado (por defecto `1`)

Requisitos:

- Tener instalado Tesseract OCR en el sistema y disponible en `PATH`.
	- Windows: puedes instalarlo desde https://github.com/tesseract-ocr/tesseract o usar Chocolatey: `choco install tesseract`.
	- Verifica con:

```powershell
tesseract --version
```




- Chocolatey:

```powershell
choco install tesseract
```


Tess4J (opcional)
------------------
Si prefieres que la aplicación haga OCR completamente desde la JVM (sin depender del ejecutable del sistema), se añadió soporte para Tess4J (binding Java). Para que Tess4J funcione correctamente necesitas:

- Asegurarte de que la dependencia `tess4j` esté presente (ya la agregué al `pom.xml`).
- Tener los datos de idioma (`tessdata`) disponibles. Por ejemplo, copia la carpeta `tessdata` en una ruta y exporta la variable de entorno `TESSDATA_PREFIX` apuntando a la carpeta que contiene `tessdata` (no a la carpeta `tessdata` en sí, sino a su padre según tu instalación) o ajusta el datapath en código.

Ejemplo (PowerShell) para apuntar tessdata:

```powershell
$env:TESSDATA_PREFIX = 'C:\Program Files\Tesseract-OCR\tessdata'
```

Comportamiento implementado en la app:

- Primero intenta invocar el ejecutable `tesseract` (si está instalado).
- Si falla al arrancar el proceso, intenta usar Tess4J en-JVM como fallback.
- Si ambos fallan, la UI mostrará un mensaje con instrucciones para instalar y/o configurar Tesseract/Tess4J.

Uso (PowerShell) — ejemplo con `curl.exe` incluido en Windows 10/11:

```powershell
# enviar la imagen y guardar  (ajusta ruta de archivo)
curl.exe -X POST "http://localhost:8080/upload-numbers" -F "file=@C:\ruta\a\tu\imagen.jpg" -F "diaSemana=1"
```

Respuesta esperada: texto simple como `Saved numbers: [5, 12, 23, 34, 45, 56]` o un mensaje de error si no detecta números.

Notas:

- El endpoint llama al CLI `tesseract` con una whitelist de dígitos para mejorar la detección. La calidad del OCR depende de la imagen (contraste, tamaño de los dígitos, ruido).
- Si prefieres una solución sin depender de Tesseract instalado, puedo agregar integración con una librería Java (Tess4J) o con OpenCV, aunque eso requiere dependencias nativas adicionales.

---

Si quieres, puedo:

- Eliminar las credenciales de `application.properties` y moverlas a `application-prod.properties` o variables de entorno.
- Añadir un `README` en español y en inglés, o ejemplos de `docker-compose` con MySQL.

Dime qué prefieres y lo hago.