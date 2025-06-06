package org.albertidam.insurancemanager.model

import org.albertidam.insurancemanager.redondearDosDecimales
import java.time.LocalDate

class SeguroVida : Seguro {
    private val fechaNac: LocalDate
    private val nivelRiesgo: NivelRiesgo
    private val indemnizacion: Double

    init {
        numPolizasVida++
    }

    companion object {
        var numPolizasVida = 800000

        fun crearSeguro(datos: List<String>): SeguroVida {
            require(datos.size == 6) { "Datos no validos para crear un seguro de vida" }
            val fecha = LocalDate.parse(datos[0])
            val riesgo = NivelRiesgo.getRiesgo(datos[1])
            val indemnizacion = datos[2].toDouble()
            val numPoliza = datos[3].toInt()
            val dniTitular = datos[4]
            val importe = datos[5].toDouble()
            return SeguroVida(fecha, riesgo, indemnizacion, numPoliza, dniTitular, importe)
        }
    }

    constructor(
        fechaNac: LocalDate,
        nivelRiesgo: NivelRiesgo,
        indemnizacion: Double,
        dniTitular: String,
        importe: Double
    ): super(numPoliza = numPolizasVida, dniTitular, importe) {
        this.fechaNac = fechaNac
        this.nivelRiesgo = nivelRiesgo
        this.indemnizacion = indemnizacion
    }

    private constructor(
        fechaNac: LocalDate,
        nivelRiesgo: NivelRiesgo,
        indemnizacion: Double,
        numPoliza: Int,
        dniTitular: String,
        importe: Double
    ): super(numPoliza, dniTitular, importe) {
        this.fechaNac = fechaNac
        this.nivelRiesgo = nivelRiesgo
        this.indemnizacion = indemnizacion
    }

    override fun calcularImporteAniosSiguiente(interes: Double): Double = importe * (1 + (interes + nivelRiesgo.interes) / 100)

    override fun serializar(separador: String): String {
        return super.serializar(separador) + "$separador$fechaNac$separador$nivelRiesgo$separador$indemnizacion"
    }

    override fun toString(): String {
        return super.toString().replace("Seguro", "SeguroVida").dropLast(1) + ", fechaNac=$fechaNac, nivelRiesgo=$nivelRiesgo, indemnizacion=${indemnizacion.redondearDosDecimales()})"
    }
}