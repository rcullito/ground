(ns n.core)

(def person
  {:name "Nancy"
   :dog  "Claire"
   :age 37})

(defmacro n
  "takes a number of formes and magically transforms them"
  [expr & forms]
  (let [g (gensym)]
    `(let [~g ~expr
           ~@(interleave (repeat g) (butlast forms))]
        ~(if (empty? forms)
          g
          (last forms)))))

;; the charter of this macro is to provide the best of threading
;; some, and cond


;;; ideally our syntax should read
(n person
   (:age)
   (> 0))


(-> person
    :age
    (> 0))


