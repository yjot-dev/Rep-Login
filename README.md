# LOGIN
LOGIN es una aplicación móvil avanzada para la gestión integral de autenticación y operaciones de usuario, diseñada bajo los más altos estándares de seguridad y experiencia de usuario. La app implementa una arquitectura robusta (MVVM + Clean Architecture) y ofrece una interfaz intuitiva basada en XML, permitiendo a los usuarios acceder, gestionar y proteger su identidad digital de manera eficiente. Además de las funciones clásicas de inicio de sesión, registro y recuperación de clave, LOGIN incorpora nuevas vistas que enriquecen la experiencia: un Dashboard centralizado para acceder rápidamente a videollamadas (Zoom o Meet) y visualizar los últimos pagos, un módulo de Pagos integrado con PayPal, un historial detallado de todas las transacciones, un sistema de notificaciones automáticas por cada pago realizado y una sección de usuario para la gestión de información personal.

# Características principales
- 🪟 Interfaz clasica con XML
- 📊 Integración con ViewModel + StateFlow
- 🎨 Patrón de diseño arquitectónico con MVVM + Clean Architecture
- 💉 Inyección de dependencias con Hilt
- 💽 Base de datos remota con MySQL 
- 🧩 API RESTful con Node.JS
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
El flujo de uso de la aplicación está diseñado para ser intuitivo y completo, guiando al usuario a través de las siguientes vistas y funcionalidades principales:

- Login: Al abrir la aplicación, el usuario es recibido por una pantalla de inicio de sesión donde puede acceder de forma segura utilizando su nombre de usuario o correo electrónico y contraseña.
- Registro: Los nuevos usuarios pueden crear una cuenta proporcionando un nombre de usuario, correo electrónico y contraseña, accediendo así a todas las funcionalidades de la app.
- Recuperación de Clave: Si el usuario olvida su contraseña, puede recuperarla fácilmente mediante un proceso seguro que incluye la verificación por correo electrónico y la creación de una nueva clave.
- Dashboard: Tras iniciar sesión, el usuario accede a un panel principal que le permite abrir videollamadas (Zoom o Meet) y consultar rápidamente los últimos pagos realizados, centralizando las acciones más frecuentes.
- Pagos: Desde la sección de pagos, el usuario puede realizar transacciones de manera segura utilizando PayPal, facilitando la gestión financiera dentro de la app.
- Historial de Pagos: El usuario puede consultar un historial completo de todas las transacciones realizadas, con detalles claros y ordenados para un mejor control y seguimiento.
- Notificaciones: Por cada pago realizado, el sistema envía notificaciones automáticas, manteniendo al usuario informado en tiempo real sobre sus movimientos.
- Usuario: En la vista de usuario, se puede consultar y actualizar la información personal, cambiar la contraseña, cerrar sesión o eliminar la cuenta de manera definitiva.

En conjunto, LOGIN proporciona un ecosistema seguro, moderno y eficiente para la gestión de cuentas, pagos y comunicaciones, adaptado a las necesidades actuales de los usuarios y alineado con las mejores prácticas de desarrollo Android.

# Ver video Demo
[Ver en Youtube](https://youtu.be/o_KMGnOFWJg)

# Contribución
- Haz un fork del repositorio
- Crea una rama con tu feature: git checkout -b feature/nueva-funcionalidad
- Haz commit de tus cambios: git commit -m "Agrega nueva funcionalidad"
- Haz push a la rama: git push origin feature/nueva-funcionalidad
- Abre un Pull Request

# Licencia
Este proyecto está bajo la licencia GPL-3.0. Consulta el archivo LICENSE para más detalles.