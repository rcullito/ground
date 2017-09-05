(ns ground.core)

(defmacro n->
  "like some->, except predicates prefixed with n either pass through the prior result or return nil for the entire form."
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
  "like some->>, except predicates prefixed with n either pass through the prior result or return nil for the entire form."
  [expr & forms]
  (reduce (fn [acc x]
            (if (= 'n (first x))
              `(if (some->> ~acc ~(second x))
                 ~acc
                 nil)
              `(some->> ~acc ~x)))
          expr
          forms))

(defmacro ground->
  "like ->, except returns nil if exception is thrown"
  [expr & forms]
  `(try (-> ~expr
            ~@forms)
        (catch Exception e# nil)))

(defmacro ground->>
  "like ->>, except returns nil if exception is thrown"
  [expr & forms]
  `(try (doall (->> ~expr
                    ~@forms)) 
        (catch Exception e# nil)))
