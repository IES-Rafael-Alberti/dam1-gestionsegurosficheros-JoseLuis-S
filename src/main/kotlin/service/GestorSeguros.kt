package org.albertidam.insurancemanager.service

import org.albertidam.insurancemanager.data.IRepoSeguros
import org.albertidam.insurancemanager.model.*
import java.time.LocalDate

class GestorSeguros(private val repoSeguros: IRepoSeguros) : IServSeguros {
    override fun contratarSeguroHogar(
        dniTitular: String,
        importe: Double,
        metrosCuadrados: Int,
        valorContenido: Double,
        direccion: String,
        anioConstruccion: Int
    ): Boolean {
        val seguroHogar = SeguroHogar(
            metrosCuadrados,
            valorContenido,
            direccion,
            anioConstruccion,
            dniTitular,
            importe)
        return repoSeguros.agregar(seguroHogar)
    }

    override fun contratarSeguroAuto(
        dniTitular: String,
        importe: Double,
        descripcion: String,
        combustible: String,
        tipoAuto: TipoAuto,
        cobertura: Cobertura,
        asistenciaCarretera: Boolean,
        numPartes: Int
    ): Boolean {
        val seguroAuto = SeguroAuto(
            descripcion,
            combustible,
            tipoAuto,
            cobertura,
            asistenciaCarretera,
            numPartes,
            dniTitular,
            importe
        )
        return repoSeguros.agregar(seguroAuto)
    }

    override fun contratarSeguroVida(
        dniTitular: String,
        importe: Double,
        fechaNacimiento: LocalDate,
        nivelRiesgo: NivelRiesgo,
        indemnizacion: Double
    ): Boolean {
        val seguroVida = SeguroVida(
            fechaNacimiento,
            nivelRiesgo,
            indemnizacion,
            dniTitular,
            importe
        )
        return repoSeguros.agregar(seguroVida)
    }

    override fun eliminarSeguro(numPoliza: Int): Boolean {
        repoSeguros.eliminar(numPoliza)
        return true
    }

    override fun consultarTodos(): List<Seguro> = repoSeguros.obtenerTodos()

    override fun consultarPorTipo(tipoSeguro: String): List<Seguro> = repoSeguros.obtener(tipoSeguro)
}