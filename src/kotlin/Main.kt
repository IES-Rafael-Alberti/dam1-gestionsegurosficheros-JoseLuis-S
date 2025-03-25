package org.albertidam.insurancemanager

fun Double.redondearDosDecimales(): Double = String.format("%.2f", this).replace(",", ".").toDouble()

fun main() {
    val archivo = "Seguros.txt"
    val repo = RepositorioSegurosFicheros(archivo, mapaSeguros)

    // Crear seguros y guardarlos
    val seguroHogar = SeguroHogar(1, "12345678A", 101, 500.0, 80, 150000.0, "Calle Mayor, 12")
    val seguroAuto = SeguroAuto(2, "98765432B", 102, 700.0, "Toyota Corolla Azul", "Gasolina", "Turismo", "Todo Riesgo", true, 1)

    repo.guardarSeguro(seguroHogar)
    repo.guardarSeguro(seguroAuto)

    // Cargar seguros desde el fichero
    val segurosCargados = repo.cargarSeguros()
    segurosCargados.forEach {
        println(it.tipoSeguro() + ": " + it.serializar())
    }
}