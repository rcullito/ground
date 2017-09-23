(ns ground.core)

(defn- valid-n-form?
  "returns true if form begins with a valid symbol defined within the macro"
  [valid-n-symbol form]
  (and (seq? form)
       (= valid-n-symbol (first form))))

(defn- intended-predicate
  "determine if the first symbol of the form is intended to signal that the subsequent
  form should be treated as a predicate"
  [form]
  ;; for backwards compatibility treat either n or n? as indicative of a predicate
  (or (valid-n-form? 'n? form)
      (valid-n-form? 'n form)))

(defn- intended-side-effect
  "determine if the first symbol of the form is intended to signal that the subsequent
  form should only produce side effects"
  [form]
  (valid-n-form? 'n! form))

(defn- n-thread
  [expr forms operator]
  (reduce (fn [acc x]
            (cond (intended-predicate x)   `(when (~operator ~acc ~(second x))
                                              ~acc)
                  (intended-side-effect x) `(do (~operator ~acc ~(second x))
                                                ~acc)
                  :else                    `(~operator ~acc ~x)))
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
