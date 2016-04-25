## Cosas que hacer

### AppDoctor
* Servicio para descargar datos de paciente que inicie en boot
* Para el servicio hay que guardar los ids de los pacientes, se puede usar SharedPreferences con Set`<String>` o base de datos
* ~~Lista de pacientes: Mostrar id para registrarlo en AppPaciente~~
* ~~Actualizar datos de grafica (Cambiar tag valores por "Diametro de la pierna")~~
* ~~NavigationDrawer~~
* Temas
* Localizacion de strings
* Layouts de diferentes tamaños

### AppPaciente
* ~~Servicio para subir los datos al servidor~~
* ~~Servicio para recibir alertas, inicia en boot~~
* ~~Notificaciones de alertas~~
* Pruebas de bluetooth (no se tendra el calcetin asi que habra que hacer simulador de este)
* Mejorar diseño
* Temas
* Localizacion de strings
* Layouts de diferentes tamaños

### Servidor
* ~~PHPs necesarios para los servicios~~
* ~~PHP (subir.php) para subir valores recolectados en AppPaciente~~
* ~~PHP (config.php) para verificar el id el paciente en la configuracion inicial en AppPaciente en InitActivity~~

### Base de datos
*  ~~Agregar columna id a alerta (llave primaria, quitar la llave primaria id_paciente)~~ (Ya esta en el servidor pero no en el dump)

### Otras cosas
* Diseñar iconos de apps
* Hacer simulador de calcetin para pruebas de bluetooth
* Signed APK
