package org.albertidam.insurancemanager.data

import org.albertidam.insurancemanager.model.Perfil
import org.albertidam.insurancemanager.model.Usuario

interface IRepoUsuarios {
    fun agregar(usuario: Usuario): Boolean
    fun buscar(nombreUsuario: String): Usuario?
    fun eliminar(usuario: Usuario): Boolean
    fun eliminar(nombreUsuario: String): Boolean
    fun obtenerTodos(): List<Usuario>
    fun obtener(perfil: Perfil): List<Usuario>
    fun cambiarClave(usuario: Usuario, nuevaClave: String): Boolean
}