plugins {
    id("dev.kikugie.stonecutter")
}
stonecutter active "1.21.1-neoforge"

stonecutter registerChiseled tasks.register("chiseledBuild", stonecutter.chiseled) { 
    group = "project"
    ofTask("build")
}

allprojects {
    repositories {
        mavenCentral()
        mavenLocal()
        maven("https://maven.neoforged.net/releases")
        maven("https://maven.fabricmc.net/")
        maven("https://www.cursemaven.com/") {
            content {
                includeGroup("curse.maven")
            }
        }
        maven("https://raw.githubusercontent.com/Fuzss/modresources/main/maven/")
        maven("https://maven.architectury.dev/") {
            content {
                includeGroup("dev.architectury")
            }
        }
        maven("https://maven.ftb.dev/releases/") {
            content {
                includeGroup("dev.ftb.mods")
            }
        }
    }
}
