package org.albertidam.insurancemanager.ui

import org.jline.reader.LineReaderBuilder
import org.jline.terminal.TerminalBuilder
import org.jline.reader.UserInterruptException
import org.jline.reader.EndOfFileException

class Consola : IEntradaSalida {

    override fun mostrar(msj: String, salto: Boolean, pausa: Boolean) {
        println(msj)
        if (salto) println()
        if (pausa) pausar()
    }

    override fun mostrarError(msj: String, pausa: Boolean) {
        if (!msj.startsWith("ERROR - ")) {
            println("ERROR - $msj")
        } else {
            println(msj)
        }
        if (pausa) pausar()
    }

    override fun pedirInfo(msj: String): String {
        if (msj.isNotEmpty()) mostrar(msj)
        return readln()
    }

    override fun pedirInfo(msj: String, error: String, debeCumplir: (String) -> Boolean): String {
        var input: String
        do {
            input = pedirInfo(msj)
            if (!debeCumplir(input)) {
                mostrarError(error)
            }
        } while (!debeCumplir(input))
        return input
    }

    override fun pedirDouble(prompt: String, error: String, errorConv: String, debeCumplir: (Double) -> Boolean): Double {
        var result: Double?
        do {
            val input = pedirInfo(prompt)
            result = input.replace(',', '.').toDoubleOrNull()
            if (result == null) {
                mostrarError(errorConv)
            } else if (!debeCumplir(result)) {
                mostrarError(error)
                result = null
            }
        } while (result == null)
        return result
    }

    override fun pedirEntero(prompt: String, error: String, errorConv: String, debeCumplir: (Int) -> Boolean): Int {
        var result: Int?
        do {
            val input = pedirInfo(prompt)
            result = input.toIntOrNull()
            if (result == null) {
                mostrarError(errorConv)
            } else if (!debeCumplir(result)) {
                mostrarError(error)
                result = null
            }
        } while (result == null)
        return result
    }

    override fun pedirInfoOculta(prompt: String): String {
        return try {
            val terminal = TerminalBuilder.builder()
                .dumb(true) // Para entornos no interactivos como IDEs
                .build()

            val reader = LineReaderBuilder.builder()
                .terminal(terminal)
                .build()

            reader.readLine(prompt, '*') // Oculta la contraseña con '*'
        } catch (e: UserInterruptException) {
            mostrarError("Entrada cancelada por el usuario (Ctrl + C).", pausa = false)
            ""
        } catch (e: EndOfFileException) {
            mostrarError("Se alcanzó el final del archivo (EOF ó Ctrl+D).", pausa = false)
            ""
        } catch (e: Exception) {
            mostrarError("Problema al leer la contraseña: ${e.message}", pausa = false)
            ""
        }
    }

    override fun pausar(msj: String) {
        pedirInfo(msj)
    }

    override fun limpiarPantalla(numSaltos: Int) {
        if (System.console() != null) {
            println("\u001b[H\u001b[2J")
            System.out.flush()
        } else {
            repeat(numSaltos) { println() }
        }
    }

    override fun preguntar(mensaje: String): Boolean {
        var respuesta: String
        do {
            mostrar(mensaje)
            respuesta = readLine()?.trim()?.lowercase() ?: ""
        } while (respuesta != "s" && respuesta != "n" && respuesta != "si" && respuesta != "no")
        return respuesta == "s" || respuesta == "si"
    }

    override fun <T> mostrarLista(lista: List<T>) {
        lista.forEachIndexed { index, opcion ->
            println("${index + 1}. $opcion\n")
        }
    }
}