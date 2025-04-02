package org.albertidam.insurancemanager.data

import org.albertidam.insurancemanager.model.Usuario
import org.albertidam.insurancemanager.utils.IUtilFicheros

class RepoUsuariosFich(private val rutaArchivo: String, private val fich: IUtilFicheros) : RepoUsuariosMem(), ICargarUsuariosIniciales {
    override fun agregar(usuario: Usuario): Boolean {
        if (buscar(usuario.nombre) != null) return false
        if (!fich.agregarLinea(rutaArchivo, usuario.serializar())) return false
        return super.agregar(usuario)
    }

    override fun eliminar(usuario: Usuario): Boolean {
        if (fich.escribirArchivo(rutaArchivo, usuarios.filter { it != usuario })) {
            return super.eliminar(usuario)
        }
        return false
    }

    override fun eliminar(nombreUsuario: String): Boolean {
        val usuario = buscar(nombreUsuario)
        if (usuario != null && fich.escribirArchivo(rutaArchivo, usuarios.filter { it != usuario })) {
            return super.eliminar(usuario)
        }
        return false
    }

    override fun cargarUsuarios(): Boolean {
        val lineas = fich.leerArchivo(rutaArchivo)

    }
}