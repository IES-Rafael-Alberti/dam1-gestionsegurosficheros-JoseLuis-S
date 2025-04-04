package org.albertidam.insurancemanager

import org.albertidam.insurancemanager.app.CargadorInicial
import org.albertidam.insurancemanager.app.ConfiguracionesApp
import org.albertidam.insurancemanager.app.ControlAcceso
import org.albertidam.insurancemanager.app.GestorMenu
import org.albertidam.insurancemanager.data.*
import org.albertidam.insurancemanager.model.Perfil
import org.albertidam.insurancemanager.service.GestorSeguros
import org.albertidam.insurancemanager.service.GestorUsuarios
import org.albertidam.insurancemanager.ui.Consola
import org.albertidam.insurancemanager.utils.Ficheros
import org.albertidam.insurancemanager.utils.Seguridad

fun Double.redondearDosDecimales(): Double = String.format("%.2f", this).replace(",", ".").toDouble()

fun main() {
    val rutaSeguros = ConfiguracionesApp.RUTA_SEGUROS
    val rutaUsuarios = ConfiguracionesApp.RUTA_USUARIOS

    val ui = Consola()
    val gestorFicheros = Ficheros()
    val seguridad = Seguridad()

    ui.limpiarPantalla()

    ui.mostrar("--- GESTOR DE SEGUROS ---")
    val modoFicheros = ui.preguntar("¿Desea usar ficheros? >> ")

    ui.limpiarPantalla()

    val repoSeguros: IRepoSeguros
    val repoUsuarios: IRepoUsuarios

    if (!modoFicheros) {
        repoSeguros = RepoSegurosMem()
        repoUsuarios = RepoUsuariosMem()

        val gestorUsuarios = GestorUsuarios(repoUsuarios, seguridad)
        val gestorSeguros = GestorSeguros(repoSeguros)
        val controlAcceso = ControlAcceso(rutaUsuarios, gestorUsuarios, ui, gestorFicheros)

        if (controlAcceso.crearAdmin()) {
            val nombre: String = gestorUsuarios.consultarPorPerfil(Perfil.ADMIN)[0].nombre
            val gestorMenu = GestorMenu(nombre, "admin", ui, gestorUsuarios, gestorSeguros)
            gestorMenu.iniciarMenu()
        }

    } else  {
        repoSeguros = RepositorioSegurosFich(rutaSeguros, gestorFicheros)
        repoUsuarios = RepoUsuariosFich(rutaUsuarios, gestorFicheros)

        CargadorInicial(ui, repoUsuarios, repoSeguros).cargarUsuarios()
        CargadorInicial(ui, repoUsuarios, repoSeguros).cargarSeguros()

        val gestorUsuarios = GestorUsuarios(repoUsuarios, seguridad)
        val gestorSeguros = GestorSeguros(repoSeguros)
        val controlAcceso = ControlAcceso(rutaUsuarios, gestorUsuarios, ui, gestorFicheros)
        val (nombre, perfil) = controlAcceso.autenticar()

        if (nombre != null && perfil != null) {
            val gestorMenu = GestorMenu(nombre, perfil.toString().lowercase(), ui, gestorUsuarios, gestorSeguros)
            gestorMenu.iniciarMenu()
        }
    }
}