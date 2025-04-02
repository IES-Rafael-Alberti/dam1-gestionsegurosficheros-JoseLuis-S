package org.albertidam.insurancemanager

fun Double.redondearDosDecimales(): Double = String.format("%.2f", this).replace(",", ".").toDouble()

fun main() {

}