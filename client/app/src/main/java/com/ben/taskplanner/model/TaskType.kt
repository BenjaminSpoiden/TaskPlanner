package com.ben.taskplanner.model

//enum class TaskType { PERSONAL, MEETING, WORK }

object TaskType {

    private val items: MutableMap<String, String> = hashMapOf(
            "Meeting" to "#C19AB7",
            "Personal" to "#228CDB",
            "Work" to "#0B7189",
            "Home" to "#9C95DC"
    )

    fun add(key: String, value: String) {
        items[key] = value
    }

    fun addAll(itemList: Map<String, String>) {
        items.putAll(itemList)
    }

    fun select(keys: List<String>): List<String> {
        val values = mutableListOf<String>()
        keys.forEach {
            values.add(items.getValue(it))
        }

        return values
    }
}