# ProyectoBingoTrome

Sistema web de gestión de Bingo desarrollado con Spring Boot que permite gestionar cartillas de bingo, registrar números sorteados y verificar automáticamente las cartillas ganadoras.

## Descripción del Proyecto

Este proyecto facilita la administración de juegos de bingo mediante las siguientes funcionalidades:

### Funcionalidades Principales

1. **Registro de Cartillas de Bingo**
   - Crear y gestionar cartillas de bingo con sus números correspondientes
   - Asignar cartillas a días específicos de la semana
   - Visualizar detalles completos de cada cartilla registrada

2. **Registro de Números Sorteados**
   - CRUD completo para gestionar los números que van saliendo durante el juego
   - Captura manual de números
   - Extracción automática de números desde imágenes mediante OCR (Tesseract)
   - Asociación de números a días específicos

3. **Comparación y Verificación de Cartillas**
   - **Botón "Comparar Números"**: Verifica automáticamente cuántos números de una cartilla han sido sorteados
   - Los números coincidentes se pintan de color verde para facilitar la verificación visual con la cartilla física
   - Sistema de estados: cuando una cartilla es revisada mediante "Comparar Números", su estado cambia automáticamente a **"Revisado"**
   - Permite identificar rápidamente cartillas ganadoras

4. **Interfaz de Revisión**
   - Panel de revisión para verificar el estado de todas las cartillas
   - Filtrado por estado (pendiente/revisado)
   - Visualización clara de números coincidentes

## Requisitos

- **JDK 17** instalado (la aplicación usa `java.version=17` en `pom.xml`)
- **Maven** (opcional): No hace falta instalar Maven global si usas el wrapper incluido (`mvnw.cmd`)
- **Tesseract OCR** (opcional): Solo si deseas usar la funcionalidad de extracción de números desde imágenes
- **Base de datos**: MySQL para producción o H2 en memoria para desarrollo

## Inicio Rápido

```powershell
# 1. Compilar el proyecto
.\mvnw.cmd -DskipTests clean package

# 2. Ejecutar en modo desarrollo (H2 en memoria)
.\mvnw.cmd "-Dspring-boot.run.profiles=dev" spring-boot:run

# 3. Acceder a la aplicación
# URL: http://localhost:8080
``` 

## Instalación y Configuración

### Verificar Requisitos

**1. Verificar instalación de Java:**

```powershell
java -version
```

Debe mostrar Java versión 17 o superior.

**2. Configurar `JAVA_HOME` (si es necesario):**

Si `java` no está disponible en PATH, configúralo temporalmente en la sesión actual:

```powershell
$env:JAVA_HOME = 'C:\Program Files\Java\jdk-17'
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"
java -version
```

### Compilación del Proyecto

Desde la raíz del repositorio:

```powershell
cd C:\Users\W11\Documents\GitHub\ProyectoTromeBingo
.\mvnw.cmd -DskipTests clean package
```

Este comando:
- Descarga todas las dependencias necesarias
- Compila el código fuente
- Genera el archivo JAR ejecutable en la carpeta `target/`

## Ejecución de la Aplicación

### Opción 1: Modo Desarrollo (BD H2 en memoria) - Recomendado para pruebas

```powershell
# Ejecuta con el perfil 'dev' - usa H2 en memoria (no requiere MySQL)
.\mvnw.cmd "-Dspring-boot.run.profiles=dev" spring-boot:run
```

Ventajas del modo desarrollo:
- No requiere configurar MySQL
- Base de datos en memoria (datos temporales)
- Consola H2 disponible en `http://localhost:8080/h2-console`

### Opción 2: Ejecutar JAR empaquetado (modo dev)

```powershell
cd target
java -jar ProyectoBingoTrome-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```

### Opción 3: Modo Producción (MySQL)

Si tienes una base de datos MySQL configurada:

```powershell
# Configurar variables de entorno
$env:SPRING_DATASOURCE_URL = 'jdbc:mysql://localhost:3306/bd_trome'
$env:SPRING_DATASOURCE_USERNAME = 'tu_usuario'
$env:SPRING_DATASOURCE_PASSWORD = 'tu_contraseña'

# Ejecutar aplicación
.\mvnw.cmd spring-boot:run
```

**Nota**: También puedes importar el script de la base de datos ubicado en `BD/bd_Trome.sql`

## Herramientas de Desarrollo

### Consola H2 (modo desarrollo)

Cuando ejecutas la aplicación con el perfil `dev`, puedes acceder a la consola de la base de datos H2:

- **URL**: http://localhost:8080/h2-console
- **JDBC URL**: `jdbc:h2:mem:devdb`
- **Usuario**: `SA`
- **Contraseña**: (vacía)

Esta consola te permite:
- Ejecutar consultas SQL directamente
- Verificar datos en tiempo real
- Inspeccionar el esquema de la base de datos

### Detener la Aplicación

Para detener la aplicación cuando está en ejecución:

```powershell
# En la terminal donde está ejecutándose, presionar:
Ctrl + C
```

## Uso de la Aplicación

### Flujo de Trabajo Típico

1. **Registrar una Cartilla de Bingo**
   - Acceder a la sección de registro de cartillas
   - Ingresar los números de la cartilla (generalmente 15 números)
   - Asociar la cartilla a un día de la semana
   - Guardar la cartilla

2. **Registrar Números Sorteados**
   - **Opción A - Manual**: Ingresar números uno por uno en el CRUD de números
   - **Opción B - OCR**: Subir una imagen con los números mediante el endpoint `/upload-numbers`

3. **Verificar Cartillas**
   - Ir a la sección de revisión de cartillas
   - Hacer clic en el botón **"Comparar Números"** de la cartilla que deseas verificar
   - El sistema automáticamente:
     - Compara los números de la cartilla con los números sorteados
     - Pinta de **verde** los números coincidentes
     - Cambia el estado de la cartilla a **"Revisado"**
   - Verifica visualmente la cartilla física contra la pantalla

4. **Identificar Ganadores**
   - Las cartillas con mayor cantidad de números en verde son potenciales ganadoras
   - El estado "Revisado" indica que ya fueron verificadas

## Funcionalidad OCR - Extracción de Números desde Imágenes

La aplicación incluye una funcionalidad avanzada que permite extraer números automáticamente desde imágenes usando Tesseract OCR.

### Instalación de Tesseract OCR

**Opción 1: Chocolatey (Recomendado)**

```powershell
choco install tesseract
```

**Opción 2: Instalación Manual**

1. Descargar desde: https://github.com/tesseract-ocr/tesseract
2. Instalar siguiendo las instrucciones del instalador
3. Asegurarse de que esté agregado al PATH del sistema

**Verificar Instalación:**

```powershell
tesseract --version
```

### Uso del Endpoint de OCR

**Endpoint**: `POST /upload-numbers`

**Parámetros**:
- `file`: Archivo de imagen (JPG, PNG, etc.)
- `diaSemana`: (opcional) ID del día de la semana (por defecto: 1)

**Ejemplo con PowerShell:**

```powershell
# Subir imagen y extraer números automáticamente
curl.exe -X POST "http://localhost:8080/upload-numbers" `
  -F "file=@C:\ruta\a\tu\imagen.jpg" `
  -F "diaSemana=1"
```

**Respuesta Esperada:**

```
Saved numbers: [5, 12, 23, 34, 45, 56]
```

### Configuración Avanzada de Tesseract

#### Opción A: Tesseract CLI (Predeterminado)

La aplicación intentará primero usar el ejecutable `tesseract` del sistema.

#### Opción B: Tess4J (Java Integration)

Si prefieres que la aplicación use Tesseract completamente desde Java (sin depender del ejecutable):

1. La dependencia `tess4j` ya está incluida en `pom.xml`
2. Configurar la variable de entorno `TESSDATA_PREFIX`:

```powershell
$env:TESSDATA_PREFIX = 'C:\Program Files\Tesseract-OCR\tessdata'
```

**Comportamiento de Fallback:**

1. Primero intenta usar `tesseract` CLI
2. Si falla, intenta usar Tess4J (Java)
3. Si ambos fallan, muestra mensaje con instrucciones

### Consejos para Mejor OCR

- Usar imágenes con buen contraste
- Números con tamaño legible
- Evitar ruido o fondos complejos
- Preferir imágenes claras y bien enfocadas

## Tecnologías Utilizadas

### Backend
- **Spring Boot 2.x** - Framework principal
- **Spring Data JPA** - Persistencia de datos
- **Thymeleaf** - Motor de plantillas para vistas
- **Lombok** - Reducción de código boilerplate
- **MySQL** - Base de datos de producción
- **H2** - Base de datos en memoria para desarrollo

### Frontend
- **HTML5 / CSS3**
- **JavaScript**
- **Thymeleaf Templates**
- **Bootstrap** (opcional, verificar en plantillas)

### Herramientas Adicionales
- **Tesseract OCR** - Extracción de números desde imágenes
- **Tess4J** - Binding Java para Tesseract
- **Maven** - Gestión de dependencias y construcción

## Configuración para Desarrollo (IDE)

### Lombok

El proyecto utiliza **Lombok** para reducir código repetitivo (getters, setters, constructores, etc.).

**Instalación en tu IDE:**

1. **IntelliJ IDEA**:
   - Instalar plugin: `Settings → Plugins → Buscar "Lombok" → Install`
   - Habilitar procesamiento de anotaciones: `Settings → Build, Execution, Deployment → Compiler → Annotation Processors → Enable annotation processing`

2. **Eclipse**:
   - Descargar `lombok.jar` desde https://projectlombok.org/download
   - Ejecutar el jar y seguir el instalador
   - Reiniciar Eclipse

3. **VS Code**:
   - Instalar extensión "Lombok Annotations Support for VS Code"

## Solución de Problemas

### Error: `JAVA_HOME not found` o `java` no reconocido

**Solución**: Asegúrate de que `JAVA_HOME` apunte a un JDK 17 y que `java` esté en el PATH.

```powershell
$env:JAVA_HOME = 'C:\Program Files\Java\jdk-17'
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"
```

### Error de conexión a MySQL al ejecutar

**Causa**: La configuración predeterminada intenta conectarse a MySQL según `application.properties`.

**Solución**: Ejecuta con el perfil `dev` para usar H2 en lugar de MySQL:

```powershell
.\mvnw.cmd "-Dspring-boot.run.profiles=dev" spring-boot:run
```

O configura las variables de entorno con tus credenciales de MySQL.

### Compilación lenta o descarga repetida de dependencias

**Solución**: 
- Maven descarga dependencias solo la primera vez
- Para compilaciones más rápidas posteriores, simplemente ejecuta:
  ```powershell
  .\mvnw.cmd clean package
  ```
- Las dependencias se cachean en `~/.m2/repository`

### Errores de Lombok en el IDE

**Solución**:
1. Verifica que el plugin de Lombok esté instalado
2. Habilita el procesamiento de anotaciones en la configuración del IDE
3. Limpia y reconstruye el proyecto (`Build → Rebuild Project`)

### OCR no funciona o no detecta números

**Soluciones**:
1. Verifica que Tesseract esté instalado:
   ```powershell
   tesseract --version
   ```
2. Verifica que esté en el PATH del sistema
3. Usa imágenes con mejor calidad y contraste
4. Configura `TESSDATA_PREFIX` si usas Tess4J

### Puerto 8080 ya en uso

**Solución**: Cambia el puerto en `application.properties` o `application-dev.properties`:

```properties
server.port=8081
```

## Seguridad y Buenas Prácticas

### ⚠️ Credenciales de Base de Datos

- El archivo `src/main/resources/application.properties` puede contener credenciales de MySQL
- **NUNCA** subas credenciales reales a repositorios públicos
- Para producción, usa variables de entorno o servicios de gestión de secretos (AWS Secrets Manager, Azure Key Vault, etc.)

**Configuración recomendada para producción:**

```powershell
# Variables de entorno
$env:SPRING_DATASOURCE_URL = 'jdbc:mysql://servidor-prod:3306/bd_trome'
$env:SPRING_DATASOURCE_USERNAME = 'usuario_prod'
$env:SPRING_DATASOURCE_PASSWORD = 'contraseña_segura'
```

### Perfiles de Spring

- **dev**: Desarrollo local con H2
- **prod**: Producción con MySQL (configurar mediante variables de entorno)

## Estructura del Proyecto

```
ProyectoTromeBingo/
├── BD/                          # Scripts de base de datos
│   └── bd_Trome.sql            # Schema y datos iniciales
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/bingoTrome/
│   │   │       ├── Controller/      # Controladores REST y MVC
│   │   │       ├── Model/           # Entidades JPA
│   │   │       ├── Repository/      # Repositorios JPA
│   │   │       └── Enum/            # Enumeraciones
│   │   └── resources/
│   │       ├── application.properties          # Config principal
│   │       ├── application-dev.properties      # Config desarrollo
│   │       ├── templates/                      # Vistas Thymeleaf
│   │       └── static/                         # Recursos estáticos
│   └── test/                    # Tests unitarios
├── pom.xml                      # Dependencias Maven
└── mvnw.cmd                     # Maven Wrapper para Windows
```

## Contribuir

Si deseas contribuir al proyecto:

1. Fork el repositorio
2. Crea una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -am 'Agrega nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Crea un Pull Request

## Licencia

[Especificar licencia del proyecto]

## Contacto

[Información de contacto o enlaces relevantes]

---

## Notas de Versión

### Versión Actual
- ✅ CRUD de cartillas de bingo
- ✅ CRUD de números sorteados
- ✅ Comparación automática de números (botón "Comparar Números")
- ✅ Sistema de estados (Pendiente/Revisado)
- ✅ Extracción OCR de números desde imágenes
- ✅ Soporte para H2 (desarrollo) y MySQL (producción)
- ✅ Interfaz web responsiva

### Mejoras Futuras Propuestas
- [ ] Frontend con React o Vue.js
- [ ] API REST completa
- [ ] Autenticación y autorización de usuarios
- [ ] Exportación de reportes (PDF, Excel)
- [ ] Notificaciones en tiempo real
- [ ] Modo oscuro
- [ ] Soporte multi-idioma

---

**¿Necesitas ayuda?** Revisa la sección de [Solución de Problemas](#solución-de-problemas) o abre un issue en el repositorio.