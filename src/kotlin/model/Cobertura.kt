package org.albertidam.insurancemanager.model

enum class Cobertura(val desc: String) {
    TERCEROS("Terceros"),
    TERCEROS_AMPLIADO("Terceros con ampliacion"),
    FRANQUICIA_200("Todo riesgos con franquicia de 200$"),
    FRANQUICIA_300("Todo riesgos con franquicia de 300$"),
    FRANQUICIA_400("Todo riesgos con franquicia de 400$"),
    FRANQUICIA_500("Todo riesgos con franquicia de 500$"),
    TODO_RIESGO("Todo riesgo");

    companion object {
        fun getCobertura(valor: String): Cobertura {
            return when (valor.uppercase()) {
                "TERCEROS" -> TERCEROS
                "TERCEROS_AMPLIADO" -> TERCEROS_AMPLIADO
                "FRANQUICIA_200" -> FRANQUICIA_200
                "FRANQUICIA_300" -> FRANQUICIA_300
                "FRANQUICIA_400" -> FRANQUICIA_400
                "FRANQUICIA_500" -> FRANQUICIA_500
                "TODO_RIESGO" -> TODO_RIESGO
                else -> TERCEROS
            }
        }
    }
}