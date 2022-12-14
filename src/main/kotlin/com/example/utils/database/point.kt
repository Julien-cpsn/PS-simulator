package com.example.utils.database

import org.jetbrains.exposed.sql.*

fun Table.point(name: String): Column<Array<Double>> = registerColumn(name, PointColumnType())

class PointColumnType: ColumnType() {

    override fun sqlType(): String = "POINT"

    override fun valueToDB(value: Any?): Any? {
        return if (value is Array<*> && value.size == 2 && value.isArrayOf<Double>()) {
            doubleArrayOf(value[0] as Double, value[1] as Double)
        } else {
            super.valueToDB(value)
        }
    }

    override fun valueFromDB(value: Any): Any {
        if (value is java.sql.Array) {
            return value.array
        }
        else if (value is Array<*> && value.isArrayOf<Double>() && value.size == 2) {
            return arrayOf(value[0] as Double, value[1] as Double)
        }
        else if (value is kotlin.DoubleArray && value.size == 2) {
            return arrayOf(value[0], value[1])
        }
        error("Point does not support for this database")
    }

    override fun notNullValueToDB(value: Any): Any {
        return if (value is Array<*> && value.size == 2 && value.isArrayOf<Double>()) {
            doubleArrayOf(value[0] as Double, value[1] as Double)
        } else {
            super.notNullValueToDB(value)
        }
    }
}

class AnyOp(val expr1: Expression<*>, val expr2: Expression<*>) : Op<Boolean>() {
    override fun toQueryBuilder(queryBuilder: QueryBuilder) {
        if (expr2 is OrOp) {
            queryBuilder.append("(").append(expr2).append(")")
        } else {
            queryBuilder.append(expr2)
        }
        queryBuilder.append(" = ANY (")
        if (expr1 is OrOp) {
            queryBuilder.append("(").append(expr1).append(")")
        } else {
            queryBuilder.append(expr1)
        }
        queryBuilder.append(")")
    }
}

class ContainsOp(expr1: Expression<*>, expr2: Expression<*>) : ComparisonOp(expr1, expr2, "@>")

infix fun<T, S> ExpressionWithColumnType<T>.any(t: S) : Op<Boolean> {
    if (t == null) {
        return IsNullOp(this)
    }
    return AnyOp(this, QueryParameter(t, columnType))
}

infix fun<T, S> ExpressionWithColumnType<T>.contains(arry: Array<in S>) : Op<Boolean> = ContainsOp(this, QueryParameter(arry, columnType))