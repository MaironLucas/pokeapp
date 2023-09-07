package com.example.pokeapp.data.data_source.local.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Index
import io.realm.kotlin.types.annotations.PrimaryKey

class Pokemon : RealmObject {
    @PrimaryKey
    var id: Int = 0
    @Index
    var name: String = ""
    var image: String = ""
}