package com.example.coinmandi.feature_useronboard.domain.model

data class CoinMandiUser(
    val uid : String ,
    val realname : String = "Defaultrealname",
    val cm_username : String = "NoUserName",
    val email : String = "DefalutEmail",
    val profileurl : String  = "NoProfileUrl"
)
