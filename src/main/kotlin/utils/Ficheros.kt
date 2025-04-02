package org.albertidam.insurancemanager.utils

import java.io.File
import org.albertidam.insurancemanager.model.IExportable
import org.albertidam.insurancemanager.ui.IEntradaSalida

class Ficheros(val ui: IEntradaSalida) : IUtilFicheros {

    override fun leerArchivo(ruta: String): List<String> {
        val lineas = mutableListOf<String>()
        try {
            File(ruta).bufferedReader().use { br ->
                br.forEachLine { lineas.add(it) }
            }
        } catch (e: Exception) {
            ui.mostrarError("No se pudo leer el archivo")
        }
        return lineas
    }

    override fun agregarLinea(ruta: String, linea: String): Boolean {
        return try {
            File(ruta).appendText(linea)
            true
        } catch (e: Exception) {
            ui.mostrarError("${e.message}")
            false
        }
    }

    override fun <T : IExportable> escribirArchivo(ruta: String, elementos: List<T>): Boolean {
        TODO("Not yet implemented")
    }

    override fun existeFichero(ruta: String): Boolean {
        return File(ruta).exists()
    }

    override fun existeDirectorio(ruta: String): Boolean {
        return File(ruta).isDirectory
    }
}