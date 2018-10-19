package br.com.cwi.presente.views

interface LoginView {
    fun onLoginSucceeded()
    fun onLoginFailed()
    fun onNormalLoginFailed(reason: String?)
}

interface CreateNewClassView {
    fun onCreateNewClassSucceeded()
    fun onCreateNewClassFailed(reason: String?)
}
interface CreateNewAlunoView {
    fun onCreateNewAlunoSucceeded()
    fun onCreateNewAlunoFailed(reason: String?)
}