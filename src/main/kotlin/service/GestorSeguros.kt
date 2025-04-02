package org.albertidam.insurancemanager.service

import org.albertidam.insurancemanager.data.IRepoSeguros
import org.albertidam.insurancemanager.model.*
import java.time.LocalDate

class GestorSeguros(val repoSeguros: IRepoSeguros) : IServSeguros {
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
        repoSeguros.agregar(seguroHogar)
        return true
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
        repoSeguros.agregar(seguroAuto)
        return true
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
        repoSeguros.agregar(seguroVida)
        return true
    }

    override fun eliminarSeguro(numPoliza: Int): Boolean {
        repoSeguros.eliminar(numPoliza)
        return true
    }

    override fun consultarTodos(): List<Seguro> = repoSeguros.obtenerTodos()

    override fun consultarPorTipo(tipoSeguro: String): List<Seguro> = repoSeguros.obtener(tipoSeguro)
}