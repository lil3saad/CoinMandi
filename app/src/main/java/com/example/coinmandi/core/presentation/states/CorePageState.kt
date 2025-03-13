package com.example.coinmandi.core.presentation.states

sealed class CorePageState {
    object AuthenticationState : CorePageState()
    object HomePage : CorePageState()
    object ExplorePage : CorePageState()
    object AiBotPage : CorePageState()
    object ProfilePage : CorePageState()
}