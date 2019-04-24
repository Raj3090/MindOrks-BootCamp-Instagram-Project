package com.raj.sharephoto.ui.main

enum class BottomMenuNavigation { HOME, ADD_PHOTOS, PROFILE }

interface BottomMenuNavigationListener {
    fun onMenuSelected(itemId: Int): Boolean
}