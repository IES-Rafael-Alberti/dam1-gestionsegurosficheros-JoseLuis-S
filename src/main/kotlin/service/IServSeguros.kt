package org.albertidam.insurancemanager.service

import org.albertidam.insurancemanager.model.Cobertura
import org.albertidam.insurancemanager.model.NivelRiesgo
import org.albertidam.insurancemanager.model.Seguro
import org.albertidam.insurancemanager.model.TipoAuto
import java.time.LocalDate

interface IServSeguros {
    fun contratarSeguroHogar(
        dniTitular: String,
        importe: Double,
        metrosCuadrados: Int,
        valorContenido: Double,
        direccion: String,
        anioConstruccion: Int
    ): Boolean

    fun contratarSeguroAuto(
        dniTitular: String,
        importe: Double,
        descripcion: String,
        combustible: String,
        tipoAuto: TipoAuto,
        cobertura: Cobertura,
        asistenciaCarretera: Boolean,
        numPartes: Int
    ): Boolean

    fun contratarSeguroVida(
        dniTitular: String,
        importe: Double,
        fechaNacimiento: LocalDate,
        nivelRiesgo: NivelRiesgo,
        indemnizacion: Double
    ): Boolean

    fun eliminarSeguro(numPoliza: Int): Boolean

    fun consultarTodos(): List<Seguro>

    fun consultarPorTipo(tipoSeguro: String): List<Seguro>
}