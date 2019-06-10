package nl.utrechtjug.adoptjsr385.repl

import tech.units.indriya.quantity.Quantities
import tech.units.indriya.unit.Units
import javax.measure.Quantity

class OperationEvaluator {

    val units = Units.getInstance().getUnits().map { unit ->
        unit.getSymbol() to unit
    }.toMap()

    fun evaluate(start: ParseElement): Quantity<*>? {
        return when (start) {
            is Operation -> evaluateOperation(start)
            else -> throw IllegalStateException("need to start with an operation")
        }
    }

    private fun evaluateOperation(operation: Operation): Quantity<*>? {

        val q1 = if(  operation.left  is Operation ){
            evaluateOperation( operation.left)
        }else{
            operation.left.toQuantity()
        }

        val q2 = if(  operation.right  is Operation ){
            evaluateOperation( operation.right)
        }else{
            operation.right.toQuantity()
        }

        return when (operation.value) {
            Symbols.OPS_MULTIPLY -> q1?.multiply(q2)
            Symbols.OPS_DIVIDE -> q1?.divide(q2)
            else -> null
        }
    }


}

fun ParseElement.toQuantity(): Quantity<*> {
    return when {
        this is QuantityElement -> Quantities.getQuantity("${this.value} ${this.unit}")
        this is ValueElement -> Quantities.getQuantity("${this.value} one")
        else -> throw java.lang.IllegalStateException("Cannot convert ${this.javaClass} to a quantity")
    }


}
//fun QuantityElement.toQuantity2()  =  Quantities.getQuantity( this.value.toInt(), getUnit( this.unit) )
//
//fun getUnit(value:String)= Units.getInstance().getUnits().filter { unit -> unit.getSymbol() == value }.first()
