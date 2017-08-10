(ns n.core)

(defn handle-n-form
  [g step]
  (let [post-n-step (second step)]
    `(if (nil? ~g) nil
         (if (~(first post-n-step) ~g ~@(next post-n-step))
           ~g
           nil))))

(defmacro n
  "like some->, except forms following n are treated as predicates
n  that do not affect the value passed to the next form."
  [expr & forms]
  (let [g (gensym)
        steps (map (fn [step]
                     (if (seq? step)
                       (if (= (first step) 'n)
                       (handle-n-form g step)
                       `(if (nil? ~g) nil (-> ~g ~step)))
                       `(if (nil? ~g) nil (-> ~g ~step))))
                   forms)]
    `(let [~g ~expr
           ~@(interleave (repeat g) (butlast steps))]
       ~(if (empty? steps)
          g
          (last steps)))))

