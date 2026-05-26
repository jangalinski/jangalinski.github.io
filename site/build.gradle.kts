import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication
import kotlinx.html.link
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kobweb.application)
    alias(libs.plugins.kobwebx.markdown)
}

group = "io.github.jangalinski"
version = "1.0-SNAPSHOT"

rootProject.plugins.withType<YarnPlugin> {
    rootProject.extensions.getByType<YarnRootExtension>().lockFileDirectory =
        rootProject.file("gradle/kotlin-js-store")
}

kobweb {
    app {
        index {
            description.set("A developers logbook")
            head.add {
                link(rel = "stylesheet", href = "/css/bootstrap.min.css")
                link(rel = "stylesheet", href = "/css/clean-blog.css")
                link(rel = "stylesheet", href = "/css/syntax.css")
                link(rel = "stylesheet", href = "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.2.0/css/font-awesome.min.css")
                link(rel = "stylesheet", href = "https://fonts.googleapis.com/css?family=Lora:400,700,400italic,700italic")
                link(rel = "stylesheet", href = "https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800")
            }
        }
    }

    markdown {
        defaultLayout.set(".site.components.layout.BlogLayout")
        process.set { markdownEntries ->
            fun firstValue(values: List<String>?) = values?.firstOrNull().orEmpty()

            val filteredEntries = markdownEntries
                .filterNot { entry ->
                    val route = entry.route
                    route == "/" || route == "/about" || route == "/links"
                }
                .sortedByDescending { entry ->
                    firstValue(entry.frontMatter["date"])
                }

            generateMarkdown("index.md", buildString {
                appendLine("---")
                appendLine("title: \"Jan Galinski\"")
                appendLine("description: \"A developers logbook\"")
                appendLine("header-img: \"/img/home-bg.jpg\"")
                appendLine("---")
                appendLine()
                appendLine("Welcome back. This is the revived version of the old GitHub Pages blog, now rendered with Kobweb.")
                appendLine()
                appendLine("## Posts")
                appendLine()
                filteredEntries.forEach { entry ->
                    val route = entry.route
                    val title = firstValue(entry.frontMatter["title"]).ifBlank { "Untitled" }
                    val subtitle = firstValue(entry.frontMatter["subtitle"])
                    append("* [")
                    append(title)
                    append("](")
                    append(route)
                    append(")")
                    if (!subtitle.isNullOrBlank()) {
                        append(" - ")
                        append(subtitle)
                    }
                    appendLine()
                }
                appendLine()
                appendLine("## More")
                appendLine()
                appendLine("* [About](/about)")
                appendLine("* [Links](/links)")
                appendLine("* [GitHub profile](https://github.com/jangalinski)")
            })
        }
    }
}

kotlin {
    configAsKobwebApplication("jangalinski")

    sourceSets {
        jsMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.html.core)
            implementation(libs.kobweb.core)
            implementation(libs.kobweb.silk)
            implementation(libs.silk.icons.fa)
            implementation(libs.kobwebx.markdown)
        }
    }
}
