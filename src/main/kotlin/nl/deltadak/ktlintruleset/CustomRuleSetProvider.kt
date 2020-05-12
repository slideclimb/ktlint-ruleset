package nl.deltadak.ktlintruleset

import com.pinterest.ktlint.core.RuleSet
import com.pinterest.ktlint.core.RuleSetProvider
import com.pinterest.ktlint.core.ast.ElementType.CATCH_KEYWORD
import com.pinterest.ktlint.core.ast.ElementType.ELSE_KEYWORD
import com.pinterest.ktlint.core.ast.ElementType.FINALLY_KEYWORD

class CustomRuleSetProvider : RuleSetProvider {
    override fun get() = RuleSet("texify-ruleset",
            NewlineBeforeKeyword("else", ELSE_KEYWORD),
            NewlineBeforeKeyword("catch", CATCH_KEYWORD),
            NewlineBeforeKeyword("finally", FINALLY_KEYWORD)
    )
}