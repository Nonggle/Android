package com.example.impl.utils

enum class Gender(val value: Int) {
    MALE(0), FEMALE(1);

    companion object {
        private val map = entries.associateBy(Gender::value)
        fun getByValue(value:Int): Gender? {
            return map[value]
        }
    }
}