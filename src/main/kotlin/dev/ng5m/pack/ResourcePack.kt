package dev.ng5m.pack

import java.net.URL
import java.nio.file.Path
import java.util.UUID

open class ResourcePack constructor(
    val hash: String,
    val url: URL,
    val forced: Boolean,
    val uuid: UUID = UUID.randomUUID()
) {
    class Local(
        val localPath: Path,
        hash: String,
        url: URL,
        forced: Boolean,
        uuid: UUID = UUID.randomUUID()
    ) : ResourcePack(hash, url, forced, uuid)
}