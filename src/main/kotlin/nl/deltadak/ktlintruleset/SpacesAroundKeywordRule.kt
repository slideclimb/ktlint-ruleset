package nl.deltadak.ktlintruleset

import com.pinterest.ktlint.core.Rule
import com.pinterest.ktlint.core.ast.ElementType.CATCH_KEYWORD
import com.pinterest.ktlint.core.ast.ElementType.DO_KEYWORD
import com.pinterest.ktlint.core.ast.ElementType.ELSE_KEYWORD
import com.pinterest.ktlint.core.ast.ElementType.FINALLY_KEYWORD
import com.pinterest.ktlint.core.ast.ElementType.FOR_KEYWORD
import com.pinterest.ktlint.core.ast.ElementType.GET_KEYWORD
import com.pinterest.ktlint.core.ast.ElementType.IF_KEYWORD
import com.pinterest.ktlint.core.ast.ElementType.SET_KEYWORD
import com.pinterest.ktlint.core.ast.ElementType.TRY_KEYWORD
import com.pinterest.ktlint.core.ast.ElementType.WHEN_KEYWORD
import com.pinterest.ktlint.core.ast.ElementType.WHILE_KEYWORD
import com.pinterest.ktlint.core.ast.nextLeaf
import com.pinterest.ktlint.core.ast.upsertWhitespaceAfterMe
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement
import org.jetbrains.kotlin.kdoc.psi.impl.KDocName
import org.jetbrains.kotlin.psi.KtPropertyAccessor

/**
 * Rule that states that some keywords should be followed by a space, and some keywords should not.
 *
 * Taken from the original [SpacingAroundKeywordRule] from ktlint.
 */
class SpacesAroundKeywordRule : Rule("keyword-spaces") {

    private val tokenSet = setOf(
            FOR_KEYWORD, IF_KEYWORD, ELSE_KEYWORD, WHILE_KEYWORD, DO_KEYWORD,
            TRY_KEYWORD, CATCH_KEYWORD, FINALLY_KEYWORD, WHEN_KEYWORD
    )

    private val keywordsWithoutSpaces = setOf(GET_KEYWORD, SET_KEYWORD)

    override fun visit(node: ASTNode, autoCorrect: Boolean, emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit) {
        if (node is LeafPsiElement) {
            if (tokenSet.contains(node.elementType) && node.parent !is KDocName && node.nextLeaf() !is PsiWhiteSpace) {
                emit(node.startOffset + node.text.length, "Missing space after \"${node.text}\"", true)
                if (autoCorrect) {
                    node.upsertWhitespaceAfterMe(" ")
                }
            }
            else if (keywordsWithoutSpaces.contains(node.elementType) && node.nextLeaf() is PsiWhiteSpace) {
                val parent = node.parent
                val nextLeaf = node.nextLeaf()
                if (parent is KtPropertyAccessor && parent.hasBody() && nextLeaf != null) {
                    emit(node.startOffset, "Unexpected space after \"${node.text}\"", true)
                    if (autoCorrect) {
                        nextLeaf.treeParent.removeChild(nextLeaf)
                    }
                }
            }
        }
    }
}