# LOGIN
Esta app es un prototipo de login, que cuenta con las funciones básicas: 
Crear usuario, Editar usuario, Eliminar usuario, Recuperar clave y obtener usuario.

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
- Al abrir la app, se muestra un Menu en la barra inferior con 3 opciones: Login, Registro 
y Recuperar clave donde se selecciona la opción deseada.
- En Login, el usuario debe ingresar su nombre o correo mas su clave, luego dar click en iniciar sesion.
- En Registro, el usuario debe ingresar su nombre, correo y clave, luego dar click en registrarse.
- En Recuperar clave, el usuario debe ingresar su correo y luego dar click en enviar codigo, luego debe
ir a su correo y copiar el codigo enviado y pegarlo en el campo correspondiente y poner su nueva clave,
para luego dar click en cambiar clave.
- Si inicia sesion vera una nueva vista llamada Usuario, hay podra actualizar sus campos personales,
asi como cerrar sesion y si gusta borrar su cuenta.

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