package io.github.jangalinski.site.components.layout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobwebx.markdown.markdown
import kotlinx.browser.document
import org.jetbrains.compose.web.dom.Article
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Footer
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Hr
import org.jetbrains.compose.web.dom.Li
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.Ul
import org.w3c.dom.Document

private const val SiteTitle = "Jan Galinski"
private const val SiteTagline = "A developers logbook"
private const val DefaultHeaderImage = "/img/home-bg.jpg"

private fun PageContext.frontMatterValue(key: String): String? =
    markdown?.frontMatter?.get(key)?.firstOrNull()?.takeIf { it.isNotBlank() }

private fun normalizeHeaderImage(value: String?): String =
    when {
        value.isNullOrBlank() -> DefaultHeaderImage
        value.startsWith("/") -> value
        else -> "/$value"
    }

private fun Document.setPageMetadata(title: String, description: String) {
    this.title = title
    (head!!.querySelector("meta[name='description']") ?: createElement("meta").also {
        it.setAttribute("name", "description")
        head!!.appendChild(it)
    }).setAttribute("content", description)
}

@Layout
@Composable
fun BlogLayout(ctx: PageContext, content: @Composable () -> Unit) {
    val pageTitle = ctx.frontMatterValue("title") ?: SiteTitle
    val pageDescription = ctx.frontMatterValue("description") ?: SiteTagline
    val headerImage = normalizeHeaderImage(ctx.frontMatterValue("header-img"))
    val subtitle = ctx.frontMatterValue("subtitle")
    val date = ctx.frontMatterValue("date")
    val isPost = !date.isNullOrBlank()
    val documentTitle = if (pageTitle == SiteTitle) SiteTitle else "$pageTitle - $SiteTitle"

    LaunchedEffect(documentTitle, pageDescription) {
        document.setPageMetadata(documentTitle, pageDescription)
    }

    Div(attrs = { attr("class", "site-shell") }) {
        org.jetbrains.compose.web.dom.Nav(attrs = { attr("class", "navbar navbar-default navbar-custom navbar-fixed-top") }) {
            Div(attrs = { attr("class", "container-fluid") }) {
                Div(attrs = { attr("class", "navbar-header page-scroll") }) {
                    Link("/") { Text(SiteTitle) }
                }

                Div(attrs = { attr("class", "collapse navbar-collapse") }) {
                    Ul(attrs = { attr("class", "nav navbar-nav navbar-right") }) {
                        Li { Link("/") { Text("Home") } }
                        Li { Link("/about") { Text("About") } }
                        Li { Link("/links") { Text("Links") } }
                        Li { Link("https://github.com/jangalinski") { Text("GitHub") } }
                    }
                }
            }
        }

        Div(attrs = {
            attr("class", "intro-header")
            attr("style", "background-image: url('$headerImage')")
        }) {
            Div(attrs = { attr("class", "container") }) {
                Div(attrs = { attr("class", "row") }) {
                    Div(attrs = { attr("class", "col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1") }) {
                        if (isPost) {
                            Div(attrs = { attr("class", "post-heading") }) {
                                H1 { Text(pageTitle) }
                                subtitle?.let {
                                    H2(attrs = { attr("class", "subheading") }) { Text(it) }
                                }
                                if (pageDescription.isNotBlank()) {
                                    P(attrs = { attr("class", "meta") }) { Text(pageDescription) }
                                }
                                P(attrs = { attr("class", "meta") }) { Text("Posted on $date") }
                            }
                        } else {
                            Div(attrs = { attr("class", "site-heading") }) {
                                H1 { Text(pageTitle) }
                                Hr(attrs = { attr("class", "small") })
                                Span(attrs = { attr("class", "subheading") }) { Text(pageDescription) }
                            }
                        }
                    }
                }
            }
        }

        if (isPost) {
            Article {
                Div(attrs = { attr("class", "container") }) {
                    Div(attrs = { attr("class", "row") }) {
                        Div(attrs = { attr("class", "col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1") }) {
                            content()
                            Hr()
                        }
                    }
                }
            }
        } else {
            Div(attrs = { attr("class", "container") }) {
                Div(attrs = { attr("class", "row") }) {
                    Div(attrs = { attr("class", "col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1") }) {
                        content()
                    }
                }
            }
        }

        Footer {
            Div(attrs = { attr("class", "container") }) {
                Div(attrs = { attr("class", "row") }) {
                    Div(attrs = { attr("class", "col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1") }) {
                        Ul(attrs = { attr("class", "list-inline text-center") }) {
                            Li { Link("/feed/rss.xml") { Text("RSS") } }
                            Li { Link("https://github.com/jangalinski") { Text("GitHub") } }
                            Li { Link("https://twitter.com/jangalinski") { Text("Twitter") } }
                        }
                        P(attrs = { attr("class", "copyright text-muted") }) {
                            Text("Copyright © $SiteTitle")
                        }
                    }
                }
            }
        }
    }
}
