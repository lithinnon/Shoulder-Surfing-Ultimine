plugins {
    id("dev.isxander.modstitch.base") version "0.5.12"
}

fun prop(name: String, consumer: (prop: String) -> Unit) {
    (findProperty(name) as? String?)
        ?.let(consumer)
}

val minecraft = property("deps.minecraft") as String;

modstitch {
    minecraftVersion = minecraft

    // Alternatively use stonecutter.eval if you have a lot of versions to target.
    // https://stonecutter.kikugie.dev/stonecutter/guide/setup#checking-versions
    javaTarget = when (minecraft) {
        "1.20.1" -> 17
        "1.21.1" -> 21
        else -> throw IllegalArgumentException("Please store the java version for ${property("deps.minecraft")} in build.gradle.kts!")
    }

    // If parchment doesnt exist for a version yet you can safely
    // omit the "deps.parchment" property from your versioned gradle.properties
    parchment {
        prop("deps.parchment") { mappingsVersion = it }
    }

    // This metadata is used to fill out the information inside
    // the metadata files found in the templates folder.
    metadata {
        modId = "shouldersurfingultimine"
        modName = "Shoulder Surfing Ultimine"
        modVersion = "1.0.0"
        modGroup = "io.github.lithinnon"
        modAuthor = "lithinnon"

        fun <K, V> MapProperty<K, V>.populate(block: MapProperty<K, V>.() -> Unit) {
            block()
        }

        replacementProperties.populate {
            // You can put any other replacement properties/metadata here that
            // modstitch doesn't initially support. Some examples below.
            put("mod_issue_tracker", "https://github.com/modunion/modstitch/issues")
            put("mod_license", "MIT")
            put("pack_format", when (property("deps.minecraft")) {
                "1.20.1" -> 15
                "1.21.1" -> 48
                else -> throw IllegalArgumentException("Please store the resource pack version for ${property("deps.minecraft")} in build.gradle.kts! https://minecraft.wiki/w/Pack_format")
            }.toString())
        }
    }

    // Fabric Loom (Fabric)
    loom {
        // It's not recommended to store the Fabric Loader version in properties.
        // Make sure its up to date.
        fabricLoaderVersion = "0.16.14"

        // Configure loom like normal in this block.
        configureLoom {
        }
    }

    // ModDevGradle (NeoForge, Forge, Forgelike)
    moddevgradle {
        enable {
            prop("deps.forge") { forgeVersion = it }
            prop("deps.neoform") { neoFormVersion = it }
            prop("deps.neoforge") { neoForgeVersion = it }
            prop("deps.mcp") { mcpVersion = it }
        }

        // Configures client and server runs for MDG, it is not done by default
        defaultRuns()

        // This block configures the `neoforge` extension that MDG exposes by default,
        // you can configure MDG like normal from here
        configureNeoforge {
            runs.all {
                disableIdeRun()
            }
        }
    }

    mixin {
        // You do not need to specify mixins in any mods.json/toml file if this is set to
        // true, it will automatically be generated.
        addMixinsToModManifest = true

        configs.register("shouldersurfingultimine")

        // Most of the time you wont ever need loader specific mixins.
        // If you do, simply make the mixin file and add it like so for the respective loader:
        // if (isLoom) configs.register("shouldersurfingultimine-fabric")
        // if (isModDevGradleRegular) configs.register("shouldersurfingultimine-neoforge")
        // if (isModDevGradleLegacy) configs.register("shouldersurfingultimine-forge")
    }
}

// Stonecutter constants for mod loaders.
// See https://stonecutter.kikugie.dev/stonecutter/guide/comments#condition-constants
var constraint: String = name.split("-")[1]
stonecutter {
    consts(
        "fabric" to constraint.equals("fabric"),
        "neoforge" to constraint.equals("neoforge"),
        "forge" to constraint.equals("forge"),
        "vanilla" to constraint.equals("vanilla")
    )
}

// All dependencies should be specified through modstitch's proxy configuration.
// Wondering where the "repositories" block is? Go to "stonecutter.gradle.kts"
// If you want to create proxy configurations for more source sets, such as client source sets,
// use the modstitch.createProxyConfigurations(sourceSets["client"]) function.
dependencies {
    modstitch.loom {
        modstitchModRuntimeOnly("net.fabricmc.fabric-api:fabric-api:${property("deps.fabric_api")}")
        modstitchModRuntimeOnly("dev.architectury:architectury-fabric:${property("deps.architectury")}")
        modstitchModRuntimeOnly("fuzs.forgeconfigapiport:forgeconfigapiport-fabric:${property("deps.forgeconfigapiport")}")
        modstitchModRuntimeOnly("dev.ftb.mods:ftb-library-fabric:${property("deps.ftblibrary")}") { isTransitive = false }
        modstitchModImplementation("dev.ftb.mods:ftb-ultimine-fabric:${property("deps.ftbultimine")}")
    }

    modstitch.moddevgradle {
        modstitchModRuntimeOnly("dev.architectury:architectury-neoforge:${property("deps.architectury")}")
        modstitchModRuntimeOnly("dev.ftb.mods:ftb-library-neoforge:${property("deps.ftblibrary")}")
        modstitchModImplementation("dev.ftb.mods:ftb-ultimine-neoforge:${property("deps.ftbultimine")}")
    }

    // Anything else in the dependencies block will be used for all platforms.
    modstitchModImplementation("curse.maven:shoulder-surfing-reloaded-243190:${property("deps.shouldersurfing")}")
}
