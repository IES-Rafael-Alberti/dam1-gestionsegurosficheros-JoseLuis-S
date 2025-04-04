package org.albertidam.insurancemanager.model

import org.albertidam.insurancemanager.redondearDosDecimales

abstract class Seguro(
    val numPoliza: Int,
    private val dniTitular: String,
    protected val importe: Double
): IExportable {
    companion object {
        fun validarDni(dni: String): Boolean {
            return dni.matches(Regex("^[0-9]{8}[A-Z]$"))
        }
    }

    abstract fun calcularImporteAniosSiguiente(interes: Double): Double

    fun tipoSeguro() = this::class.simpleName ?: "Desconocido"

    fun comprobarNumPoliza(numPoliza: Int): Boolean = numPoliza == this.numPoliza

    override fun serializar(separador: String): String = "${tipoSeguro()}$numPoliza$separador$dniTitular$separador$importe"

    override fun toString(): String = "Seguro(numPoliza=$numPoliza, dniTitular=$dniTitular, importe=${importe.redondearDosDecimales()})"

    override fun hashCode(): Int = numPoliza

    override fun equals(other: Any?): Boolean = if (this === other) true else if (other is Seguro) other.numPoliza == numPoliza else false
}