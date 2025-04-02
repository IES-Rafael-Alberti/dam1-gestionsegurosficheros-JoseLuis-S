package org.albertidam.insurancemanager.app

import org.albertidam.insurancemanager.data.ICargarSegurosIniciales
import org.albertidam.insurancemanager.data.ICargarUsuariosIniciales
import org.albertidam.insurancemanager.data.IRepoSeguros
import org.albertidam.insurancemanager.data.IRepoUsuarios
import org.albertidam.insurancemanager.ui.IEntradaSalida

/**
 * Clase encargada de cargar los datos iniciales de usuarios y seguros desde ficheros,
 * necesarios para el funcionamiento del sistema en modo persistente.
 *
 * @param ui Interfaz de entrada/salida para mostrar mensajes de error.
 * @param repoUsuarios Repositorio que permite cargar usuarios desde un fichero.
 * @param repoSeguros Repositorio que permite cargar seguros desde un fichero.
 */
class CargadorInicial(
    val ui: IEntradaSalida,
    val repoUsuarios: ICargarUsuariosIniciales,
    val repoSeguros: ICargarSegurosIniciales
) {

    /**
     * Carga los usuarios desde el archivo configurado en el repositorio.
     * Muestra errores si ocurre un problema en la lectura o conversión de datos.
     */
    fun cargarUsuarios() {
        try {
            repoUsuarios.cargarUsuarios()
        } catch (e: Exception) {

        }
    }

    /**
     * Carga los seguros desde el archivo configurado en el repositorio.
     * Utiliza el mapa de funciones de creación definido en la configuración de la aplicación
     * (ConfiguracionesApp.mapaCrearSeguros).
     * Muestra errores si ocurre un problema en la lectura o conversión de datos.
     */
    fun cargarSeguros() {

    }

}