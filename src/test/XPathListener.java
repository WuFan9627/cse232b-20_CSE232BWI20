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
	/**
	 * Enter a parse tree produced by the {@code XqAp}
	 * labeled alternative in {@link XPathParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterXqAp(XPathParser.XqApContext ctx);
	/**
	 * Exit a parse tree produced by the {@code XqAp}
	 * labeled alternative in {@link XPathParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitXqAp(XPathParser.XqApContext ctx);
	/**
	 * Enter a parse tree produced by the {@code XqConstructor}
	 * labeled alternative in {@link XPathParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterXqConstructor(XPathParser.XqConstructorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code XqConstructor}
	 * labeled alternative in {@link XPathParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitXqConstructor(XPathParser.XqConstructorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FLWR}
	 * labeled alternative in {@link XPathParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterFLWR(XPathParser.FLWRContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FLWR}
	 * labeled alternative in {@link XPathParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitFLWR(XPathParser.FLWRContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Variable}
	 * labeled alternative in {@link XPathParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterVariable(XPathParser.VariableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Variable}
	 * labeled alternative in {@link XPathParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitVariable(XPathParser.VariableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code StringConst}
	 * labeled alternative in {@link XPathParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterStringConst(XPathParser.StringConstContext ctx);
	/**
	 * Exit a parse tree produced by the {@code StringConst}
	 * labeled alternative in {@link XPathParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitStringConst(XPathParser.StringConstContext ctx);
	/**
	 * Enter a parse tree produced by the {@code XqJoin}
	 * labeled alternative in {@link XPathParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterXqJoin(XPathParser.XqJoinContext ctx);
	/**
	 * Exit a parse tree produced by the {@code XqJoin}
	 * labeled alternative in {@link XPathParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitXqJoin(XPathParser.XqJoinContext ctx);
	/**
	 * Enter a parse tree produced by the {@code XqRpDoubleSlash}
	 * labeled alternative in {@link XPathParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterXqRpDoubleSlash(XPathParser.XqRpDoubleSlashContext ctx);
	/**
	 * Exit a parse tree produced by the {@code XqRpDoubleSlash}
	 * labeled alternative in {@link XPathParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitXqRpDoubleSlash(XPathParser.XqRpDoubleSlashContext ctx);
	/**
	 * Enter a parse tree produced by the {@code XqwithP}
	 * labeled alternative in {@link XPathParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterXqwithP(XPathParser.XqwithPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code XqwithP}
	 * labeled alternative in {@link XPathParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitXqwithP(XPathParser.XqwithPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code XqRpSingleSlash}
	 * labeled alternative in {@link XPathParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterXqRpSingleSlash(XPathParser.XqRpSingleSlashContext ctx);
	/**
	 * Exit a parse tree produced by the {@code XqRpSingleSlash}
	 * labeled alternative in {@link XPathParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitXqRpSingleSlash(XPathParser.XqRpSingleSlashContext ctx);
	/**
	 * Enter a parse tree produced by the {@code XqLet}
	 * labeled alternative in {@link XPathParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterXqLet(XPathParser.XqLetContext ctx);
	/**
	 * Exit a parse tree produced by the {@code XqLet}
	 * labeled alternative in {@link XPathParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitXqLet(XPathParser.XqLetContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TwoXqConcat}
	 * labeled alternative in {@link XPathParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterTwoXqConcat(XPathParser.TwoXqConcatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TwoXqConcat}
	 * labeled alternative in {@link XPathParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitTwoXqConcat(XPathParser.TwoXqConcatContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPathParser#forClause}.
	 * @param ctx the parse tree
	 */
	void enterForClause(XPathParser.ForClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPathParser#forClause}.
	 * @param ctx the parse tree
	 */
	void exitForClause(XPathParser.ForClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPathParser#letClause}.
	 * @param ctx the parse tree
	 */
	void enterLetClause(XPathParser.LetClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPathParser#letClause}.
	 * @param ctx the parse tree
	 */
	void exitLetClause(XPathParser.LetClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPathParser#whereClause}.
	 * @param ctx the parse tree
	 */
	void enterWhereClause(XPathParser.WhereClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPathParser#whereClause}.
	 * @param ctx the parse tree
	 */
	void exitWhereClause(XPathParser.WhereClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPathParser#returnClause}.
	 * @param ctx the parse tree
	 */
	void enterReturnClause(XPathParser.ReturnClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPathParser#returnClause}.
	 * @param ctx the parse tree
	 */
	void exitReturnClause(XPathParser.ReturnClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPathParser#joinClause}.
	 * @param ctx the parse tree
	 */
	void enterJoinClause(XPathParser.JoinClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPathParser#joinClause}.
	 * @param ctx the parse tree
	 */
	void exitJoinClause(XPathParser.JoinClauseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code XqEqual}
	 * labeled alternative in {@link XPathParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterXqEqual(XPathParser.XqEqualContext ctx);
	/**
	 * Exit a parse tree produced by the {@code XqEqual}
	 * labeled alternative in {@link XPathParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitXqEqual(XPathParser.XqEqualContext ctx);
	/**
	 * Enter a parse tree produced by the {@code XqEmpty}
	 * labeled alternative in {@link XPathParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterXqEmpty(XPathParser.XqEmptyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code XqEmpty}
	 * labeled alternative in {@link XPathParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitXqEmpty(XPathParser.XqEmptyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code XqCondOr}
	 * labeled alternative in {@link XPathParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterXqCondOr(XPathParser.XqCondOrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code XqCondOr}
	 * labeled alternative in {@link XPathParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitXqCondOr(XPathParser.XqCondOrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code XqSome}
	 * labeled alternative in {@link XPathParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterXqSome(XPathParser.XqSomeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code XqSome}
	 * labeled alternative in {@link XPathParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitXqSome(XPathParser.XqSomeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code XqIs}
	 * labeled alternative in {@link XPathParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterXqIs(XPathParser.XqIsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code XqIs}
	 * labeled alternative in {@link XPathParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitXqIs(XPathParser.XqIsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code XqCondwithP}
	 * labeled alternative in {@link XPathParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterXqCondwithP(XPathParser.XqCondwithPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code XqCondwithP}
	 * labeled alternative in {@link XPathParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitXqCondwithP(XPathParser.XqCondwithPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code XqCondNot}
	 * labeled alternative in {@link XPathParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterXqCondNot(XPathParser.XqCondNotContext ctx);
	/**
	 * Exit a parse tree produced by the {@code XqCondNot}
	 * labeled alternative in {@link XPathParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitXqCondNot(XPathParser.XqCondNotContext ctx);
	/**
	 * Enter a parse tree produced by the {@code XqCondAnd}
	 * labeled alternative in {@link XPathParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterXqCondAnd(XPathParser.XqCondAndContext ctx);
	/**
	 * Exit a parse tree produced by the {@code XqCondAnd}
	 * labeled alternative in {@link XPathParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitXqCondAnd(XPathParser.XqCondAndContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPathParser#var}.
	 * @param ctx the parse tree
	 */
	void enterVar(XPathParser.VarContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPathParser#var}.
	 * @param ctx the parse tree
	 */
	void exitVar(XPathParser.VarContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPathParser#names}.
	 * @param ctx the parse tree
	 */
	void enterNames(XPathParser.NamesContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPathParser#names}.
	 * @param ctx the parse tree
	 */
	void exitNames(XPathParser.NamesContext ctx);
}