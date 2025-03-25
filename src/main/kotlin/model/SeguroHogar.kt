package org.albertidam.insurancemanager.model

class SeguroHogar : Seguro {
    private val metrosCuadrados: Int
    private val valorContenido: Double
    private val direccion: String

    init {
        numPolizasHogar++
    }

    companion object {
        private var numPolizasHogar = 100000
        const val PORCENTAJE_INCREMENTO_ANIOS = 0.02
        const val CICLO_ANIOS_INCREMENTO = 5

        fun crearSeguro(datos: List<String>): SeguroHogar {
            return SeguroHogar()
        }
    }

    constructor(
        metrosCuadrados: Int,
        valorContenido: Double,
        direccion: String,
        dniTitular: String,
        importe: Double
        ): super(numPoliza = numPolizasHogar, dniTitular, importe) {
            this.metrosCuadrados = metrosCuadrados
            this.valorContenido = valorContenido
            this.direccion = direccion
        }

    private constructor(
        metrosCuadrados: Int,
        valorContenido: Double,
        direccion: String,
        numPoliza: Int,
        dniTitular: String,
        importe: Double
    ): super(numPoliza, dniTitular, importe) {
        this.metrosCuadrados = metrosCuadrados
        this.valorContenido = valorContenido
        this.direccion = direccion
    }

    override fun calcularImporteAniosSiguiente(interes: Double): Double {
        val interesResidual =
        return importe * (1 + interes / 100) + interesResidual
    }

    override fun serializar(separador: String): String {
        TODO("Not yet implemented")
    }
}