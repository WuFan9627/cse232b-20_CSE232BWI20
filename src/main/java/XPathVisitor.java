// Generated from XPath.g4 by ANTLR 4.8
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link XPathParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface XPathVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link XPathParser#fileName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFileName(XPathParser.FileNameContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ApGOToChildren}
	 * labeled alternative in {@link XPathParser#ap}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitApGOToChildren(XPathParser.ApGOToChildrenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ApGoTODescent}
	 * labeled alternative in {@link XPathParser#ap}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitApGoTODescent(XPathParser.ApGoTODescentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AllChildren}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAllChildren(XPathParser.AllChildrenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TagName}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTagName(XPathParser.TagNameContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Parent}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParent(XPathParser.ParentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Attribute}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttribute(XPathParser.AttributeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RpWithP}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpWithP(XPathParser.RpWithPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RpGoToChildren}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpGoToChildren(XPathParser.RpGoToChildrenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RpWithFilter}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpWithFilter(XPathParser.RpWithFilterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RpGoToDescent}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpGoToDescent(XPathParser.RpGoToDescentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RpWithConcat}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpWithConcat(XPathParser.RpWithConcatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Text}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitText(XPathParser.TextContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Current}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCurrent(XPathParser.CurrentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FilAnd}
	 * labeled alternative in {@link XPathParser#filter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilAnd(XPathParser.FilAndContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FilEqual}
	 * labeled alternative in {@link XPathParser#filter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilEqual(XPathParser.FilEqualContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FilWithP}
	 * labeled alternative in {@link XPathParser#filter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilWithP(XPathParser.FilWithPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FilNot}
	 * labeled alternative in {@link XPathParser#filter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilNot(XPathParser.FilNotContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FilOr}
	 * labeled alternative in {@link XPathParser#filter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilOr(XPathParser.FilOrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FilIs}
	 * labeled alternative in {@link XPathParser#filter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilIs(XPathParser.FilIsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FilRp}
	 * labeled alternative in {@link XPathParser#filter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilRp(XPathParser.FilRpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code XqAp}
	 * labeled alternative in {@link XPathParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXqAp(XPathParser.XqApContext ctx);
	/**
	 * Visit a parse tree produced by the {@code XqConstructor}
	 * labeled alternative in {@link XPathParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXqConstructor(XPathParser.XqConstructorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FLWR}
	 * labeled alternative in {@link XPathParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFLWR(XPathParser.FLWRContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Variable}
	 * labeled alternative in {@link XPathParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable(XPathParser.VariableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code StringConst}
	 * labeled alternative in {@link XPathParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringConst(XPathParser.StringConstContext ctx);
	/**
	 * Visit a parse tree produced by the {@code XqJoin}
	 * labeled alternative in {@link XPathParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXqJoin(XPathParser.XqJoinContext ctx);
	/**
	 * Visit a parse tree produced by the {@code XqRpDoubleSlash}
	 * labeled alternative in {@link XPathParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXqRpDoubleSlash(XPathParser.XqRpDoubleSlashContext ctx);
	/**
	 * Visit a parse tree produced by the {@code XqwithP}
	 * labeled alternative in {@link XPathParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXqwithP(XPathParser.XqwithPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code XqRpSingleSlash}
	 * labeled alternative in {@link XPathParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXqRpSingleSlash(XPathParser.XqRpSingleSlashContext ctx);
	/**
	 * Visit a parse tree produced by the {@code XqLet}
	 * labeled alternative in {@link XPathParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXqLet(XPathParser.XqLetContext ctx);
	/**
	 * Visit a parse tree produced by the {@code TwoXqConcat}
	 * labeled alternative in {@link XPathParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTwoXqConcat(XPathParser.TwoXqConcatContext ctx);
	/**
	 * Visit a parse tree produced by {@link XPathParser#forClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForClause(XPathParser.ForClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link XPathParser#letClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLetClause(XPathParser.LetClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link XPathParser#whereClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhereClause(XPathParser.WhereClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link XPathParser#returnClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnClause(XPathParser.ReturnClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link XPathParser#joinClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJoinClause(XPathParser.JoinClauseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code XqEqual}
	 * labeled alternative in {@link XPathParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXqEqual(XPathParser.XqEqualContext ctx);
	/**
	 * Visit a parse tree produced by the {@code XqEmpty}
	 * labeled alternative in {@link XPathParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXqEmpty(XPathParser.XqEmptyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code XqCondOr}
	 * labeled alternative in {@link XPathParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXqCondOr(XPathParser.XqCondOrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code XqSome}
	 * labeled alternative in {@link XPathParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXqSome(XPathParser.XqSomeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code XqIs}
	 * labeled alternative in {@link XPathParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXqIs(XPathParser.XqIsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code XqCondwithP}
	 * labeled alternative in {@link XPathParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXqCondwithP(XPathParser.XqCondwithPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code XqCondNot}
	 * labeled alternative in {@link XPathParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXqCondNot(XPathParser.XqCondNotContext ctx);
	/**
	 * Visit a parse tree produced by the {@code XqCondAnd}
	 * labeled alternative in {@link XPathParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXqCondAnd(XPathParser.XqCondAndContext ctx);
	/**
	 * Visit a parse tree produced by {@link XPathParser#var}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar(XPathParser.VarContext ctx);
	/**
	 * Visit a parse tree produced by {@link XPathParser#names}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNames(XPathParser.NamesContext ctx);
}