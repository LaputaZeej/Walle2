package com.bugull.android

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        println(addLambda)
        val addLambda1 = addLambda(22)(22)(33)(11)(222)(333).value
        println(addLambda1)
        jianLambda(10)(2)
    }
}

val addLambda = Add::operation
val jianLambda = Jian::operation
val chengLambda = Cheng::operation
val chuLambda = Chu::operation

interface M : Function1<Int, M> {
    var value :Int
    fun operation(value: Int): M
}

abstract class Op:M{
    override var value: Int = 0
    override fun invoke(p1: Int): M {
        Add.operation(p1)
        return this
    }
}

object Add : Op() {
    override fun operation(value: Int): M {
        this.value+=value
        println("operation ${this.value}")
        return this
    }
}

object Jian : Op() {
    override fun operation(value: Int): M {
        this.value-=value
        println("operation ${this.value}")
        return this
    }
}

object Cheng : Op() {
    override fun operation(value: Int): M {
        this.value*=value
        println("operation ${this.value}")
        return this
    }
}

object Chu : Op() {
    override fun operation(value: Int): M {
        this.value*=value
        println("operation ${this.value}")
        return this
    }
}