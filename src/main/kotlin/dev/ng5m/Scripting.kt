package dev.ng5m

import dev.ng5m.api.KMc
import org.graalvm.polyglot.Context
import org.graalvm.polyglot.HostAccess
import java.util.LinkedList
import java.util.concurrent.Executors
import kotlin.concurrent.thread

object Scripting {

    object ScriptThread {
        internal val scripts = LinkedList<String>()
        private val thr = thread {
            val ctx = Context.newBuilder("js")
                .allowHostAccess(HostAccess.ALL)
                .allowHostClassLookup { true }
                .build()
                .apply {
                    getBindings("js").putMember("kmc", KMc)
                }

            while (true) {
                while (scripts.isNotEmpty()) {
                    ctx.eval("js", scripts.poll())
                }
            }
        }

    }

    fun runScript(js: String) {
        ScriptThread.scripts.add(js)
    }
}

fun main() {
    Scripting.runScript("""
var ChunkGenerator = Java.type("dev.ng5m.api.ChunkGenerator");
var ChunkGenerator_x = Java.extend(ChunkGenerator);
        
var server = kmc.createServer();

var world = server.createWorld("the_end", "script:test");
var flatGenerator = new ChunkGenerator_x({
    generate: function(chunk) {
        for (var x = 0; x < 16; x++) {
            for (var z = 0; z < 16; z++) {
                chunk.setBlock(x, 0, z, "dirt");
            }
        }
    }
});

world.setChunkGenerator(flatGenerator);

kmc.on("player.PlayerPreJoinEvent", function(event) {
    event.getPlayer().getConnection().close();
});

server.start(25565);
    """.trimIndent())
}