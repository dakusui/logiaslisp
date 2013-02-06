["$let", [["$a", ["$quote", {"hello":"world", "everyone":["!","!"], "a":{"b":"c","d":"e"}}]]],
 ["$print", ["$aget", "$a", "hello"]],
 ["$print", ["$aget", "$a", "everyone"]],
 ["$print", ["$aget", "$a", "a"]],
]