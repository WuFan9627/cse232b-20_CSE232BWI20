// Generated from XPath.g4 by ANTLR 4.8
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link XPathParser}.
 */
public interface XPathListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link XPathParser#fileName}.
	 * @param ctx the parse tree
	 */
	void enterFileName(XPathParser.FileNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPathParser#fileName}.
	 * @param ctx the parse tree
	 */
	void exitFileName(XPathParser.FileNameContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ApGOToChildren}
	 * labeled alternative in {@link XPathParser#ap}.
	 * @param ctx the parse tree
	 */
	void enterApGOToChildren(XPathParser.ApGOToChildrenContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ApGOToChildren}
	 * labeled alternative in {@link XPathParser#ap}.
	 * @param ctx the parse tree
	 */
	void exitApGOToChildren(XPathParser.ApGOToChildrenContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ApGoTODescent}
	 * labeled alternative in {@link XPathParser#ap}.
	 * @param ctx the parse tree
	 */
	void enterApGoTODescent(XPathParser.ApGoTODescentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ApGoTODescent}
	 * labeled alternative in {@link XPathParser#ap}.
	 * @param ctx the parse tree
	 */
	void exitApGoTODescent(XPathParser.ApGoTODescentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AllChildren}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterAllChildren(XPathParser.AllChildrenContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AllChildren}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitAllChildren(XPathParser.AllChildrenContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TagName}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterTagName(XPathParser.TagNameContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TagName}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitTagName(XPathParser.TagNameContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Parent}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterParent(XPathParser.ParentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Parent}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitParent(XPathParser.ParentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Attribute}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterAttribute(XPathParser.AttributeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Attribute}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitAttribute(XPathParser.AttributeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RpWithP}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpWithP(XPathParser.RpWithPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RpWithP}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpWithP(XPathParser.RpWithPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RpGoToChildren}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpGoToChildren(XPathParser.RpGoToChildrenContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RpGoToChildren}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpGoToChildren(XPathParser.RpGoToChildrenContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RpWithFilter}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpWithFilter(XPathParser.RpWithFilterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RpWithFilter}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpWithFilter(XPathParser.RpWithFilterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RpGoToDescent}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpGoToDescent(XPathParser.RpGoToDescentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RpGoToDescent}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpGoToDescent(XPathParser.RpGoToDescentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RpWithConcat}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpWithConcat(XPathParser.RpWithConcatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RpWithConcat}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpWithConcat(XPathParser.RpWithConcatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Text}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterText(XPathParser.TextContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Text}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitText(XPathParser.TextContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Current}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterCurrent(XPathParser.CurrentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Current}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitCurrent(XPathParser.CurrentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FilAnd}
	 * labeled alternative in {@link XPathParser#filter}.
	 * @param ctx the parse tree
	 */
	void enterFilAnd(XPathParser.FilAndContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FilAnd}
	 * labeled alternative in {@link XPathParser#filter}.
	 * @param ctx the parse tree
	 */
	void exitFilAnd(XPathParser.FilAndContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FilEqual}
	 * labeled alternative in {@link XPathParser#filter}.
	 * @param ctx the parse tree
	 */
	void enterFilEqual(XPathParser.FilEqualContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FilEqual}
	 * labeled alternative in {@link XPathParser#filter}.
	 * @param ctx the parse tree
	 */
	void exitFilEqual(XPathParser.FilEqualContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FilWithP}
	 * labeled alternative in {@link XPathParser#filter}.
	 * @param ctx the parse tree
	 */
	void enterFilWithP(XPathParser.FilWithPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FilWithP}
	 * labeled alternative in {@link XPathParser#filter}.
	 * @param ctx the parse tree
	 */
	void exitFilWithP(XPathParser.FilWithPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FilNot}
	 * labeled alternative in {@link XPathParser#filter}.
	 * @param ctx the parse tree
	 */
	void enterFilNot(XPathParser.FilNotContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FilNot}
	 * labeled alternative in {@link XPathParser#filter}.
	 * @param ctx the parse tree
	 */
	void exitFilNot(XPathParser.FilNotContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FilOr}
	 * labeled alternative in {@link XPathParser#filter}.
	 * @param ctx the parse tree
	 */
	void enterFilOr(XPathParser.FilOrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FilOr}
	 * labeled alternative in {@link XPathParser#filter}.
	 * @param ctx the parse tree
	 */
	void exitFilOr(XPathParser.FilOrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FilIs}
	 * labeled alternative in {@link XPathParser#filter}.
	 * @param ctx the parse tree
	 */
	void enterFilIs(XPathParser.FilIsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FilIs}
	 * labeled alternative in {@link XPathParser#filter}.
	 * @param ctx the parse tree
	 */
	void exitFilIs(XPathParser.FilIsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FilRp}
	 * labeled alternative in {@link XPathParser#filter}.
	 * @param ctx the parse tree
	 */
	void enterFilRp(XPathParser.FilRpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FilRp}
	 * labeled alternative in {@link XPathParser#filter}.
	 * @param ctx the parse tree
	 */
	void exitFilRp(XPathParser.FilRpContext ctx);
}