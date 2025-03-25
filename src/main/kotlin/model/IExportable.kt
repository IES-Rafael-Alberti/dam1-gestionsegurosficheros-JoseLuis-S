package org.albertidam.insurancemanager.model

interface IExportable {
    fun serializar(separador: String = ";"): String
}