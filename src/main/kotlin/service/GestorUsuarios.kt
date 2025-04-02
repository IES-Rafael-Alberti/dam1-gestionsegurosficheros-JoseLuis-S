package org.albertidam.insurancemanager.service

import org.albertidam.insurancemanager.data.IRepoUsuarios
import org.albertidam.insurancemanager.model.Perfil
import org.albertidam.insurancemanager.model.Usuario
import org.albertidam.insurancemanager.utils.IUtilSeguridad

class GestorUsuarios(val repoUsuario: IRepoUsuarios, val seguridad: IUtilSeguridad) : IServUsuarios, IUtilSeguridad {
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
        TODO("Not yet implemented")
    }

    override fun cambiarClave(usuario: Usuario, nuevaClave: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun buscarUsuario(nombre: String): Usuario? {
        TODO("Not yet implemented")
    }

    override fun consultarTodos(): List<Usuario> {
        TODO("Not yet implemented")
    }

    override fun consultarPorPerfil(perfil: Perfil): List<Usuario> {
        TODO("Not yet implemented")
    }

    override fun encriptarClave(clave: String, nivelSeguridad: Int): String {
        TODO("Not yet implemented")
    }

    override fun verificarClave(claveIngresada: String, hashAlmacenado: String): Boolean {
        TODO("Not yet implemented")
    }

}