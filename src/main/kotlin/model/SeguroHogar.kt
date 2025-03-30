package org.albertidam.insurancemanager.model

import org.albertidam.insurancemanager.redondearDosDecimales
import java.time.LocalDate

class SeguroHogar : Seguro {
    private val metrosCuadrados: Int
    private val valorContenido: Double
    private val direccion: String
    private val anioConstruccion: Int

    init {
        numPolizasHogar++
    }

    companion object {
        private var numPolizasHogar = 100000
        const val PORCENTAJE_INCREMENTO_ANIOS = 0.02
        const val CICLO_ANIOS_INCREMENTO = 5

        fun crearSeguroHogar(datos: List<String>): SeguroHogar {
            require(datos.size == 6) { "Datos no validos para crear un seguro de hogar" }
            val metros = datos[0].toInt()
            val valor = datos[1].toDouble()
            val direccion = datos[2]
            val anio = datos[3].toInt()
            val dniTitular = datos[4]
            val importe = datos[5].toDouble()
            return SeguroHogar(metros, valor, direccion, anio, dniTitular, importe)
        }
    }

    private constructor(
        metrosCuadrados: Int,
        valorContenido: Double,
        direccion: String,
        anioConstruccion: Int,
        dniTitular: String,
        importe: Double
        ): super(numPoliza = numPolizasHogar, dniTitular, importe) {
            this.metrosCuadrados = metrosCuadrados
            this.valorContenido = valorContenido
            this.direccion = direccion
            this.anioConstruccion = anioConstruccion
        }

    private constructor(
        metrosCuadrados: Int,
        valorContenido: Double,
        direccion: String,
        anioConstruccion: Int,
        numPoliza: Int,
        dniTitular: String,
        importe: Double
    ): super(numPoliza, dniTitular, importe) {
        this.metrosCuadrados = metrosCuadrados
        this.valorContenido = valorContenido
        this.direccion = direccion
        this.anioConstruccion = anioConstruccion
    }

    override fun calcularImporteAniosSiguiente(interes: Double): Double {
        val fechaActual = LocalDate.now().year
        val anios = fechaActual - anioConstruccion
        val incremento: Double = (anios / CICLO_ANIOS_INCREMENTO) * PORCENTAJE_INCREMENTO_ANIOS
        return importe * (1 + interes + incremento)
    }

    override fun serializar(separador: String): String {
        return super.serializar(separador) + "$separador$metrosCuadrados$separador$valorContenido$separador$direccion$separador$anioConstruccion"
    }

    override fun toString(): String {
        return super.toString().dropLast(1) +
                ", metrosCuadrados=$metrosCuadrados, valorContenido=${valorContenido.redondearDosDecimales()}, " +
                "direccion=\"$direccion\", anioConstruccion=$anioConstruccion)"
    }
}