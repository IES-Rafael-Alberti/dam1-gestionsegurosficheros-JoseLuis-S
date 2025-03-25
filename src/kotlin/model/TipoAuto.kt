package org.albertidam.insurancemanager.model

enum class TipoAuto {
    COCHE, MOTO, CAMION;

    companion object {
        fun getAuto(valor: String): TipoAuto {
            return when(valor.uppercase()) {
                "COCHE" -> COCHE
                "MOTO" -> MOTO
                "CAMION" -> CAMION
                else -> COCHE
            }
        }
    }
}