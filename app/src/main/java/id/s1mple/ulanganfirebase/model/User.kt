package id.s1mple.ulanganfirebase.model

import com.google.firebase.Timestamp

data class User(
    var name: String,
    var username: String,
    var password: String,
    var created: Timestamp = Timestamp.now()
)
