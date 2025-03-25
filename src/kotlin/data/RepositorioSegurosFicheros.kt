package org.albertidam.insurancemanager.data

import

class RepositorioSegurosFicheros(
    private val archivo: String,
    private val mapaSeguros: Map<String, (List<String>) -> Seguro>
) {
    val mapaSeguros: Map<String, (List<String>) -> Seguro> = mapOf(
        "SeguroHogar" to { datos ->
            SeguroHogar(
                datos[0].toInt(), datos[1], datos[2].toInt(), datos[3].toDouble(),
                datos[4].toInt(), datos[5].toDouble(), datos[6]
            )
        },
        "SeguroAuto" to { datos ->
            SeguroAuto(
                datos[0].toInt(), datos[1], datos[2].toInt(), datos[3].toDouble(),
                datos[4], datos[5], datos[6], datos[7], datos[8].toBoolean(), datos[9].toInt()
            )
        },
        "SeguroVida" to { datos ->
            SeguroVida(
                datos[0].toInt(), datos[1], datos[2].toInt(), datos[3].toDouble(),
                datos[4], datos[5], datos[6].toDouble()
            )
        }
    )
    // Guardar un seguro en el fichero
    fun guardarSeguro(seguro: Seguro) {
        File(archivo).appendText(seguro.serializar() + "\n")
    }

    // Cargar todos los seguros del fichero
    fun cargarSeguros(): List<Seguro> {
        val seguros = mutableListOf<Seguro>()
        val file = File(archivo)

        if (!file.exists()) return seguros

        file.forEachLine { linea ->
            val datos = linea.split(";")
            val tipoSeguro = datos.last() // El último campo indica el tipo de seguro

            val seguro = mapaSeguros[tipoSeguro]?.invoke(datos.dropLast(1)) // Pasamos la lista de datos SIN el tipoSeguro
            if (seguro != null) {
                seguros.add(seguro)
            }
        }
        return seguros
    }
}