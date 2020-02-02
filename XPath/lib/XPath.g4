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

