package org.albertidam.insurancemanager.model

class Usuario private constructor(
    val nombre: String,
    clave: String,
    val perfil: Perfil
): IExportable {
    var clave: String = clave
        private set

    companion object {
        fun crearUsuario(datos: List<String>): Usuario {
            return Usuario(datos[0], datos[1], Perfil.getPerfil(datos[2]))
        }
    }

    fun cambiarClave(nuevaClaveEncriptada: String) {
        clave = nuevaClaveEncriptada
    }

    override fun serializar(separador: String): String = "$nombre$separador$clave$separador$perfil$separador"

    override fun hashCode(): Int {
        return nombre.hashCode()
    }

    override fun equals(other: Any?): Boolean = if (this === other) true else if (other is Usuario) other.nombre == nombre else false
}