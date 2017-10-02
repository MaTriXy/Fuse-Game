package com.fuseteam.games.belote.data.types

class Player {
    var id: String? = null
    var email: String? = null
    var playing = false

    companion object {
        var TABLE = "players"
    }
}
