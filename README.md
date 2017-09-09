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

## Usage

###  predicates

predicates after the symbol `n` pass through
the prior expression if true, and return nil for the entire form if false

#### n->

```clojure
(n-> 6
    (n (> 5))
    (vector 7 8)) => [6 7 8]

(n-> 9
    (n (> 10))
    (vector 11 12)) => nil
```

#### n->>

```clojure
(n->> [1 2 3]
	(n (every? identity))
	(map inc)) => '(2 3 4)

(n->> [1 nil 3]
	(n (every? identity))
	(map inc)) => nil
```

### ignoring exceptions

ground will ignore exceptions during threading, returning nil instead

#### ground->

```clojure
(ground-> [1 2]
          second
          inc) => 3

(ground-> [1]
          second
          inc) => nil
```

#### ground->>

```clojure
(ground->> 51
           dec
           (/ 100)) => 2

(ground->> 1
           dec
           (/ 100)) => nil
```	

## References

* [clojure.core threading](https://clojure.org/guides/threading_macros)
* [swiss arrows](https://github.com/rplevy/swiss-arrows)
