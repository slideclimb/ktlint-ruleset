package nl.deltadak.ktlintruleset

import com.pinterest.ktlint.core.LintError
import com.pinterest.ktlint.test.format
import com.pinterest.ktlint.test.lint
import io.kotest.core.spec.style.StringSpec
import io.kotest.inspectors.forExactly
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe

class SpacesAroundKeywordTest : StringSpec() {
    init {
        "space after if" {
            val lintErrors = SpacesAroundKeywordRule().lint("""
                fun temp() {
                    if (true) println(true)
                }
            """.trimIndent())
            lintErrors.shouldBeEmpty()
        }

        "no space after if" {
            val lintErrors = SpacesAroundKeywordRule().lint("""
                fun temp() {
                    if(true) println(true)
                }
            """.trimIndent())
            lintErrors.forExactly(1) {
                it.shouldBe(LintError(2, 7, "keyword-spaces", "Missing space after \"if\""))
            }
        }

        "formatting if" {
            SpacesAroundKeywordRule().format("""
                fun temp() {
                    if(true) println(true)
                }
            """.trimIndent()) shouldBe """
                fun temp() {
                    if (true) println(true)
                }
            """.trimIndent()
        }

        "no space after get" {
            val lintErrors = SpacesAroundKeywordRule().lint("""
                class Dummy {
                    val dum: Int
                        get() = 3
                }
            """.trimIndent())
            lintErrors.shouldBeEmpty()
        }

        "space after get" {
            val lintErrors = SpacesAroundKeywordRule().lint("""
                class Dummy {
                    val dum: Int
                        get () = 3
                }
            """.trimIndent())
            lintErrors.forExactly(1) {
                it.shouldBe(LintError(3, 9, "keyword-spaces", "Unexpected space after \"get\""))
            }
        }

        "formatting get" {
            SpacesAroundKeywordRule().format("""
                class Dummy {
                    val dum: Int
                        get () = 3
                }
            """.trimIndent()) shouldBe """
                class Dummy {
                    val dum: Int
                        get() = 3
                }
            """.trimIndent()
        }
    }
}