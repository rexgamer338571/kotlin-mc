package dev.ng5m.player

enum class Hand {
    LEFT,
    RIGHT;

    enum class Relative {
        MAIN_HAND,
        OFF_HAND;
    }
}