package com.example.plugins

import com.example.utils.mqtt.*
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureMqtt() {
    // Example topic
    val microbit: Topic = Topic("microbit")
    val microbitTopicSubscription: TopicSubscription = TopicSubscription(microbit, AtMostOnce)

    // Installs the plugin to the server so that you can use it, won't work otherwise
    install(Mqtt) {
        broker = "tcp://localhost:1883"
        autoConnect = true
        initialSubscriptions(microbitTopicSubscription)
    }

    // Allows to map function to different topics
    routing {
        topic("microbit", AtMostOnce) {
            val message = it.toString()
            if (message.matches(Regex("^telegraf serial=\\d+,intensity=\\d+$"))) {
                println("bon")
            }
        }
    }
}