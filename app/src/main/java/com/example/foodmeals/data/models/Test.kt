package com.example.foodmeals.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey


/*
* Created By mabrouk on 20/03/19
* KotilnApp
*/
@Entity
data class Test(@PrimaryKey(autoGenerate = true) val id: Int) {
}
