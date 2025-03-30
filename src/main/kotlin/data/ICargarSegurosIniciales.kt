package org.albertidam.insurancemanager.data

import org.albertidam.insurancemanager.model.Seguro

interface ICargarSegurosIniciales {
    fun cargarSeguros(mapa: Map<String, (List<String>) -> Seguro>): Boolean
}