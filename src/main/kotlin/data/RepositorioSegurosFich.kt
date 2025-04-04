package org.albertidam.insurancemanager.data

import org.albertidam.insurancemanager.model.Seguro
import org.albertidam.insurancemanager.model.SeguroAuto
import org.albertidam.insurancemanager.model.SeguroHogar
import org.albertidam.insurancemanager.model.SeguroVida
import org.albertidam.insurancemanager.utils.IUtilFicheros

class RepositorioSegurosFich(private val rutaArchivo: String, private val fich: IUtilFicheros) : RepoSegurosMem(), ICargarSegurosIniciales {
    override fun agregar(seguro: Seguro): Boolean {
        if (!fich.agregarLinea(rutaArchivo, seguro.serializar())) return false
        return super.agregar(seguro)
    }

    override fun eliminar(seguro: Seguro): Boolean {
        val nuevaLista = seguros.filter { it != seguro }
        if (fich.escribirArchivo(rutaArchivo, nuevaLista)) {
            return super.eliminar(seguro)
        }
        return false
    }

    override fun eliminar(numPoliza: Int): Boolean {
        val seguro = buscar(numPoliza)
        if (seguro != null) {
            eliminar(seguro)
            return true
        } else return false
    }

    override fun cargarSeguros(mapa: Map<String, (List<String>) -> Seguro>): Boolean {
        val lineas = fich.leerArchivo(rutaArchivo)
        seguros.clear()

        for (linea in lineas) {
            val datos = linea.split(",").toMutableList()
            val tipoSeguro = datos.removeAt(0)
            val parametros = datos

            val crearSeguro = mapa[tipoSeguro]
            if (crearSeguro != null) {
                seguros.add(crearSeguro(parametros))
            } else {
                seguros.clear()
                return false
            }
        }

        actualizarContadores(seguros)
        return true
    }


    private fun actualizarContadores(seguros: List<Seguro>) {
        // Actualizar los contadores de polizas del companion object según el tipo de seguro
        val maxHogar = seguros.filter { it.tipoSeguro() == "SeguroHogar" }.maxOfOrNull { it.numPoliza }
        val maxAuto = seguros.filter { it.tipoSeguro() == "SeguroAuto" }.maxOfOrNull { it.numPoliza }
        val maxVida = seguros.filter { it.tipoSeguro() == "SeguroVida" }.maxOfOrNull { it.numPoliza }

        if (maxHogar != null) SeguroHogar.numPolizasHogar = maxHogar
        if (maxAuto != null) SeguroAuto.numPolizasAuto = maxAuto
        if (maxVida != null) SeguroVida.numPolizasVida = maxVida
    }
}