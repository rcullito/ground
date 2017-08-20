(ns n.core)

(defn handle-n-form
  [g step]
  (let [post-n-step (second step)]
    `(if (nil? ~g) nil
         (if
             (if (sequential? ~g)
               (->> ~g ~post-n-step)
               (-> ~g ~post-n-step))
           ~g
           nil))))

(defmacro n
  "like some->, except forms following n are treated as predicates
  that do not affect the value passed to the next form."
  [expr & forms]
  (let [g (gensym)
        steps (map (fn [step]
                     (if ((every-pred seq? #(= (first %) 'n)) step)
                       (handle-n-form g step)
                       `(if (nil? ~g) nil (if (sequential? ~g)
                                            (->> ~g ~step)
                                            (-> ~g ~step)))))
                   forms)]
    `(let [~g ~expr
           ~@(interleave (repeat g) (butlast steps))]
       ~(if (empty? steps)
          g
          (last steps)))))



