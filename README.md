# ground

![](https://clojars.org/ground/latest-version.svg)

"The ground wire is an additional path for electrical current to return safely to the ground without danger to anyone in the event of a short circuit."

## Getting Started 

[![Build Status](https://travis-ci.org/rcullito/ground.svg?branch=master)](https://travis-ci.org/rcullito/ground)
[![Clojars Project](https://img.shields.io/clojars/v/ground.svg)](https://clojars.org/ground)

```clojure
(ns demo.core
  (:require [ground.core :refer :all]))
```

###  predicates

predicates after the symbol `n` pass through
the prior expression if true, and return nil for the entire form if false

```clojure
(n-> 6
    (n (> 5))
    (vector 7 8)) => [6 7 8]

(n-> 9
    (n (> 10))
    (vector 11 12))  => nil
```


```clojure
(n->> [1 2 3]
	(n (every? identity))
	(map inc)) => '(2 3 4)

(n->> [1 nil 3]
	(n (every? identity))
	(map inc)) => nil
```

### grounding

`ground->` and `ground->>` will suppress errors during threading, returning nil instead

```clojure
(ground->> 1
           dec
           (/ 100))

(ground->> 1
           dec
           (/ 100)) = nil
```	

