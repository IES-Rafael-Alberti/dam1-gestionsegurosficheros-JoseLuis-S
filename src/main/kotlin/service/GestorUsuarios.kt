package org.albertidam.insurancemanager.service

import org.albertidam.insurancemanager.data.IRepoUsuarios
import org.albertidam.insurancemanager.model.Perfil
import org.albertidam.insurancemanager.model.Usuario
import org.albertidam.insurancemanager.utils.IUtilSeguridad

class GestorUsuarios(private val repoUsuario: IRepoUsuarios, private val seguridad: IUtilSeguridad) : IServUsuarios {
    override fun iniciarSesion(nombre: String, clave: String): Perfil? {
        val usuario: Usuario? = repoUsuario.buscar(nombre)
        val comprobacionClave: Boolean = seguridad.verificarClave(clave, seguridad.encriptarClave(clave))
        return if (usuario != null && comprobacionClave) usuario.perfil else null
    }

    override fun agregarUsuario(nombre: String, clave: String, perfil: Perfil): Boolean {
        var usuario: Usuario? = repoUsuario.buscar(nombre)
        return if (usuario == null) {
            usuario = Usuario.crearUsuario(listOf(nombre, clave, perfil.toString()))
            repoUsuario.agregar(usuario)
            true
        } else false
    }

    override fun eliminarUsuario(nombre: String): Boolean {
        val usuario: Usuario? = repoUsuario.buscar(nombre)
        return if (usuario != null) {
            repoUsuario.eliminar(usuario)
            true
        } else false
    }

    override fun cambiarClave(usuario: Usuario, nuevaClave: String): Boolean {
        val claveEncriptada = seguridad.encriptarClave(nuevaClave)
        usuario.cambiarClave(claveEncriptada)
        return true
    }

    override fun buscarUsuario(nombre: String): Usuario? = repoUsuario.buscar(nombre)

    override fun consultarTodos(): List<Usuario> = repoUsuario.obtenerTodos()

    override fun consultarPorPerfil(perfil: Perfil): List<Usuario> = repoUsuario.obtener(perfil)
}