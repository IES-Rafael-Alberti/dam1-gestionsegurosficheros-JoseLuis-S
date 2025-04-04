package org.albertidam.insurancemanager.app

import org.albertidam.insurancemanager.model.Perfil
import org.albertidam.insurancemanager.service.IServSeguros
import org.albertidam.insurancemanager.service.IServUsuarios
import org.albertidam.insurancemanager.ui.IEntradaSalida
import org.albertidam.insurancemanager.model.TipoAuto
import org.albertidam.insurancemanager.model.Cobertura
import org.albertidam.insurancemanager.model.NivelRiesgo
import java.time.LocalDate

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
    private val perfilUsuario: String,
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
    private fun <T> formatearMenu(opciones: List<T>): String {
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
            } else {
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
        val respuestaUsuario = ui.preguntar("Consultar todo (s/si) o por perfil (n/no) >> ")
        when (respuestaUsuario) {
            true -> ui.mostrarLista(gestorUsuarios.consultarTodos())
            else -> consultarUsuariosPerfil()
        }
    }

    private fun consultarUsuariosPerfil() {
        val perfil = ui.pedirInfo("Tipo de perfil para ver (por defecto Consulta) >> ")
        ui.mostrarLista(gestorUsuarios.consultarPorPerfil(Perfil.getPerfil(perfil)))
    }

    /**
     * Solicita al usuario un DNI y verifica que tenga el formato correcto: 8 dígitos seguidos de una letra.
     *
     * @return El DNI introducido en mayúsculas.
     */
    private fun pedirDni(): String {
        var dni = ""
        do {
            try {
                dni = ui.pedirInfo("DNI >> ", "DNI incorrecto, debe tener 8 dígitos y una letra.") {
                    it.length >= 9 && it[8].isLetter()
                }
            } catch (e: IllegalArgumentException) {
                ui.mostrarError(e.toString())
            } catch (e: Exception) {
                ui.mostrarError(e.toString())
            }
        } while (dni == "")
        return dni.uppercase()
    }

    /**
     * Solicita al usuario un importe positivo, usado para los seguros.
     *
     * @return El valor introducido como `Double` si es válido.
     */
    private fun pedirImporte(): Double {
        var importe = 0.0
        do {
            try {
                ui.limpiarPantalla()
                importe = ui.pedirDouble(
                    "Importe >> ",
                    "Introduzca un importe positivo",
                    "Introduce un número decimal"
                ) {
                    it > 0
                }
            } catch (e: IllegalArgumentException) {
                ui.mostrarError(e.toString())
            } catch (e: Exception) {
                ui.mostrarError(e.toString())
            }
        } while (importe <= 0)
        return importe
    }

    /** Crea un nuevo seguro de hogar solicitando los datos al usuario */
    fun contratarSeguroHogar() {
        var seguroCorrecto = false
        do {
            try {
                val dni = pedirDni()
                val importe = pedirImporte()
                val metrosCuadrados = ui.pedirEntero(
                    "Metros cuadrados >> ",
                    "El número de metros debe ser positivo",
                    "Debes introducir un número"
                ) {
                    it > 0
                }
                val valorContenido = ui.pedirDouble(
                    "Valor del contenido >> ",
                    "El valor debe ser positivo",
                    "Debes introducir un número válido"
                ) {
                    it > 0
                }
                val direccion = ui.pedirInfo("Direccion >> ")
                val anioConstruccion = ui.pedirEntero(
                    "Año de construccion >> ",
                    "El año debe ser positivo y menor que el año actual",
                    "Introduce un numero"
                ) {
                    it > 0
                    it < LocalDate.now().year
                }
                gestorSeguros.contratarSeguroHogar(
                    dni,
                    importe,
                    metrosCuadrados,
                    valorContenido,
                    direccion,
                    anioConstruccion
                )
                seguroCorrecto = true
            } catch (e: IllegalArgumentException) {
                ui.mostrarError(e.toString())
            } catch (e: Exception) {
                ui.mostrarError(e.toString())
            }
        } while (!seguroCorrecto)
    }

    /** Crea un nuevo seguro de auto solicitando los datos al usuario */
    fun contratarSeguroAuto() {
        var seguroCorrecto = false
        do {
            try {
                ui.limpiarPantalla()
                val dni = pedirDni()
                val importe = pedirImporte()
                val descripcion = ui.pedirInfo("Descripción del auto >> ")
                val combustible = ui.pedirInfo("Tipo de combustible >> ")
                val tipoAuto = ui.pedirInfo("Tipo de auto (COCHE/CAMION/MOTO) >> ", "El tipo de auto no existe") {
                    it.uppercase() in TipoAuto.entries.toString()
                }.uppercase()
                val cobertura = ui.pedirInfo("Cobertura >> ", "El tipo de cobertura no existe") {
                    it.uppercase() in Cobertura.entries.toString()
                }
                val asistenciaEnCarretera = ui.preguntar("¿Asistencia en carretera? (s/si/n/no) >> ")
                val numPartes = ui.pedirEntero(
                    "Numero de partes >> ",
                    "El numero de partes debe ser positivo",
                    "Introduce un número entero"
                ) {
                    it > 0
                }
                gestorSeguros.contratarSeguroAuto(
                    dni,
                    importe,
                    descripcion,
                    combustible,
                    TipoAuto.getAuto(tipoAuto),
                    Cobertura.getCobertura(cobertura),
                    asistenciaEnCarretera,
                    numPartes
                )
                seguroCorrecto = true
            } catch (e: IllegalArgumentException) {
                ui.mostrarError(e.toString())
            } catch (e: Exception) {
                ui.mostrarError(e.toString())
            }
        } while (!seguroCorrecto)
    }

    /** Crea un nuevo seguro de vida solicitando los datos al usuario */
    fun contratarSeguroVida() {
        var seguroCorrecto = false
        do {
            try {
                ui.limpiarPantalla()
                val dni = pedirDni()
                val importe = pedirImporte()
                val fechaNacimiento = ui.pedirInfo("Fecha de nacimiento >> ")
                val fechaNacimientoDate = LocalDate.parse(fechaNacimiento)
                val nivelRiesgo = ui.pedirInfo("Nivel de riesgo (BAJO/MEDIO/ALTO) >> ", "Introduce un nivel de riesgo válido" ) {
                    it in NivelRiesgo.entries.toString()
                }
                val indemnizacion = ui.pedirDouble(
                    "Indemnización >> ",
                    "La indemnización debe ser positiva",
                    "Introduce un número decimal"
                ) {
                    it > 0
                }
                gestorSeguros.contratarSeguroVida(
                    dni,
                    importe,
                    fechaNacimientoDate,
                    NivelRiesgo.getRiesgo(nivelRiesgo),
                    indemnizacion
                )
                seguroCorrecto = true
            } catch (e: IllegalArgumentException) {
                ui.mostrarError(e.toString())
            } catch (e: Exception) {
                ui.mostrarError(e.toString())
            }
        } while (!seguroCorrecto)
    }

    /** Elimina un seguro si existe por su número de póliza */
    fun eliminarSeguro() {
        var seguroCorrecto = false
        do {
            try {
                ui.limpiarPantalla()
                val numPoliza = ui.pedirEntero(
                    "Numero poliza a eliminar >> ",
                    "Introduce un número positivo",
                    "El número de póliza debe ser número entero"
                ) {
                    it > 0
                }
                gestorSeguros.eliminarSeguro(numPoliza)
                seguroCorrecto = true

            } catch (e: IllegalArgumentException) {
                ui.mostrarError(e.toString())
            }
        } while (!seguroCorrecto)
    }

    /** Muestra todos los seguros existentes */
    fun consultarSeguros() {
        ui.mostrar("--- SEGUROS ---")
        ui.mostrarLista(gestorSeguros.consultarTodos())
    }

    /** Muestra todos los seguros de tipo hogar */
    fun consultarSegurosHogar() {
        ui.mostrar("--- SEGUROS DE HOGAR ---")
        ui.mostrarLista(gestorSeguros.consultarPorTipo("seguroHogar"))
    }

    /** Muestra todos los seguros de tipo auto */
    fun consultarSegurosAuto() {
        ui.mostrar("--- SEGUROS DE AUTO ---")
        ui.mostrarLista(gestorSeguros.consultarPorTipo("seguroAuto"))
    }

    /** Muestra todos los seguros de tipo vida */
    fun consultarSegurosVida() {
        ui.mostrar("--- SEGUROS DE VIDA ---")
        ui.mostrarLista(gestorSeguros.consultarPorTipo("seguroVida"))
    }
}