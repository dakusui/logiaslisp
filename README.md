logiaslisp
==========
LogiasLisp is a project to create a lisp processor whose syntax is based on JSON(JavaScript Object Notation).

It is implemented in Java.

You can run LogiasLisp by doing like
~~~~
$ java -jar logias-1.0-SNAPSHOT-with-dependencies.jar test.js
~~~~

Code examples are below,
#Example 1

```javascript
[
    "$do",
    [
        ["$i", 0, ["$+", "$i", 1]]
    ],
    [
        ["$>", "$i", 5], "DONE!"
    ],
    ["$print", "hello, world"],
    ["$print", ["$*", "$i", "$i"]],
    ["$print", ["$/", 10, ["$-", "$i", "0.5"]]]
]
```


#Example 2

```javascript
    ["$let", 
        [["$y", 200]],
        ["$defvar", "$testfunc", ["$lambda",["$x"], ["$print", ["$+", "$x", "$y"]]]],
        ["$testfunc", 100]
    ]
```
## Copyright and license

Copyright 2013 Hiroshi Ukai.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this work except in compliance with the License.
You may obtain a copy of the License in the LICENSE file, or at:

  [http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

