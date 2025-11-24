package dev.ng5m.registry

import net.kyori.adventure.key.Key

object CatVariants {
    val TABBY = register("tabby")
    val BLACK = register("black")
    val RED = register("red")
    val SIAMESE = register("siamese")
    val BRITISH_SHORTHAIR = register("british_shorthair")
    val CALICO = register("calico")
    val PERSIAN = register("persian")
    val RAGDOLL = register("ragdoll")
    val WHITE = register("white")
    val JELLIE = register("jellie")
    val ALL_BLACK = register("all_black")

    private fun register(texture: String): ResourceKey<CatVariant> =
        Registries.CAT_VARIANT.register(Key.key(texture),
            CatVariant(Key.key("textures/entity/cat/$texture.png")))
}