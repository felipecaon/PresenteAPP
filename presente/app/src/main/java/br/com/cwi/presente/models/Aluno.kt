package br.com.cwi.presente.models

class Aluno(val nome: String, val email: String) {

    lateinit var uid: String
    var aula: String? = null
    var macAddress: String? = null

    constructor() : this("", "")
}
