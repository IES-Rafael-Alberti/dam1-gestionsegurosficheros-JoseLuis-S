package org.albertidam.insurancemanager.data

import org.albertidam.insurancemanager.model.Perfil
import org.albertidam.insurancemanager.model.Usuario

open class RepoUsuariosMem : IRepoUsuarios {
    private val usuarios = mutableListOf<Usuario>()

    override fun agregar(usuario: Usuario): Boolean {
        if (buscar(usuario.nombre) != null) return false
        return usuarios.add(usuario)
    }

    override fun buscar(nombreUsuario: String): Usuario? =
        usuarios.find { it.nombre == nombreUsuario }

    override fun eliminar(usuario: Usuario): Boolean = usuarios.remove(usuario)

    override fun eliminar(nombreUsuario: String): Boolean {
        val usuario = buscar(nombreUsuario)
        return if (usuario != null) eliminar(usuario) else false
    }

    override fun obtenerTodos(): List<Usuario> = usuarios

    override fun obtener(perfil: Perfil): List<Usuario> =
        usuarios.filter { it.perfil == perfil }

    override fun cambiarClave(usuario: Usuario, nuevaClave: String): Boolean {
        usuario.cambiarClave(nuevaClave)
        return true
    }
}
