package org.albertidam.insurancemanager.data

import org.albertidam.insurancemanager.model.Seguro

open class RepoSegurosMem : IRepoSeguros {
    val seguros = mutableListOf<Seguro>()

    override fun agregar(seguro: Seguro): Boolean = seguros.add(seguro)

    override fun buscar(numPoliza: Int): Seguro? = seguros.find { numPoliza == it.numPoliza }

    override fun eliminar(seguro: Seguro): Boolean = seguros.remove(seguro)

    override fun eliminar(numPoliza: Int): Boolean = seguros.remove(seguros.find { numPoliza == it.numPoliza })

    override fun obtenerTodos(): List<Seguro> = seguros.sortedBy { it.numPoliza }

    override fun obtener(tipoSeguro: String): List<Seguro> = seguros.filter { it.tipoSeguro() == tipoSeguro }
}