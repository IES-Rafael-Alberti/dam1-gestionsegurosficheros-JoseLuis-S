package org.albertidam.insurancemanager.model

class SeguroAuto : Seguro {
    private val descripcion: String
    private val combustible: String
    private val tipoAuto: TipoAuto
    private val tipoCobertura: Cobertura
    private val asistenciaCarretera: Boolean
    private val numPartes: Int

    companion object {
        var numPolizasAuto = 400000
        private const val PORCENTAJE_INCREMENTO_PARTES = 2

        fun crearSeguroAuto(datos: List<String>): SeguroAuto {
            require(datos.size == 7) { "Datos no válidos para crear un seguro de auto" }
            val descripcion = datos[0]
            val combustible = datos[1]
            val tipoAuto = TipoAuto.getAuto(datos[2])
            val tipoCobertura = Cobertura.getCobertura(datos[3])
            val asistenciaCarretera = datos[4].toBooleanStrict()
            val numPartes = datos[5].toInt()
            val dniTitular = datos[6]
            val importe = datos[7].toDouble()
            return SeguroAuto(descripcion, combustible, tipoAuto, tipoCobertura, asistenciaCarretera, numPartes, dniTitular, importe)
        }
    }

    constructor(
        descripcion: String,
        combustible: String,
        tipoAuto: TipoAuto,
        tipoCobertura: Cobertura,
        asistenciaCarretera: Boolean,
        numPartes: Int,
        dniTitular: String,
        importe: Double
    ) : super(numPoliza = numPolizasAuto, dniTitular, importe) {
        this.descripcion = descripcion
        this.combustible = combustible
        this.tipoAuto = tipoAuto
        this.tipoCobertura = tipoCobertura
        this.asistenciaCarretera = asistenciaCarretera
        this.numPartes = numPartes
    }

    private constructor(
        descripcion: String,
        combustible: String,
        tipoAuto: TipoAuto,
        tipoCobertura: Cobertura,
        asistenciaCarretera: Boolean,
        numPartes: Int,
        numPoliza: Int,
        dniTitular: String,
        importe: Double
    ) : super(numPoliza, dniTitular, importe) {
        this.descripcion = descripcion
        this.combustible = combustible
        this.tipoAuto = tipoAuto
        this.tipoCobertura = tipoCobertura
        this.asistenciaCarretera = asistenciaCarretera
        this.numPartes = numPartes
    }

    override fun calcularImporteAniosSiguiente(interes: Double): Double {
        return if (numPartes > 0) {
            ((((numPartes * PORCENTAJE_INCREMENTO_PARTES) + interes) / 100) + 1) * importe
        } else {
            importe * (1 + interes / 100)
        }
    }

    override fun serializar(separador: String): String {
        return super.serializar(separador) + "$separador$descripcion$separador$combustible$separador" +
                "$tipoAuto$separador$tipoCobertura$separador$asistenciaCarretera$separador$numPartes"
    }
}