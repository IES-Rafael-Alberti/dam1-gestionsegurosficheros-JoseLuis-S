package org.albertidam.insurancemanager.model

enum class NivelRiesgo(val interes: Int) {
    BAJO(2),
    MEDIO(5),
    ALTO(10);

    companion object {
        fun getRiesgo(valor: String): NivelRiesgo {
            return when(valor.uppercase()) {
                "BAJO" -> BAJO
                "MEDIO" -> MEDIO
                "ALTO" -> ALTO
                else -> MEDIO
            }
        }
    }
}