# LOGIN
LOGIN es una aplicación móvil robusta y segura que sirve como prototipo funcional para la gestión de autenticación de usuarios. Ofrece una experiencia de usuario clara y directa, cubriendo todas las operaciones esenciales de un ciclo de vida de cuenta de usuario, desde la creación hasta la eliminación.

# Características principales
- 🪟 Interfaz clasica con XML
- 📊 Integración con ViewModel + StateFlow
- 🎨 Patrón de diseño arquitectónico con MVVM + Hexagonal
- 🧩 Inyección de dependencias con Hilt
- 💽 Base de datos remota con MySQL, la API RESTful con Node
- 📱 Compatible con Android 7.0 (API 24) en adelante

# Instalación
- Clona el repositorio: git clone https://github.com/yjot-dev/Rep-Login.git
- Abre el proyecto en Android Studio (Giraffe o superior)
- Sincroniza dependencias con Gradle
- Conecta un dispositivo o emulador y ejecuta la app

# Tecnologías usadas
- Kotlin
- XML
- AndroidX (Lifecycle, Core KTX)
- Material 3

# Uso
El flujo de uso de la aplicación está diseñado para ser intuitivo y completo, guiando al usuario a través de los siguientes pasos:

1. Acceso y Gestión de Cuenta: Al iniciar la aplicación, el usuario es recibido por una interfaz limpia con una barra de navegación inferior que presenta tres opciones directas para la gestión de su cuenta:
   - Iniciar Sesión (Login): Los usuarios existentes pueden acceder a su perfil de forma segura ingresando su nombre de usuario o correo electrónico junto con su contraseña.
   - Registrarse (Registro): Los nuevos usuarios son dirigidos a una vista dedicada donde pueden crear su cuenta de manera rápida y sencilla, proporcionando un nombre de usuario, un correo electrónico válido y una contraseña.
   - Recuperar Clave: En caso de olvido, el sistema ofrece una función de recuperación segura. El usuario ingresa su correo electrónico para recibir un código de verificación único. Tras validar dicho código, puede establecer una nueva contraseña y restaurar el acceso a su cuenta sin complicaciones.
2. Gestión del Perfil de Usuario: Una vez que el usuario ha iniciado sesión correctamente, es dirigido a una vista de "Usuario" personalizada. Esta sección funciona como su panel de control personal y le permite realizar las siguientes acciones:
   - Actualizar Datos: El usuario tiene control total para modificar su información personal, incluyendo su nombre, correo electrónico y contraseña.
   - Cerrar Sesión: Permite salir de la sesión actual de forma segura, volviendo a la pantalla de inicio de sesión.
   - Eliminar Cuenta: Ofrece la opción de eliminar permanentemente su cuenta del sistema si así lo desea.

En resumen, LOGIN no es solo una pantalla de inicio de sesión, sino un sistema de autenticación completo que demuestra una arquitectura técnica sólida (MVVM y Hexagonal), proporcionando una experiencia de usuario fluida y segura para registrarse, acceder, gestionar y proteger su identidad digital.

# Ver video Demo
[Ver en Youtube](https://youtu.be/gTadwvq60Yg)

# Contribución
- Haz un fork del repositorio
- Crea una rama con tu feature: git checkout -b feature/nueva-funcionalidad
- Haz commit de tus cambios: git commit -m "Agrega nueva funcionalidad"
- Haz push a la rama: git push origin feature/nueva-funcionalidad
- Abre un Pull Request

# Licencia
Este proyecto está bajo la licencia GPL-3.0. Consulta el archivo LICENSE para más detalles.