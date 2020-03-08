grammar XPath;

fileName
	: NAME '.xml'
	;

ap
	: 'doc("' fileName '")''/' rp    # ApGOToChildren
    | 'doc("' fileName '")' '//' rp  # ApGoTODescent
    ;
rp
	: NAME                         # TagName
	| '*'                          # AllChildren
	| '.'                          # Current
	| '..'                         # Parent
	| 'text()'                     # Text
	| '@' NAME                     # Attribute
	| '(' rp ')'                   # RpWithP
	| rp '/' rp                    # RpGoToChildren
	| rp '//' rp                   # RpGoToDescent
	| rp '[' filter ']'            # RpWithFilter
	| rp ',' rp                    # RpWithConcat
	;

filter
	: rp                           # FilRp
	| rp '=' rp                    # FilEqual
	| rp 'eq' rp                   # FilEqual
	| rp '==' rp                   # FilIs
	| rp 'is' rp                   # FilIs
	| '(' filter ')'               # FilWithP
	| filter 'and' filter          # FilAnd
	| filter 'or' filter           # FilOr
	| 'not' filter                 # FilNot
	;


NAME: [a-zA-Z0-9_-]+;
WS : [ \t\r\n]+ -> skip;

xq
	: var													    # Variable
	| StringConstant											# StringConst
	| ap														# XqAp
	| '(' xq ')'												# XqwithP
	| xq ',' xq 											    # TwoXqConcat
	| xq '/' rp													# XqRpSingleSlash
	| xq '//' rp 												# XqRpDoubleSlash
	| '<' NAME '>' '{' xq '}' '<' '/' NAME '>'					# XqConstructor
	| forClause letClause? whereClause? returnClause    		# FLWR
	| letClause xq 												# XqLet
	| joinClause                                                # XqJoin
	;

forClause
	: 'for' var 'in' xq (',' var 'in' xq)*
	;

letClause
	: 'let' var ':=' xq (',' var ':=' xq)*
	;

whereClause
	: 'where' cond
	;

returnClause
	: 'return' xq
	;

joinClause
    : 'join' '(' xq ',' xq ',' attrNames ',' attrNames ')' ;

cond
	: xq '=' xq 											 # XqEqual
	| xq 'eq' xq 											 # XqEqual
	| xq '==' xq 											 # XqIs
	| xq 'is' xq 											 # XqIs
	| 'empty' '(' xq ')' 		 							 # XqEmpty
	| 'some' var 'in' xq (',' var 'in' xq)* 'satisfies' cond # XqSome
	| '(' cond ')' 											 # XqCondwithP
	| cond 'and' cond 										 # XqCondAnd
	| cond 'or' cond 										 # XqCondOr
	| 'not' cond 											 # XqCondNot
	;

var : '$' NAME;
attrNames: '[' NAME (',' NAME)* ']';
StringConstant: '"'+[a-zA-Z0-9,.!?; '"-]+'"';