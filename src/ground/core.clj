(ns ground.core)

(defn- n-thread
  [expr forms operator]
  (reduce (fn [acc x]
            ;; for backwards compatibility treat either n or n? as indicative of a predicate
            (cond (and (seq? x) (or (= 'n (first x)) (= 'n? (first x)))) `(if (~operator ~acc ~(second x))
                                                                            ~acc
                                                                            nil)
                  (and (seq? x) (= 'n! (first x)))                       `(do (~operator ~acc ~(second x))
                                                                              ~acc)
                  :else                                                  `(~operator ~acc ~x)))
          expr
          forms))

(defmacro n->
  "within -> threading, 
  use n! for side effects that do not alter the pass through value and n?
  for predicates that pass through a value if true or return nil for the entire form if false."
  [expr & forms]
  (n-thread expr forms 'some->))

(defmacro n->>
  "within ->> threading, 
  use n! for side effects that do not alter the pass through value and n?
  for predicates that pass through a value if true or return nil for the entire form if false."
  [expr & forms]
  (n-thread expr forms 'some->>))

(defn- try-catch-thread
  [expr forms operator]
  `(try (~operator ~expr
             ~@forms)
        (catch Exception e# nil)))

(defn- try-catch-thread-with-doall
  [expr forms operator]
  (let [[try threading catch] (try-catch-thread expr forms operator)]
  `(~try (doall ~threading) ~catch)))

(defmacro ground->
  "behaves like ->, except returns nil if exception is thrown"
  [expr & forms]
  (try-catch-thread expr forms '->))

(defmacro ground->>
  "behaves like ->>, except returns nil if exception is thrown"  
  [expr & forms]
  (cond
    (sequential? expr) (try-catch-thread-with-doall expr forms '->>) 
    :else              (try-catch-thread expr forms '->>)))


