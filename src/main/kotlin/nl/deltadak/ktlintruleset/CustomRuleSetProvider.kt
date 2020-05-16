package nl.deltadak.ktlintruleset

import com.pinterest.ktlint.core.RuleSet
import com.pinterest.ktlint.core.RuleSetProvider

class CustomRuleSetProvider : RuleSetProvider {
    override fun get() = RuleSet("abby-ruleset",
            NewlineBeforeKeywordRule(),
            SpacesAroundKeywordRule()
    )
}