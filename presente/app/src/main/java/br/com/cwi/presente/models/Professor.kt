package br.com.cwi.presente.models

import java.io.Serializable

class Professor : Serializable {
    lateinit var uid: String
    lateinit var nome: String
    var email: String? = null
    lateinit var materia: String
    lateinit var foto: String
}