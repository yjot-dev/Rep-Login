package com.yjotdev.login.domain.repository

/**
 * Define el contrato para la gestión de Strings.
 * Esta interfaz pertenece a la capa de Dominio y actúa como un puerto en
 * la Arquitectura Hexagonal.
 **/
interface StringRepository {
    /**
     * Obtiene una cadena de texto a partir de un recurso de cadena.
     * **/
    fun getString(resId: Int): String

    /**
     * Obtiene una cadena de texto a partir de un recurso de cadena con argumentos.
     * **/
    fun getString(resId: Int, vararg args: Any): String
}