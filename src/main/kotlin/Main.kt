package org.albertidam.insurancemanager

import org.albertidam.insurancemanager.app.ControlAcceso
import org.albertidam.insurancemanager.ui.Consola
import org.albertidam.insurancemanager.utils.Ficheros
import org.albertidam.insurancemanager.utils.Seguridad

fun Double.redondearDosDecimales(): Double = String.format("%.2f", this).replace(",", ".").toDouble()

fun main() {
    val rutaSeguros = "./resources/Seguros.txt"
    val rutaUsuarios = "./resources/Usuarios.txt"

    val ui = Consola()
    val gestorFicheros = Ficheros()
    val seguridad = Seguridad()

    ui.limpiarPantalla()

    val modoSimulacion = ui.preguntar("¿Desea usar ficheros? >> ")

    if (modoSimulacion) {

    } else {

    }
}