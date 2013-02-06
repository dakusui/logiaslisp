["$let", 
    [["$y", 200]],
    ["$defvar", "$javatest", ["$new", "java.util.Date", ["java.lang.String", "1973/06/10"]]],
    ["$print", ["$invoke", ["$invoke", "$javatest", "toString"], "contains", ["java.lang.String","75"]]],
    ["$invoke", "$stdout", "println", "$javatest"]
]
