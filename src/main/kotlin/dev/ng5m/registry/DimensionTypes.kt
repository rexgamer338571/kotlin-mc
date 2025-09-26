package dev.ng5m.registry

object DimensionTypes : RegistryInitializer<DimensionType>(Registries.DIMENSION_TYPE) {
    val THE_NETHER = add("the_nether")

    val THE_END = add("the_end")

    val OVERWORLD = add("overworld")

    val OVERWORLD_CAVES = add("overworld_caves")
}
    