package nl.utrechtjug.adoptjsr385.repl

interface ParseElement

data class QuantityElement(val value: String, val unit: String) : ParseElement
data class ValueElement(val value: String) : ParseElement


data class Operation(val value: String,val left: ParseElement,  val right: ParseElement) : ParseElement



