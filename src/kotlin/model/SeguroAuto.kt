package org.albertidam.insurancemanager.model

class SeguroAuto(
    val descripcion: String,
    val combustible: String,
    val tipoAuto: TipoAuto,
    val tipoCobertura: String,
    val asistenciaCarretera: Boolean,
    val numPartes: Int,
    numPoliza: Int,
    dniTitular: String,
    importe: Double
): Seguro(numPoliza, dniTitular, importe) {
    companion object {
        fun generarID(): Int {

        }
    }

    override fun calcularImporteAniosSiguiente(interes: Double): Double {
        return if (numPartes > 0) {
            ((((numPartes * 2) + interes) / 100) + 1) * obtenerImporte()
        } else {
            obtenerImporte() * (1 + interes / 100)
        }
    }

    override fun tipoSeguro() {
        TODO("Not yet implemented")
    }

    override fun serializar(): String {
        TODO("Not yet implemented")
    }
}