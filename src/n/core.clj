(ns n.core)

(def person
  {:name "Nancy"
   :dog  "Claire"
   :age 37})

(defmacro n
  "like some->, except forms following n are treated as predicates
  that do not affect the value passed to the next form."
  [expr & forms]
  (let [g (gensym)
        steps (map (fn [step] (if (= (first step) 'n)
                                (let [post-n-step (second step)]
                                  `(if (~(first post-n-step) ~g ~@(next post-n-step))
                                     ~g
                                     nil))
                                `(if (nil? ~g) nil (-> ~g ~step))))
                   forms)]
    `(let [~g ~expr
           ~@(interleave (repeat g) (butlast steps))]
       ~(if (empty? steps)
          g
          (last steps)))))

(n person
   (:age)
   (+ 1)
   (n (> 35)))

(n person
   (:age)
   (n (> 30))
   (+ 10))

;; update readme with examples of how the macro is actually used in practice.
