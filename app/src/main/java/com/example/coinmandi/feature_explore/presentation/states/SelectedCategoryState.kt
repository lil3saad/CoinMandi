package com.example.coinmandi.feature_explore.presentation.states

enum class SelectedCategoryState(val categoryid : String , val catergoryname : String) {

    RealWorldAsset(categoryid= "real-world-assets-rwa" , catergoryname = "Rwa"),
    DeFi(categoryid = "decentralized-finance-defi" , catergoryname = "Defi"),
    Gaming( categoryid = "gaming", catergoryname = "Gaming"),
    AI(categoryid = "artificial-intelligence" , catergoryname = "Ai"),
    MemeCoins( categoryid = "solana-meme-coins" ,catergoryname = "Meme" ),
    LayerOne(categoryid = "layer-1" , catergoryname = "Layer 1"),
    LayerTwo(categoryid = "layer-2" , catergoryname = "Layer 2")

}