package org.albertidam.insurancemanager.data

import org.albertidam.insurancemanager.model.Seguro

interface IRepoSeguros {
    fun agregar(seguro: Seguro): Boolean
    fun buscar(numPoliza: Int): Seguro?
    fun eliminar(seguro: Seguro): Boolean
    fun eliminar(numPoliza: Int): Boolean
    fun obtenerTodos(): List<Seguro>
    fun obtener(tipoSeguro: String): List<Seguro>
}