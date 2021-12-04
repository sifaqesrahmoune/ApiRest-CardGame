package com.app.model.dao

class MyCard(val name: String, val color: String) {
    override fun toString() : String {
        return "$name de $color";
    }
}