package nl.utrechtjug.adoptjsr385.repl

class ExpressionTokerizer {

    fun tokenize(input: String): List<Token> {
        return input.split(" ").map { Token(it) }
    }
}