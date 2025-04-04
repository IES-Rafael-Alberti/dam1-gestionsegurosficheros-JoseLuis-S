package org.albertidam.insurancemanager.app

import org.albertidam.insurancemanager.model.Perfil
import org.albertidam.insurancemanager.service.IServSeguros
import org.albertidam.insurancemanager.service.IServUsuarios
import org.albertidam.insurancemanager.ui.IEntradaSalida

/**
 * Clase encargada de gestionar el flujo de menús y opciones de la aplicación,
 * mostrando las acciones disponibles según el perfil del usuario autenticado.
 *
 * @property nombreUsuario Nombre del usuario que ha iniciado sesión.
 * @property perfilUsuario Perfil del usuario: admin, gestion o consulta.
 * @property ui Interfaz de usuario.
 * @property gestorUsuarios Servicio de operaciones sobre usuarios.
 * @property gestorSeguros Servicio de operaciones sobre seguros.
 */
class GestorMenu(
    private val nombreUsuario: String,
    private val perfilUsuario: Perfil,
    private val ui: IEntradaSalida,
    private val gestorUsuarios: IServUsuarios,
    private val gestorSeguros: IServSeguros
) {

    /**
     * Inicia un menú según el índice correspondiente al perfil actual.
     *
     * @param indice Índice del menú que se desea mostrar (0 = principal).
     */
    fun iniciarMenu(indice: Int = 0) {
        val (opciones, acciones) = ConfiguracionesApp.obtenerMenuYAcciones(perfilUsuario.toString(), indice)
        ejecutarMenu(opciones, acciones)
    }

    /**
     * Formatea el menú en forma numerada.
     */
    private fun formatearMenu(opciones: List<String>): String {
        var cadena = ""
        opciones.forEachIndexed { index, opcion ->
            cadena += "${index + 1}. $opcion\n"
        }
        return cadena
    }

    /**
     * Muestra el menú limpiando pantalla y mostrando las opciones numeradas.
     */
    private fun mostrarMenu(opciones: List<String>) {
        ui.limpiarPantalla()
        ui.mostrar(formatearMenu(opciones), salto = false)
    }

    /**
     * Ejecuta el menú interactivo.
     *
     * @param opciones Lista de opciones que se mostrarán al usuario.
     * @param ejecutar Mapa de funciones por número de opción.
     */
    private fun ejecutarMenu(opciones: List<String>, ejecutar: Map<Int, (GestorMenu) -> Boolean>) {
        do {
            mostrarMenu(opciones)
            val opcion = ui.pedirInfo("Elige opción > ").toIntOrNull()
            if (opcion != null && opcion in 1..opciones.size) {
                // Buscar en el mapa las acciones a ejecutar en la opción de menú seleccionada
                val accion = ejecutar[opcion]
                // Si la accion ejecutada del menú retorna true, debe salir del menú
                if (accion != null && accion(this)) return
            }
            else {
                ui.mostrarError("Opción no válida!")
            }
        } while (true)
    }

    /** Crea un nuevo usuario solicitando los datos necesarios al usuario */
    fun nuevoUsuario() {
        ui.mostrar("--- CREACION USUARIO ---")
        val perfil: Perfil = pedirPerfil()
        ui.mostrar("--- CREACION $perfil ---", false)
        val nombre: String = pedirNombre()
        if (gestorUsuarios.buscarUsuario(nombre) != null) {
            ui.mostrarError("El usuario $nombre ya existe.")
            return
        }
        ui.mostrar("--- CREACION $perfil ---", false)
        val clave: String = pedirClave()
        gestorUsuarios.agregarUsuario(nombre, clave, perfil)
    }

    private fun pedirPerfil(): Perfil {
        var perfil: String
        do {
            perfil = ui.pedirInfo("Perfil del usuario >> ").uppercase()
            ui.limpiarPantalla()
        } while (perfil != "GESTION" && perfil != "CONSULTA")
        return Perfil.getPerfil(perfil)
    }

    private fun pedirNombre(): String {
        var nombre: String
        do {
            nombre = ui.pedirInfo("Nombre usuario >>")
            ui.limpiarPantalla()
        } while (nombre == "")
        return nombre
    }

    private fun pedirClave(): String {
        var clave: String
        do {
            clave = ui.pedirInfoOculta("Clave >> ")
            ui.limpiarPantalla()
        } while (clave == "")
        return clave
    }

    /** Elimina un usuario si existe */
    fun eliminarUsuario() {
        ui.mostrar("--- ELIMINAR USUARIO ---", false)
        val nombre: String = pedirNombre()
        if (gestorUsuarios.buscarUsuario(nombre) == null) {
            ui.mostrarError("El usuario $nombre no existe.")
            return
        }
    }

    /** Cambia la contraseña del usuario actual */
    fun cambiarClaveUsuario() {
        ui.mostrar("--- CAMBIAR CLAVE ---")
        val clave = ui.pedirInfo("Nueva clave >> ")
        ui.limpiarPantalla()
        val usuario = gestorUsuarios.buscarUsuario(nombreUsuario)
        gestorUsuarios.cambiarClave(usuario!!, clave)
    }

    /**
     * Mostrar la lista de usuarios (Todos o filstrados por un perfil)
     */
    fun consultarUsuarios() {

    }

    /**
     * Solicita al usuario un DNI y verifica que tenga el formato correcto: 8 dígitos seguidos de una letra.
     *
     * @return El DNI introducido en mayúsculas.
     */
    private fun pedirDni() {
        TODO("Implementar este método")
    }

    /**
     * Solicita al usuario un importe positivo, usado para los seguros.
     *
     * @return El valor introducido como `Double` si es válido.
     */
    private fun pedirImporte() {
        TODO("Implementar este método")
    }

    /** Crea un nuevo seguro de hogar solicitando los datos al usuario */
    fun contratarSeguroHogar() {
        TODO("Implementar este método")
    }

    /** Crea un nuevo seguro de auto solicitando los datos al usuario */
    fun contratarSeguroAuto() {
        TODO("Implementar este método")
    }

    /** Crea un nuevo seguro de vida solicitando los datos al usuario */
    fun contratarSeguroVida() {
        TODO("Implementar este método")
    }

    /** Elimina un seguro si existe por su número de póliza */
    fun eliminarSeguro() {
        TODO("Implementar este método")
    }

    /** Muestra todos los seguros existentes */
    fun consultarSeguros() {
        TODO("Implementar este método")
    }

    /** Muestra todos los seguros de tipo hogar */
    fun consultarSegurosHogar() {
        TODO("Implementar este método")
    }

    /** Muestra todos los seguros de tipo auto */
    fun consultarSegurosAuto() {
        TODO("Implementar este método")
    }

    /** Muestra todos los seguros de tipo vida */
    fun consultarSegurosVida() {
        TODO("Implementar este método")
    }
}