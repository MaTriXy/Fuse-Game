package com.elkriefy.games.belote.data.types

class Game {
    var id: String? = null
    var groupA:ArrayList<Player> = ArrayList()
    var groupB:ArrayList<Player> = ArrayList()
    var groupAScore: Int = 0
    var groupBScore: Int = 0

    companion object {
        var TABLE = "games"
    }
}
