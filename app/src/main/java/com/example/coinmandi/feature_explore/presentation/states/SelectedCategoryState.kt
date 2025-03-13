package com.example.coinmandi.feature_explore.presentation.states

enum class SelectedCategoryState(val categoryid : String , val catergoryname : String) {

    RealWorldAsset(categoryid= "real-world-assets-rwa" , catergoryname = "RWA"),
    DeFi(categoryid = "decentralized-finance-defi" , catergoryname = "DEFI"),
    Gaming( categoryid = "gaming", catergoryname = "GAMING"),
    AI(categoryid = "artificial-intelligence" , catergoryname = "AI"),
    MemeCoins( categoryid = "solana-meme-coins" ,catergoryname = "MEME" ),
    LayerOne(categoryid = "layer-1" , catergoryname = "LAYER ONE"),
    LayerTwo(categoryid = "layer-2" , catergoryname = "LAYER TWO")

}