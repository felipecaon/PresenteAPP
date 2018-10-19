package br.com.cwi.presente.models

class Aula(val nome: String, val professor: Professor) {
    lateinit  var uid: String

    constructor() : this("", Professor())
}