package org.albertidam.insurancemanager.model

class SeguroVida(
    val fechaNac: String,
    val nivelRiesgo: NivelRiesgo,
    val indemnizacion: Double,
    numPoliza: Int,
    dniTitular: String,
    importe: Double
): Seguro(numPoliza, dniTitular, importe) {
    companion object {

    }

    override fun calcularImporteAniosSiguiente(interes: Double): Double = obtenerImporte() * (1 + (interes + nivelRiesgo.interes) / 100)

    override fun tipoSeguro() {
        TODO("Not yet implemented")
    }

    override fun serializar(): String {
        TODO("Not yet implemented")
    }
}