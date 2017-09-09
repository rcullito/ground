(ns ground.core)

(defmacro n->
  "within -> threading, include predicates after the symbol `n` to either forward 
  the prior expression if true, or return nil for the entire form if false.
  Assuming the prior expression resulted in an integer, a valid n predicate might be:
  `(n (> 3))`"
  [expr & forms]
  (reduce (fn [acc x]
            (if (= 'n (first x))
              `(if (some-> ~acc ~(second x))
                 ~acc
                 nil)
              `(some-> ~acc ~x)))
          expr
          forms))

(defmacro n->>
  "within ->> threading, include predicates after the symbol `n` to either forward 
  the prior expression if true, or return nil for the entire form if false.
  Assuming the prior expression resulted in a collection, a valid n predicate might be:
  `(n (every? identity))`"
  [expr & forms]
  (reduce (fn [acc x]
            (if (= 'n (first x))
              `(if (some->> ~acc ~(second x))
                 ~acc
                 nil)
              `(some->> ~acc ~x)))
          expr
          forms))

(defn try-catch-thread
  [expr forms operator]
  `(try (~operator ~expr
             ~@forms)
        (catch Exception e# nil)))

;; need to figure out how to wrap this from previous fn
(defn try-catch-thread-with-doall
  [expr forms operator]
  `(try (doall (~operator ~expr
          ~@forms))
        (catch Exception e# nil)))

(defmacro ground->
  "behaves like ->, except returns nil if exception is thrown"
  [expr & forms]
  (try-catch-thread expr forms '->))

(defmacro ground->>
  "behaves like ->>, except returns nil if exception is thrown"  
  [expr & forms]
  (cond
    (sequential? expr) (try-catch-thread-with-doall expr forms '->>) 
    :else (try-catch-thread expr forms '->>)))


